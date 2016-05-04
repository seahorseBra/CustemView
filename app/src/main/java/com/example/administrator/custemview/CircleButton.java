package com.example.administrator.custemview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by youlu on 2016/1/27.
 */
public class CircleButton extends ImageView{
    private static final String TAG = "CircleButton";

    public CircleButton() {
        this(null);
    }
    public CircleButton(Context context) {
        this(context, null);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "CircleButton() called with: " + "context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
    }

    @Override
    protected boolean verifyDrawable(Drawable dr) {
        Log.d(TAG, "verifyDrawable() called with: " + "dr = [" + dr + "]");
        return super.verifyDrawable(dr);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        Log.d(TAG, "jumpDrawablesToCurrentState() called with: " + "");
        super.jumpDrawablesToCurrentState();
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        Log.d(TAG, "invalidateDrawable() called with: " + "dr = [" + dr + "]");
        super.invalidateDrawable(dr);
    }

    @Override
    public boolean hasOverlappingRendering() {
        Log.d(TAG, "hasOverlappingRendering() called with: " + "");
        return super.hasOverlappingRendering();
    }

    @Override
    public boolean getAdjustViewBounds() {
        Log.d(TAG, "getAdjustViewBounds() called with: " + "");
        return super.getAdjustViewBounds();
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        Log.d(TAG, "setAdjustViewBounds() called with: " + "adjustViewBounds = [" + adjustViewBounds + "]");
        super.setAdjustViewBounds(adjustViewBounds);
    }

    @Override
    public int getMaxWidth() {
        Log.d(TAG, "getMaxWidth() called with: " + "");
        return super.getMaxWidth();
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        Log.d(TAG, "setMaxWidth() called with: " + "maxWidth = [" + maxWidth + "]");
        super.setMaxWidth(maxWidth);
    }

    @Override
    public int getMaxHeight() {
        Log.d(TAG, "getMaxHeight() called with: " + "");
        return super.getMaxHeight();
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        Log.d(TAG, "setMaxHeight() called with: " + "maxHeight = [" + maxHeight + "]");
        super.setMaxHeight(maxHeight);
    }

    @Override
    public Drawable getDrawable() {
        Log.d(TAG, "getDrawable() called with: " + "");
        return super.getDrawable();
    }

