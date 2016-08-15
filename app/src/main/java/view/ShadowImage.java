package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.custemview.R;


/**
 * Created by zchao on 2016/8/10.
 */
public class ShadowImage extends FrameLayout {

    private Context context;
    private View mRoot;
    private ImageView mImage;
    private ImageView mShadow;
    private int shadowDx, shadowDy;
    private int shadowColor;
    private int resourceId;

    public ShadowImage(Context context) {
        this(context, null);
    }

    public ShadowImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.shadow_image, this);
        initeAttr(attrs);
        iniView();
    }

    private void initeAttr(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowImage);
        shadowDx = typedArray.getInteger(R.styleable.ShadowImage_shadowDX, 0);
        shadowDy = typedArray.getInteger(R.styleable.ShadowImage_shadowDY, 5);
        shadowColor = typedArray.getColor(R.styleable.ShadowImage_shadowTint, 0x1f000000);
        resourceId = typedArray.getResourceId(R.styleable.ShadowImage_res, 0);
    }

    private void iniView() {
        mRoot = findViewById(R.id.root);
        mImage = (ImageView) findViewById(R.id.icon_view);
        mShadow = (ImageView) findViewById(R.id.icon_shadow);


        if (resourceId != 0) {
            mShadow.setImageResource(resourceId);
            mImage.setImageResource(resourceId);
        }
    }

    public void setImageResource(int resID) {
        if (resID != 0) {
            resourceId = resID;
            mImage.setImageResource(resID);
            mShadow.setImageResource(resID);
        }
    }
}
