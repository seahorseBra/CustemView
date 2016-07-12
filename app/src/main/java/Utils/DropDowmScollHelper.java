package utils;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zchao on 2016/5/3.
 */
public class DropDowmScollHelper extends RecyclerView.OnScrollListener implements View.OnTouchListener{

    private static final String TAG = "DropDowmScollHelper";
    private GestureDetector.SimpleOnGestureListener scollListener = new SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp() called with: " + "e = [" + e + "]");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress() called with: " + "e = [" + e + "]");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll() called with: " + "e1 = [" + e1 + "], e2 = [" + e2 + "], distanceX = [" + distanceX + "], distanceY = [" + distanceY + "]");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling() called with: " + "e1 = [" + e1 + "], e2 = [" + e2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress() called with: " + "e = [" + e + "]");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown() called with: " + "e = [" + e + "]");
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap() called with: " + "e = [" + e + "]");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTapEvent() called with: " + "e = [" + e + "]");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed() called with: " + "e = [" + e + "]");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.d(TAG, "onContextClick() called with: " + "e = [" + e + "]");
            return super.onContextClick(e);
        }
    };
    private final GestureDetectorCompat gestureDetector;

    public DropDowmScollHelper(Context context, View view) {
        super();
        gestureDetector = new GestureDetectorCompat(context, scollListener);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d(TAG, "onScrollStateChanged() called with: " + "recyclerView = [" + recyclerView + "], newState = [" + newState + "]");

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.d(TAG, "onScrolled() called with: " + "recyclerView = [" + recyclerView + "], dx = [" + dx + "], dy = [" + dy + "]");
        super.onScrolled(recyclerView, dx, dy);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch() called with: " + "v = [" + v + "], event = [" + event + "]");
        return gestureDetector.onTouchEvent(event);
    }
}
