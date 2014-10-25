package com.ckroetsch.hanabi.injection;

import android.content.Context;

import com.ckroetsch.hanabi.network.Constants;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiRetrofitFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocket;
import com.ckroetsch.hanabi.network.HanabiSocketFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocketIO;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

/**
 * @author curtiskroetsch
 */
public class HanabiModule extends AbstractModule {

    private final Context mContext;

    @Inject
    public HanabiModule(final Context context) {
        super();
        mContext = context;
    }

    @Override
    protected void configure() {
        bind(HanabiRetrofitFrontEndAPI.class).toInstance(getRetrofitImpl());
        bind(HanabiSocket.class).to(HanabiSocketIO.class).in(Singleton.class);
        bind(HanabiFrontEndAPI.class).to(HanabiSocketFrontEndAPI.class).in(Singleton.class);
    }

    private HanabiRetrofitFrontEndAPI getRetrofitImpl() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setConverter(new JacksonConverter())
                .build();
        return restAdapter.create(HanabiRetrofitFrontEndAPI.class);
    }
}
