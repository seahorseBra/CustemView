package view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.administrator.custemview.R;

import utils.RandomGenerator;

/**
 * Created by mavin on 2016/6/20.
 */
public class RainFlake implements WeatherInterface{
    protected Paint paint;
    protected Point posintion;
    private Bitmap bitmap;
    public RainFlake(Paint paint, Point posintion, Bitmap bitmap) {
        this.paint = paint;
        this.posintion = posintion;
        this.bitmap = bitmap;
    }

    public static RainFlake create(int width, int height, Paint paint, Resources res) {
        RandomGenerator random = new RandomGenerator();
        Point point = new Point(random.getRandom(width), random.getRandom(height));
        final Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.rain_pice);
        return new RainFlake(paint, point, bitmap);
    }

    protected void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, posintion.x, posintion.y, paint);
    }


}
