package cc.rome753.wat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SignalingClient.Callback, GameView.BorderListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameView gameView = findViewById(R.id.game_view);
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

    }

    @Override
    public void onSelfJoined() {

    }

    @Override
    public void onPeerLeave(String msg) {

    }

    @Override
    public boolean onReach(int direction, Ball ball) {
        return direction == 2;
    }
}
