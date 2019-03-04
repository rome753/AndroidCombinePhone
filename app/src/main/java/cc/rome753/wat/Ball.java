package cc.rome753.wat;

/**
 * Created by chao on 19-3-4.
 */

public class Ball {

    int x;
    int y;
    int dx;
    int dy;
    int right;
    int bottom;
    long time;
    long maxTime;

    public Ball(int x, int y, int dx, int dy, int maxTime, int right, int bottom) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.maxTime = maxTime;
        this.right = right;
        this.bottom = bottom;
    }

    public void move() {
        x += dx;
        y += dy;
        if(x <= 0 || x >= right) {
            dx = -dx;
        }
        if(y <= 0 || y >= bottom) {
            dy = -dy;
        }
        time++;
    }

    public boolean isOverTime() {
        return time >= maxTime;
    }
}
