package littlegridview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * describe: 测算卡片底部小选项
 * change: 修改测算卡片小选项view，使之能通过适配器{@link MeasureViewAdapter}来适配不同的item项目，目前样式上扩展还没做完备，如其他地方使用需小心
 * auth: xll
 * date: 2016/12/15
 */
public class LittleGridView extends ViewGroup{
    /**
     * 最大显示个数
     */
    public int mShowMax = 8;

    /**
     * ViewHolder列表
     */
    List<LittleGridViewBaseHolder> mHolders = new ArrayList<>();



    /**
     * item的高度
     */
    private int mItemHeight = 0;

    /**
     * 绘制分割线paint
     */
    private Paint mLinePaint;

    private int spanCount = 1;  //每行个数

    MeasureViewAdapter adapter = null;

    public LittleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLinePaint = new Paint();
        mLinePaint.setColor(0xfff5f0e9);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    public void setAdapter(MeasureViewAdapter adapter) {
        this.adapter = adapter;
        this.spanCount = adapter.getSpanCount();
        this.mShowMax = adapter.getTotleCount();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测算当前view应展示多高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(width, 0);
            return;
        }
        int lines = childCount / spanCount + (childCount % spanCount == 0 ? 0 : 1);
        int width0 = (width) / spanCount;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView == null) {
                continue;
            }
            /**
             * 避免占不满宽度的问题，左边元素使用width0，右边元素使用width1
             */
            measureChild(childView, width0);
            if (i == 0) {
                mItemHeight = childView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(width, mItemHeight * lines);
    }

    /**
     * measure child
     *
     * @param child
     * @param width
     */
    private void measureChild(View child, int width) {
        child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public int getLineHeight() {
        if (adapter.getTotleCount() == 0) {
            return 0;
        }
        if (mItemHeight == 0) {
            LittleGridViewBaseHolder holder = adapter.onCreateView(getContext());
            measureChild(holder.getItemView(), 100);
            mItemHeight = holder.getItemView().getMeasuredHeight();
        }
        return mItemHeight;
    }


    public void refresh() {
        /**
         * 1.构造item view 如果已经有了，就从mHolders中取出来
         * 2.如果没有，就新建一个holder
         * 3. 如果view没有加入到容器，要放入容器中
         * 4. 此组最大显示6个
         */
        int count = adapter.getTotleCount();

        for (int i = 0; i < count; i++) {
            LittleGridViewBaseHolder holder = createHolder(i);
            adapter.onBindViewHolder(holder, i);
            //加入容器
            if (holder.getItemView().getParent() == null) {
                addView(holder.getItemView());
            }
        }

        for (int i = count; i < mHolders.size(); i++) {
            //从容器里面去掉多余的元素
            if (mHolders.get(i).getItemView().getParent() != null) {
                removeView(mHolders.get(i).getItemView());
            }
        }
    }

    /**
     * 获取当前item需要展示的view holder ，如果没有就新建，并加入 holder列表
     *
     * @param i
     * @return
     */
    private LittleGridViewBaseHolder createHolder(int i) {
        if (i >= mHolders.size() && adapter != null) {
            LittleGridViewBaseHolder holder = adapter.onCreateView(getContext());
            mHolders.add(holder);
            return holder;
        }
        return mHolders.get(i);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int lines = i / spanCount + 1;
            int top = (lines - 1) * mItemHeight;
            int left = (i % spanCount) * (childView.getMeasuredWidth());
            childView.layout(left, top, childView.getMeasuredWidth() + left, childView.getMeasuredHeight() + top);
        }
    }




    /**
     * Desc:
     * Change:
     *
     * @version
     * @author zchao created at 2017/3/23 16:06
     * @see
    */
    public abstract class LittleGridViewBaseHolder<T> {

        public View mItemView;

        public LittleGridViewBaseHolder(Context context) {
            if (getLayoutRes() != 0) {
                mItemView = LayoutInflater.from(context).inflate(getLayoutRes(), null, false);
            }
        }

        public abstract @LayoutRes int getLayoutRes();

        /**
         * 加入容器的view
         *
         * @return
         */
        public View getItemView() {
            return mItemView;
        }

        /**
         * 显示view
         */
        public void show() {
            mItemView.setVisibility(View.VISIBLE);
        }

        /**
         * 隐藏view
         */
        public void hide() {
            mItemView.setVisibility(View.GONE);
        }

        /**
         * 绑定item
         *
         * @param tool
         */
        public void bindView(T tool) {

        }

    }
}
