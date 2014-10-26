package com.ckroetsch.hanabi.network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.socket.HanabiErrorEvent;
import com.ckroetsch.hanabi.events.socket.SocketEvent;
import com.ckroetsch.hanabi.util.JsonUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.ConnectCallback;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.JSONCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.StringCallback;
import com.squareup.otto.Bus;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author curtiskroetsch
 */
@Singleton
public final class HanabiSocketIO implements HanabiSocket, JSONCallback {

    static final String TAG = HanabiSocketIO.class.getName();

    static final String KEY_EVENT = "event";
    static final String KEY_PAYLOAD = "payload";
    static final String KEY_ERROR = "error";

    SocketIOClient mSocket;

    Bus mBus = BusSingleton.get();

    boolean mConnectRequested;

    final Handler mHandler;

    @Inject
    public HanabiSocketIO(Context context) {
        mHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void connect() {
        if (mConnectRequested) {
            return;
        }
        mConnectRequested = true;
        SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), Constants.SOCKET_URL, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, SocketIOClient client) {
                if (ex != null) {
                    Log.d(TAG, "onConnectCompleted FAILED");
                    ex.printStackTrace();
                    return;
                }
                Log.d(TAG, "onConnectCompleted SUCCESS");
                mSocket = client;
                mSocket.setStringCallback(new StringCallback() {
                    @Override
                    public void onString(String string, Acknowledge acknowledge) {
                        Log.d(TAG, "onString: " + string);
                    }
                });
                mSocket.on("someEvent", new EventCallback() {
                    @Override
                    public void onEvent(JSONArray argument, Acknowledge acknowledge) {
                        Log.d(TAG, "onEvent: " + argument.toString());
                    }
                });
                mSocket.setJSONCallback(HanabiSocketIO.this);
            }
        });
    }

    @Override
    public void emit(String event, Object arg) {
        final JSONArray jsonArray = new JSONArray();
        final JSONObject jsonArg = JsonUtil.objectToJson(arg);
        jsonArray.put(jsonArg);
        mSocket.emit(event, jsonArray);
    }

    @Override
    public void onJSON(JSONObject json, Acknowledge acknowledge) {
        Log.d(TAG, "onJSON: " + json.toString());
        final String eventString = JsonUtil.getChild(json, KEY_EVENT);
        if (eventString == null) {
            final HanabiError e = JsonUtil.getChild(json, KEY_ERROR, HanabiError.class);
            Log.e(TAG, e.getReason());
            mBus.post(new HanabiErrorEvent(e));
            return;
        }
        final SocketEvent socketEvent = SocketEvent.getEvent(eventString);
        if (socketEvent == null) {
            Log.e(TAG, "Unknown event: " + eventString);
            return;
        }
        final Object event = JsonUtil.getChild(json, KEY_PAYLOAD, socketEvent.getEventClass());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBus.post(event);
            }
        });
    }
}
