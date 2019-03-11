package cc.rome753.wat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
    BorderListener borderListener;

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

    boolean isRunning = false;

    public void start() {
        isRunning = true;
        postInvalidate();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isRunning) return;
        int w = getWidth(), h = getHeight();
        if(balls.size() < 10 && random.nextInt(100) > 95) {
            createBall(random, balls, w, h);
        }
        Iterator<Ball> iterator = balls.iterator();
        while(iterator.hasNext()) {
            Ball ball = iterator.next();
            canvas.drawCircle(ball.x, ball.y, 50, paint);
            ball.move();
            int borderType = ball.getBorderType(w, h);
            if(!ball.isLive() || (borderListener != null && borderListener.onBorder(borderType, ball))) {
                iterator.remove();
            } else {
                ball.bounce(w, h);
            }
        }
        invalidate();
    }

    private static void createBall(Random random, HashSet<Ball> balls, int width, int height) {
        Ball ball = new Ball(
                random.nextInt(width),
                random.nextInt(height),
                random.nextInt(100) - 50,
                random.nextInt(100) - 50,
                random.nextInt(600) + 200 // 16ms * 200
        );
        balls.add(ball);
    }

    public interface BorderListener {
        /**
         *
         * @param borderType 0,1,2,3: left,top,right,down
         * @param ball the ball
         * @return true: will remove the ball, false: not remove
         */
        boolean onBorder(int borderType, Ball ball);
    }

    public void setBorderListener(BorderListener borderListener) {
        this.borderListener = borderListener;
    }

    public void postAddBall(Ball ball) {
        post(new Runnable() {
            @Override
            public void run() {
                if(ball.dx > 0) ball.x = 0;
                else ball.x = getWidth();
                balls.add(ball);
            }
        });
    }
}
