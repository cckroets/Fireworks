package com.ckroetsch.hanabi.network;

/**
 * @author curtiskroetsch
 */
public interface ResponseHandler<T> {
    void onSuccess(T result);
    void onFailure();
}
