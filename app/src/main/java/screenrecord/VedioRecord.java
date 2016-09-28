package screenrecord;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Created by zchao on 2016/8/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class VedioRecord extends Thread {
    private Activity mActivity;
    private MediaProjection mMediapro;
    private File file;

    private static final int SAMPLE_RATE = 44100; //采样率(CD音质)
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO; //音频通道(单声道)
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT; //音频格式
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;  //音频源（麦克风）

    private AudioRecord mAudioRecord;
    private static final int SIMPLE_RATE = 44100;
    private static final int CHANNAL_COUNT = 1;
    private MediaCodec mAudioEncode;

    public VedioRecord(@NonNull Activity activity, @NonNull MediaProjection mediaProjection, @Nullable File file) {
        this.mActivity = activity;
        this.mMediapro = mediaProjection;
        if (file == null) {
            this.file = new File(Environment.getExternalStorageDirectory(), "vedio" + System.currentTimeMillis() + ".mp3");
        } else {
            this.file = file;
        }
    }


    @Override

    public void run() {
        initeEncode();
    }

    private void initeEncode() {
        try {
            MediaFormat format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_MPEG, SIMPLE_RATE, CHANNAL_COUNT);
            mAudioEncode = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_MPEG);
            mAudioEncode.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);

//            mAudioRecord = AudioRecord.getMinBufferSize(SIMPLE_RATE, CHANNAL_COUNT, )
            mAudioEncode.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
