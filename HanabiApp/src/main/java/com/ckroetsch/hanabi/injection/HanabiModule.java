package com.ckroetsch.hanabi.injection;

import android.content.Context;

import com.ckroetsch.hanabi.network.Constants;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import retrofit.RestAdapter;

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
        bind(HanabiFrontEndAPI.class).toInstance(getRetrofitImpl());
    }

    public HanabiFrontEndAPI getRetrofitImpl() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_URL)
                .setConverter(HanabiFrontEndAPI.JACKSON_CONVERTER)
                .build();
        return restAdapter.create(HanabiFrontEndAPI.class);
    }
}
