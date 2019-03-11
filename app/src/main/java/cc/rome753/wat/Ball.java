package cc.rome753.wat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chao on 19-3-4.
 */

public class Ball {

    int x;
    int y;
    int dx;
    int dy;
    int life;

    public Ball(int x, int y, int dx, int dy, int life) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.life = life;
    }

    public void move() {
        life--;
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

    public boolean isLive() {
        return life > 0;
    }

    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("x", x);
            jo.put("dx", dx);
            jo.put("y", y);
            jo.put("dy", dy);
            jo.put("life", life);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }

    public static Ball fromJson(JSONObject jo) {
        int x = jo.optInt("x");
        int dx = jo.optInt("dx");
        int y = jo.optInt("y");
        int dy = jo.optInt("dy");
        int life = jo.optInt("life");
        return new Ball(x, y, dx, dy, life);
    }

}
