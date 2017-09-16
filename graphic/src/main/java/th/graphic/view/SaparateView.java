package th.graphic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Color.CYAN;

/**
 * Created by The on 8/6/2017.
 */

public class SaparateView extends View{
    Paint paint = new Paint();

    public SaparateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.CYAN);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 20, 20, paint);
        super.onDraw(canvas);
    }
}
