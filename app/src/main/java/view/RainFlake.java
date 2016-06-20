package view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;

import Utils.RandomGenerator;

/**
 * Created by mavin on 2016/6/20.
 */
public class RainFlake implements WeatherInterface{
    protected RandomGenerator random;
    protected Paint paint;
    protected int lenght;
    protected Point posintion;
    protected Shader shader;
    private int color[] = {0x00ffffff, 0xffffffff, 0x00ffffff};
    private float pos[] = {0, 0.5f, 1};
    public RainFlake(RandomGenerator random, Paint paint, int lenght, Point posintion) {
        this.random = random;
        this.paint = paint;
        this.lenght = lenght;
        this.posintion = posintion;
        shader = new LinearGradient(0,0,20,20,color,pos, Shader.TileMode.CLAMP);
    }

    public static RainFlake create(int width, int height, Paint paint, int length) {
        RandomGenerator random = new RandomGenerator();
        Point point = new Point(random.getRandom(width), random.getRandom(height));
        return new RainFlake(random, paint, length, point);
    }

    protected void draw(Canvas canvas) {
        paint.setShader(shader);
        canvas.drawLine(posintion.x, posintion.y, posintion.x + lenght /2, (float) (posintion.y +Math.cos(Math.PI/6) * lenght), paint);
    }

    private void move(int width, int height) {
        posintion.x = random.getRandom(width);
        posintion.y = random.getRandom(height);
    }
}