    @Override
    public void setImageResource(int resId) {
        Log.d(TAG, "setImageResource() called with: " + "resId = [" + resId + "]");
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        Log.d(TAG, "setImageURI() called with: " + "uri = [" + uri + "]");
        super.setImageURI(uri);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        Log.d(TAG, "setImageDrawable() called with: " + "drawable = [" + drawable + "]");
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageIcon(Icon icon) {
        Log.d(TAG, "setImageIcon() called with: " + "icon = [" + icon + "]");
        super.setImageIcon(icon);
    }

    @Override
    public void setImageTintList(ColorStateList tint) {
        Log.d(TAG, "setImageTintList() called with: " + "tint = [" + tint + "]");
        super.setImageTintList(tint);
    }

    @Nullable
    @Override
    public ColorStateList getImageTintList() {
        Log.d(TAG, "getImageTintList() called with: " + "");
        return super.getImageTintList();
    }

    @Override
    public void setImageTintMode(PorterDuff.Mode tintMode) {
        Log.d(TAG, "setImageTintMode() called with: " + "tintMode = [" + tintMode + "]");
        super.setImageTintMode(tintMode);
    }

    @Nullable
    @Override
    public PorterDuff.Mode getImageTintMode() {
        Log.d(TAG, "getImageTintMode() called with: " + "");
        return super.getImageTintMode();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        Log.d(TAG, "setImageBitmap() called with: " + "bm = [" + bm + "]");
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageState(int[] state, boolean merge) {
        Log.d(TAG, "setImageState() called with: " + "state = [" + state + "], merge = [" + merge + "]");
        super.setImageState(state, merge);
    }

    @Override
    public void setSelected(boolean selected) {
        Log.d(TAG, "setSelected() called with: " + "selected = [" + selected + "]");
        super.setSelected(selected);
    }

    @Override
    public void setImageLevel(int level) {
        Log.d(TAG, "setImageLevel() called with: " + "level = [" + level + "]");
        super.setImageLevel(level);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        Log.d(TAG, "setScaleType() called with: " + "scaleType = [" + scaleType + "]");
        super.setScaleType(scaleType);
    }

    @Override
    public ScaleType getScaleType() {
        Log.d(TAG, "getScaleType() called with: " + "");
        return super.getScaleType();
    }

    @Override
    public Matrix getImageMatrix() {
        Log.d(TAG, "getImageMatrix() called with: " + "");
        return super.getImageMatrix();
    }

    @Override
    public void setImageMatrix(Matrix matrix) {
        Log.d(TAG, "setImageMatrix() called with: " + "matrix = [" + matrix + "]");
        super.setImageMatrix(matrix);
    }

    @Override
    public boolean getCropToPadding() {
        Log.d(TAG, "getCropToPadding() called with: " + "");
        return super.getCropToPadding();
    }

    @Override
    public void setCropToPadding(boolean cropToPadding) {
        Log.d(TAG, "setCropToPadding() called with: " + "cropToPadding = [" + cropToPadding + "]");
        super.setCropToPadding(cropToPadding);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        Log.d(TAG, "onCreateDrawableState() called with: " + "extraSpace = [" + extraSpace + "]");
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        Log.d(TAG, "onRtlPropertiesChanged() called with: " + "layoutDirection = [" + layoutDirection + "]");
        super.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        Log.d(TAG, "setFrame() called with: " + "l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void drawableStateChanged() {
        Log.d(TAG, "drawableStateChanged() called with: " + "");
        super.drawableStateChanged();
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        Log.d(TAG, "drawableHotspotChanged() called with: " + "x = [" + x + "], y = [" + y + "]");
        super.drawableHotspotChanged(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");
        super.onDraw(canvas);
    }

    @Override
    public int getBaseline() {
        Log.d(TAG, "getBaseline() called with: " + "");
        return super.getBaseline();
    }

    @Override
    public void setBaseline(int baseline) {
        Log.d(TAG, "setBaseline() called with: " + "baseline = [" + baseline + "]");
        super.setBaseline(baseline);
    }

    @Override
    public void setBaselineAlignBottom(boolean aligned) {
        Log.d(TAG, "setBaselineAlignBottom() called with: " + "aligned = [" + aligned + "]");
        super.setBaselineAlignBottom(aligned);
    }

    @Override
    public boolean getBaselineAlignBottom() {
        Log.d(TAG, "getBaselineAlignBottom() called with: " + "");
        return super.getBaselineAlignBottom();
    }

    @Override
    public ColorFilter getColorFilter() {
        Log.d(TAG, "getColorFilter() called with: " + "");
        return super.getColorFilter();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        Log.d(TAG, "setColorFilter() called with: " + "cf = [" + cf + "]");
        super.setColorFilter(cf);
    }

    @Override
    public int getImageAlpha() {
        Log.d(TAG, "getImageAlpha() called with: " + "");
        return super.getImageAlpha();
    }

    @Override
    public void setImageAlpha(int alpha) {
        Log.d(TAG, "setImageAlpha() called with: " + "alpha = [" + alpha + "]");
        super.setImageAlpha(alpha);
    }

    @Override
    public void setAlpha(int alpha) {
        Log.d(TAG, "setAlpha() called with: " + "alpha = [" + alpha + "]");
        super.setAlpha(alpha);
    }

    @Override
    public boolean isOpaque() {
        Log.d(TAG, "isOpaque() called with: " + "");
        return super.isOpaque();
    }

    @Override
    public void setVisibility(int visibility) {
        Log.d(TAG, "setVisibility() called with: " + "visibility = [" + visibility + "]");
        super.setVisibility(visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow() called with: " + "");
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow() called with: " + "");
        super.onDetachedFromWindow();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        Log.d(TAG, "getAccessibilityClassName() called with: " + "");
        return super.getAccessibilityClassName();
    }
}
