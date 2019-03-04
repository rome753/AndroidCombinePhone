package cc.rome753.wat;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Arrays;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by chao on 2019/1/30.
 */

public class SignalingClient {

    private static SignalingClient instance;
    private SignalingClient(){}
    public static SignalingClient get() {
        if(instance == null) {
            synchronized (SignalingClient.class) {
                if(instance == null) {
                    instance = new SignalingClient();
                }
            }
        }
        return instance;
    }

    private Socket socket;
    private String room = "OldPlace";
    private Callback callback;

    public void init(Callback callback) {
        this.callback = callback;
        try {

            socket = IO.socket("http://192.168.1.237:8080");
            socket.connect();

            socket.emit("create or join", room);

            socket.on("created", args -> {
                Log.e("chao", "room created:" + socket.id());
                callback.onCreateRoom();
            });
            socket.on("full", args -> {
                Log.e("chao", "room full");
            });
            socket.on("join", args -> {
                Log.e("chao", "peer joined " + Arrays.toString(args));
                callback.onPeerJoined(String.valueOf(args[1]));
            });
            socket.on("joined", args -> {
                Log.e("chao", "self joined:" + socket.id());
                callback.onSelfJoined();
            });
            socket.on("log", args -> {
                Log.e("chao", "log call " + Arrays.toString(args));
            });
            socket.on("bye", args -> {
                Log.e("chao", "bye " + args[0]);
                callback.onPeerLeave((String) args[0]);
            });
            socket.on("message", args -> {
                Log.e("chao", "message " + Arrays.toString(args));
                Object arg = args[0];
                if(arg instanceof String) {

                } else if(arg instanceof JSONObject) {
                    JSONObject data = (JSONObject) arg;
                    String type = data.optString("type");
                    if("offer".equals(type)) {
                        callback.onOfferReceived(data);
                    } else if("answer".equals(type)) {
                        callback.onAnswerReceived(data);
                    } else if("candidate".equals(type)) {
                        callback.onIceCandidateReceived(data);
                    }
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        socket.emit("bye", socket.id());
        socket.disconnect();
        socket.close();
        instance = null;
    }

    public interface Callback {
        void onCreateRoom();
        void onPeerJoined(String socketId);
        void onSelfJoined();
        void onPeerLeave(String msg);

        void onOfferReceived(JSONObject data);
        void onAnswerReceived(JSONObject data);
        void onIceCandidateReceived(JSONObject data);
    }

}
