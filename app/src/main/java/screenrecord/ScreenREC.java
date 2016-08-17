package screenrecord;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Surface;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zchao on 2016/8/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenREC extends Thread {
    private static final String TAG = "ScreenREC";
    private Activity mActivity;
    private MediaProjection mMediaPro;
    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private final DisplayMetrics metrics;
    private File file;

    private boolean isMuxerStarted = false;
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private Surface mSurface;
    private static final int BIT_RATE = 6000000;//采样率
    private static final int FRAME_RATE = 30;   //帧率
    private static final int FRAME_INTERVAL_RATE = 10;
    private MediaMuxer muxer;
    private VirtualDisplay mVirtualDisplay;
    private MediaCodec mEncode;
    private int mVideoTranckIndex;

    public ScreenREC(@NonNull Activity activity, @NonNull MediaProjection mediaProjection, @Nullable File file) {
        super(TAG);
        this.mActivity = activity;
        this.mMediaPro = mediaProjection;
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (file == null) {
            this.file = new File(Environment.getExternalStorageDirectory(), "video" + System.currentTimeMillis() + ".mp4");
        } else {
            this.file = file;
        }
    }

    @Override
    public void run() {
        try {
            try {
                prepareInite();
                muxer = new MediaMuxer(file.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mVirtualDisplay = mMediaPro.createVirtualDisplay("aaa", metrics.widthPixels,
                    metrics.heightPixels, metrics.densityDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mSurface, null, null);

            recordVirtualDisplay();
        } finally {
            release();
        }


    }

    /**
     * 录制
     */
    private void recordVirtualDisplay() {
        while (!mQuit.get()) {
            int index = mEncode.dequeueOutputBuffer(mBufferInfo, 10000);
            if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                resetEncode();
            } else if (index == MediaCodec.INFO_TRY_AGAIN_LATER) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (index > 0) {
                if (!isMuxerStarted) {
                    throw new IllegalStateException("MediaMuxer must call addTrack(format)");
                }
                encodeToVideoTrack(index);

                mEncode.releaseOutputBuffer(index, false);
            }
        }
    }

    private void encodeToVideoTrack(int index) {
        ByteBuffer outputBuffer = mEncode.getOutputBuffer(index);
        if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
            mBufferInfo.size = 0;
        }
        if (mBufferInfo.size == 0) {
            outputBuffer = null;
        }
        if (outputBuffer != null) {
            outputBuffer.position(mBufferInfo.offset);
            outputBuffer.limit(mBufferInfo.offset + mBufferInfo.size);
            muxer.writeSampleData(mVideoTranckIndex, outputBuffer, mBufferInfo);
        }
    }

    private void resetEncode() {
        if (isMuxerStarted) {
            throw new IllegalStateException("the Muxer is started");
        } else {
            MediaFormat newFormat = mEncode.getOutputFormat();
            mVideoTranckIndex = muxer.addTrack(newFormat);
            muxer.start();
            isMuxerStarted = true;
        }
    }

    /**
     * 释放资源
     */
    private void release() {
        if (mEncode != null) {
            mEncode.stop();
            mEncode.release();
            mEncode = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mMediaPro != null) {
            mMediaPro.stop();
        }
        if (muxer != null) {
            muxer.stop();
            muxer.release();
            muxer = null;
        }
    }

    /**
     * 预加载
     */
    private void prepareInite() {
        MediaFormat format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, metrics.widthPixels, metrics.heightPixels);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, FRAME_INTERVAL_RATE);

        try {
            mEncode = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            mEncode.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mSurface = mEncode.createInputSurface();
            mEncode.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * stop task
     */
    public final void quit() {
        mQuit.set(true);
    }
}
