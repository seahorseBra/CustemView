package com.example.administrator.custemview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.HandleMatrix;

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
    @Bind(R.id.btn1)
    Button mBtn;

    private float lighten = 1, tinge = 0, saturation = 1;
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


    @OnClick(R.id.btn1)
    public void changeMatrix(){
        Bitmap bitmap = handleBitmap(this.bitmap, HandleMatrix.getMatrix(HandleMatrix.REVERSAL));
        mImage.setImageBitmap(bitmap);
    }
    @OnClick(R.id.reset)
    public void reset(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.index);
        mImage.setImageBitmap(bitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.light:
                lighten = progress*1.0f/MID_VALUE;
                break;
            case R.id.tinge:
                tinge = (progress - MID_VALUE) *1.0f/MID_VALUE*180;
                break;
            case R.id.saturation:
                saturation = progress * 1.0f/MID_VALUE;
                break;

        }
        Bitmap bitmap = handleBitmap(this.bitmap, lighten, saturation, tinge);
        mImage.setImageBitmap(bitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static Bitmap handleBitmap(Bitmap bmp, float light, float saturation, float tinge){

        ColorMatrix lightMatrix = new ColorMatrix();
        lightMatrix.setScale(light,light,light,1);

        ColorMatrix satMatrix = new ColorMatrix();
        satMatrix.setSaturation(saturation);

        ColorMatrix tingeMatrix = new ColorMatrix();
        tingeMatrix.setRotate(0, tinge);
        tingeMatrix.setRotate(1, tinge);
        tingeMatrix.setRotate(2, tinge);

        ColorMatrix matrix = new ColorMatrix();
        matrix.postConcat(lightMatrix);
        matrix.postConcat(satMatrix);
        matrix.postConcat(tingeMatrix);

        return handleBitmap(bmp, matrix);
    }

    public static Bitmap handleBitmap(Bitmap bmp, ColorMatrix matrix){
        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bmp, 0 ,0, paint);
        return bitmap;
    }

}
