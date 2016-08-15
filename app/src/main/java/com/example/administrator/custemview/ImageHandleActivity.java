package com.example.administrator.custemview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.ShadowImage;
import view.ShadowImageView;

/**
 * Created by zchao on 2016/3/30.
 */
public class ImageHandleActivity extends BaseActivity {

    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.img_nores)
    TextView imgNores;
    @Bind(R.id.image)
    ShadowImageView image;
    @Bind(R.id.select_img)
    Button selectImg;
    @Bind(R.id.start_compress)
    Button startCompress;
    @Bind(R.id.save_img)
    Button saveImg;
    @Bind(R.id.image_s)
    ShadowImage image_s;
    @Bind(R.id.image_ss)
    ImageView image_ss;
    private Bitmap bitmap;
    private Bitmap bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_handle_activity);
        ButterKnife.bind(this);

        translateImage();
    }

    private void translateImage() {
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.sd_icon);
        bitmap = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        ColorDrawable colorDrawable = new ColorDrawable(Color.BLACK);
        colorDrawable.setBounds(0,0,bitmap1.getWidth(), bitmap1.getHeight());
        colorDrawable.draw(canvas);

        Paint paint = new Paint();
        paint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.OUTER));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap1, 0,0,paint);
    }

    @OnClick(R.id.save_img)
    public void setimage() {
        if (bitmap != null) {
            image_ss.setImageBitmap(bitmap);
        }
    }
}
