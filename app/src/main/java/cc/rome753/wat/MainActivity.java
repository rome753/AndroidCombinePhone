package cc.rome753.wat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SignalingClient.Callback, GameView.BorderListener {

    GameView gameView;
    int openSide = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.game_view);
        gameView.setBorderListener(this);
        SignalingClient.get().init(this);
    }

    @Override
    protected void onDestroy() {
        SignalingClient.get().destroy();
        super.onDestroy();
    }

    @Override
    public void onCreateRoom() {
    }

    @Override
    public void onPeerJoined(String socketId) {
        openSide = 2;
    }

    @Override
    public void onSelfJoined() {
        openSide = 0;
    }

    @Override
    public void onPeerLeave(String msg) {

    }

    @Override
    public void onBallReached(Ball ball) {
        gameView.postAddBall(ball);
    }

    @Override
    public boolean onBorder(int borderType, Ball ball) {
        if(borderType == openSide) {
            SignalingClient.get().sendBall(ball);
            return true;
        }
        return false;
    }
}
