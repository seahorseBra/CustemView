package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zchao on 2017/5/23.
 * desc:
 * version:
 */

public class WeatherImgeTextView extends android.support.v7.widget.AppCompatTextView {
    public WeatherImgeTextView(Context context) {
        this(context, null);
    }

    public WeatherImgeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface_number = Typeface.createFromAsset(context.getAssets(), "and_num_Regular.ttf");
        setTypeface(typeface_number);
    }

    float fontHeight = -1;

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        Paint paint = getPaint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getCurrentTextColor());

        if (fontHeight == -1) {
            Paint.FontMetrics ft = getPaint().getFontMetrics();
            fontHeight = ft.bottom - ft.top - ft.descent - ft.leading;
        }
        canvas.drawText(getText().toString().trim(), getWidth()/2, (getHeight() - fontHeight) / 2, paint);
        canvas.restore();
    }
}
