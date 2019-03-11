package cc.rome753.wat;

/**
 * Created by chao on 19-3-4.
 */

public class Ball {

    int x;
    int y;
    int dx;
    int dy;
    long time;
    long maxTime;

    public Ball(int x, int y, int dx, int dy, int maxTime) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.maxTime = maxTime;
    }

    public void move() {
        time++;
        x += dx;
        y += dy;
    }

    public void bounce(int right, int bottom) {
        if(x <= 0 || x >= right) {
            dx = -dx;
        }
        if(y <= 0 || y >= bottom) {
            dy = -dy;
        }
    }
    
    public int getBorderType(int width, int height) {
        int borderType = -1;
        if(x <= 0) borderType = 0;
        else if(y <= 0) borderType = 1;
        else if(x >= width) borderType = 2;
        else if(y >= height) borderType = 3;
        return borderType;
    }

    public boolean isOverTime() {
        return time >= maxTime;
    }
}
