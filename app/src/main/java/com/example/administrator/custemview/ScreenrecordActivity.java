package com.example.administrator.custemview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import screenrecord.ScreenREC;

/**
 * Created by zchao on 2016/8/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenrecordActivity extends BaseActivity {
    private static final String RESULT_CODE_KEY = "result_code";
    private static final String RESULT_DATA_KEY = "result_data";

    private static final int RESULT_REQUEST_CODE = 1;
    private int mResultCode;
    private Intent mResultData;
    private MediaProjectionManager mProjectManager;
    private MediaProjection mMediaPro;

    @Bind(R.id.text_view)
    EditText mEditText;
    @Bind(R.id.start_btn)
    Button mStartBtn;
    private ScreenREC mScreenREC;
    private File file;

    //    @Bind(R.id.surface_view)
//    SurfaceView mSurfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenrecord_activity);
        ButterKnife.bind(this);
        mProjectManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        if (savedInstanceState != null) {
            mResultCode = savedInstanceState.getInt(RESULT_CODE_KEY);
            mResultData = savedInstanceState.getParcelable(RESULT_DATA_KEY);
        }

        file = new File(Environment.getExternalStorageDirectory(), "vedio-"+ System.currentTimeMillis()+ ".mp4");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this, "用户取消权限", Toast.LENGTH_LONG).show();
            } else {
                mMediaPro = mProjectManager.getMediaProjection(resultCode, data);

                mScreenREC = new ScreenREC(this, mMediaPro, file);
                mScreenREC.start();
                mStartBtn.setText("STOP");
            }
        }
    }

    @OnClick(R.id.start_btn)
    public void startScreenCapture() {
        if (mScreenREC != null) {
            mScreenREC.quit();
            mScreenREC = null;
            mStartBtn.setText("START");
        } else {
            startActivityForResult(mProjectManager.createScreenCaptureIntent(), RESULT_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScreenREC != null) {
            mScreenREC.quit();
            mScreenREC = null;
        }
    }
}
