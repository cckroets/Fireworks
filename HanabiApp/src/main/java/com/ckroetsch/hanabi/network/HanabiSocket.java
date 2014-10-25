package com.ckroetsch.hanabi.network;

/**
 * @author curtiskroetsch
 */
public interface HanabiSocket {

    void connect();

    void emit(String event, Object arg);
}
