package view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by mavin on 2016/8/31.
 */
public class DragHelperTest extends FrameLayout {

    private static final String TAG = "DragHelperTest";
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG, "tryCaptureView() called with: " + "], pointerId = [" + pointerId + "]");
            return secondView == child || fistView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d(TAG, "clampViewPositionVertical() called with: " +  "], top = [" + top + "], dy = [" + dy + "]");
            if (child == fistView) {
                return top;
            }
            if (child == secondView) {
                return 0;
            }
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal() called with: " + "], left = [" + left + "], dx = [" + dx + "]");
            if (child == fistView) {
                return 0;
            }
            if (child == secondView) {
                return left;
            }
            return 0;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            Log.d(TAG, "onViewDragStateChanged() called with: " + "state = [" + state + "]");
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == fistView && top < 0 && (-top) <= dp2px(200)) {
                float alph = Math.abs(1.0f - (-top + 0.5f)/dp2px(200));
                if (alph > 1f) {
                    alph = 1f;
                }
                thirdView.setAlpha(alph);

                FrameLayout.LayoutParams params = (LayoutParams) thirdView.getLayoutParams();
                params.height += dy;

                if (params.height <= dp2px(44)) {
                    params.height = dp2px(44);
                }
                if (params.height >= dp2px(200)) {
                    params.height = dp2px(200);
                }
                thirdView.setLayoutParams(params);
            }
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.d(TAG, "onViewPositionChanged() called with: "  + "], left = [" + left + "], top = [" + top + "], dx = [" + dx + "], dy = [" + dy + "]");
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.d(TAG, "onViewCaptured() called with: " + "capturedChild = [" + capturedChild + "], activePointerId = [" + activePointerId + "]");
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == secondView) {
                helper.smoothSlideViewTo(secondView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperTest.this);
            }
            if (releasedChild == fistView) {
                helper.smoothSlideViewTo(fistView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperTest.this);
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Log.d(TAG, "onEdgeTouched() called with: " + "edgeFlags = [" + edgeFlags + "], pointerId = [" + pointerId + "]");
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            Log.d(TAG, "onEdgeLock() called with: " + "edgeFlags = [" + edgeFlags + "]");
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.d(TAG, "onEdgeDragStarted() called with: " + "edgeFlags = [" + edgeFlags + "], pointerId = [" + pointerId + "]");
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            Log.d(TAG, "getOrderedChildIndex() called with: " + "index = [" + index + "]");
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            Log.d(TAG, "getViewHorizontalDragRange() called with: " + "child = [" + child + "]");
//            if (child == secondView) {
//                return 0;
//            }
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            Log.d(TAG, "getViewVerticalDragRange() called with: " + "child = [" + child + "]");
            return super.getViewVerticalDragRange(child);
        }
    };
    private ViewDragHelper helper;
    private View fistView;
    private View secondView;
    private View thirdView;


    public DragHelperTest(Context context) {
        this(context,null);
    }

    public DragHelperTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper = ViewDragHelper.create(this,callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fistView = getChildAt(0);
        secondView = getChildAt(1);
        thirdView = getChildAt(2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            Log.d(TAG, "computeScroll() called with: " + "");
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public int dp2px(int dp){
        float density = getResources().getDisplayMetrics().density;
        return (int)(density * dp + 0.5f);
    }
}
