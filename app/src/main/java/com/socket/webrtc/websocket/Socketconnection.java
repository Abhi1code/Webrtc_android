package com.socket.webrtc.websocket;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class Socketconnection implements Parcelable {

    protected Socketconnection(Parcel in) {
        mServerUrl = in.readString();
    }

    public static final Creator<Socketconnection> CREATOR = new Creator<Socketconnection>() {
        @Override
        public Socketconnection createFromParcel(Parcel in) {
            return new Socketconnection(in);
        }

        @Override
        public Socketconnection[] newArray(int size) {
            return new Socketconnection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mServerUrl);
    }

    public enum ConnectionStatus {
        DISCONNECTED,
        CONNECTED
    }

    public interface ServerListener {
        void onNewMessage(String message);
        void onStatusChange(ConnectionStatus status);
    }

    private WebSocket mWebSocket;
    private OkHttpClient mClient;
    private String mServerUrl;
    private Handler mMessageHandler;
    private Handler mStatusHandler;
    private ServerListener mListener;
    //private Context context;

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.CONNECTED);
            mStatusHandler.sendMessage(m);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Message m = mMessageHandler.obtainMessage(0, text);
            mMessageHandler.sendMessage(m);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
//            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.DISCONNECTED);
//            mStatusHandler.sendMessage(m);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.DISCONNECTED);
            mStatusHandler.sendMessage(m);
            disconnect();

        }
    }

    public Socketconnection(String url) {
        mClient = new OkHttpClient.Builder()
                .readTimeout(3,  TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mServerUrl = url;
        //this.context = context;
        Request request = new Request.Builder()
                .url(mServerUrl)
                .build();
        mWebSocket = mClient.newWebSocket(request, new SocketListener());
    }

    public void connect(ServerListener listener) {

        mListener = listener;
        mMessageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                mListener.onNewMessage((String) message.obj);
                return true;
            }
        });

        mStatusHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                mListener.onStatusChange((ConnectionStatus)message.obj);
                return true;
            }
        });
    }

    public void disconnect() {
        mWebSocket.close(1000,null);
        mListener = null;
        mMessageHandler.removeCallbacksAndMessages(null);
        mStatusHandler.removeCallbacksAndMessages(null);
    }

    public void sendMessage(String message) {
        mWebSocket.send(message);
    }

}
