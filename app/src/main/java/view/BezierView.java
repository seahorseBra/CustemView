package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.imagepipeline.memory.PooledByteArrayBufferedInputStream;

import java.util.ArrayList;

import utils.Utils;

/**
 * Created by zchao on 2016/9/6.
 */
public class BezierView extends View {

    private Paint paint;
    private Path path;
    private boolean isSelect;
    private Point assistPoint;
    private int height;
    private int width;
    private float firstX;
    private float firstY;

    private ArrayList<Point> p1 = new ArrayList<>();
    private ArrayList<Point> p2 = new ArrayList<>();
    private ArrayList<Point> p3 = new ArrayList<>();
    private ArrayList<Point> p4 = new ArrayList<>();


    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);

        assistPoint = new Point();

        path = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float xp = event.getX();
        float yp = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (yp > height / 2 - Utils.dp2Px(30) && yp < height / 2 + Utils.dp2Px(30)) {
                    isSelect = true;
                    firstX = xp;
                    firstY = yp;
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
//                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                assistPoint.x = (int) xp;
                assistPoint.y = (int) yp;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isSelect = false;
                break;
        }
        if (isSelect) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        assistPoint.x = w;
        assistPoint.y = h/2;
        for (int i = 0; i < 6; i++) {
            Point point = new Point(w/5 * i, h/2);
            p1.add(point);
        }
        //计算所有点的中点
        for (int i = 0; i < p1.size() - 1; i++) {
            p2.add(getCenter(p1.get(i), p1.get(i+1)));
        }
        //计算中点的中点
        for (int i = 0; i < p2.size() - 1; i++) {
            p3.add(getCenter(p2.get(i), p2.get(i+1)));
        }
        //计算所有点的贝塞尔曲线辅助点
        for (int i = 0; i < p2.size(); i++) {
            p4.add(translationPoint(p2.get(i), p3.get(i/2), p1.get(i+1)));
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        path.reset();

        path.moveTo(0, height/2);
        path.quadTo(assistPoint.x, assistPoint.y, width, height/2);

        canvas.drawPath(path, paint);
    }

    private Point getCenter(int x1, int y1, int x2, int y2) {
        Point point = new Point();
        point.x = x1 + (x2 - x1)/2;
        point.y = y1 + (y2 - y1)/2;
        return point;
    }

    /**
     * 计算两个点的中点
     * @param p1
     * @param p2
     * @return
     */
    private Point getCenter(Point p1, Point p2) {
        Point point = new Point();
        point.x = p1.x + (p2.x - p1.x)/2;
        point.y = p1.y + (p2.y - p1.y)/2;
        return point;
    }

    /**
     * 根据两个点的位置关系来平移另一点
     * @param srcPosition
     * @param p1
     * @param p2
     * @return
     */
    private Point translationPoint(Point srcPosition, Point p1, Point p2) {
        Point dstPoint = new Point();
        dstPoint.x = srcPosition.x + (p2.x - p1.x);
        dstPoint.y = srcPosition.y + (p2.y - p1.y);
        return dstPoint;
    }
}
