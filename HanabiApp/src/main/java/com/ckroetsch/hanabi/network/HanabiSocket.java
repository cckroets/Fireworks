package com.ckroetsch.hanabi.network;

import android.util.Log;


import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.MessageEvent;
import com.ckroetsch.hanabi.model.Message;
import com.ckroetsch.hanabi.util.JsonUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * @author curtiskroetsch
 */
@Singleton
public final class HanabiSocket {

    static final String TAG = HanabiSocket.class.getName();

    static final String EVENT_MESSAGE = "send_message";


    @Inject
    public HanabiSocket() {
        connect();
    }

    public void connect() {
        /*
        try {
            SocketIO socket = new SocketIO(Constants.SOCKET_URL_TEST);
            socket.connect(new IOCallback() {
                @Override
                public void onMessage(JSONObject json, IOAcknowledge ack) {
                    Log.d(TAG, "Message json:" + json.toString());
                    try {
                        final String payload = json.getString(EVENT_MESSAGE);
                        Log.d(TAG, "Message payload:" + payload);
                        BusSingleton.get().post(new MessageEvent(JsonUtil.jsonToObject(payload, Message.class)));
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                @Override
                public void onMessage(String data, IOAcknowledge ack) {
                    Log.d(TAG, "Message string: " + data);
                }

                @Override
                public void onError(SocketIOException socketIOException) {
                    Log.e(TAG, "Error " + socketIOException.getMessage());
                }

                @Override
                public void onDisconnect() {
                    Log.d(TAG, "Disconnect");
                }

                @Override
                public void onConnect() {
                    Log.d(TAG, "Connected");
                }

                @Override
                public void on(String event, IOAcknowledge ack, Object... args) {
                    Log.d(TAG, "Server triggered event '" + event + "'");
                }
            });

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }*/
    }
}
