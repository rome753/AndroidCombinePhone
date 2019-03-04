package cc.rome753.wat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by chao on 19-3-4.
 */

public class GameView extends View {

    Random random = new Random();
    Paint paint;
    HashSet<Ball> balls = new HashSet<>();

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(balls.size() < 10 && random.nextInt(100) > 95) {
            createBall();
        }
        Iterator<Ball> iterator = balls.iterator();
        while(iterator.hasNext()) {
            Ball ball = iterator.next();
            canvas.drawCircle(ball.x, ball.y, 50, paint);
            ball.move();
            if (ball.isOverTime()) {
                iterator.remove();
            }
        }
        invalidate();
    }

    private void createBall() {
        Ball ball = new Ball(
                random.nextInt(getWidth()),
                random.nextInt(getHeight()),
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(600) + 200,
                getWidth(),
                getHeight()
        );
        balls.add(ball);
    }
}
