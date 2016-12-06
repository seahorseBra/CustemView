package com.example.administrator.custemview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/3/30.
 */
public class ImageHandleActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    private static final int  MID_VALUE = 50;
    @Bind(R.id.img)
    ImageView mImage;
    @Bind(R.id.light)
    SeekBar mLightSeek;
    @Bind(R.id.tinge)
    SeekBar mTingeSeek;
    @Bind(R.id.saturation)
    SeekBar mSaturationSeek;

    private float lighten = 0;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_handle_activity);
        ButterKnife.bind(this);
        mLightSeek.setOnSeekBarChangeListener(this);
        mSaturationSeek.setOnSeekBarChangeListener(this);
        mTingeSeek.setOnSeekBarChangeListener(this);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.index);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.light:
                lighten = (progress - MID_VALUE) * 1.0f/MID_VALUE*180;
                break;
            case R.id.tinge:

                break;
            case R.id.saturation:

                break;

        }
        mImage.setImageBitmap(handleBitmap(bitmap, lighten, 0,0));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static Bitmap handleBitmap(Bitmap bmp, float light, float staturation, float tinge){
        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        ColorMatrix matrix = new ColorMatrix();
        matrix.setScale(light,light,light,1);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bmp, 0,0, paint);

        return bitmap;
    }
}
