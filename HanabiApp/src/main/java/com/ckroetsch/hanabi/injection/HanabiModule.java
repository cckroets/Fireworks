package com.ckroetsch.hanabi.injection;

import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocket;
import com.ckroetsch.hanabi.network.HanabiSocketFrontEndAPI;
import com.ckroetsch.hanabi.network.HanabiSocketIO;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author curtiskroetsch
 */
public class HanabiModule extends AbstractModule {

    @Inject
    public HanabiModule() {
        super();
    }

    @Override
    protected void configure() {
        bind(HanabiSocket.class).to(HanabiSocketIO.class).in(Singleton.class);
        bind(HanabiFrontEndAPI.class).to(HanabiSocketFrontEndAPI.class).in(Singleton.class);
    }
}
