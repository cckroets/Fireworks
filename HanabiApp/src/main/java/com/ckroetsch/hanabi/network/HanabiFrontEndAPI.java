package com.ckroetsch.hanabi.network;

import com.ckroetsch.hanabi.model.GameResponse;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameId;
import com.ckroetsch.hanabi.model.Suit;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * @author curtiskroetsch
 */
public interface HanabiFrontEndAPI {

    @GET(Constants.API_GAME)
    void getGame(@Path("id") int id,
                 Callback<Game> callback);

    @FormUrlEncoded
    @POST(Constants.API_CREATE)
    void create(@Field("name") String name,
                @Field("rainbow") boolean rainbow,
                Callback<Game> callback);

    @FormUrlEncoded
    @POST(Constants.API_ENTER)
    void enter(@Path("id") int id,
               @Field("name") String name,
               Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_JOIN)
    void join(@Path("id") int id,
              @Field("name") String name,
              Callback<GameResponse> callback);

    @POST(Constants.API_START)
    void start(@Path("id") int id,
               Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_PLAY)
    void play(@Path("id") int id,
              @Field("name") String name,
              @Field("cardIndex") int index,
              Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_DISCARD)
    void discard(@Path("id") int id,
                 @Field("name") String name,
                 @Field("cardIndex") int index,
                 Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_HINT_COLOR)
    void giveColorHint(@Path("id") int id,
                       @Field("name") String name,
                       @Field("toName") String toName,
                       @Field("suit") Suit suit,
                       Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_HINT_NUMBER)
    void giveNumberHint(@Path("id") int id,
                        @Field("name") String name,
                        @Field("toName") String toName,
                        @Field("number") int number,
                        Callback<GameResponse> callback);

    @FormUrlEncoded
    @POST(Constants.API_END)
    void end(@Path("id") int id,
             Callback<Boolean> callback);

    @FormUrlEncoded
    @POST(Constants.API_MESSAGE)
    void message(@Path("id") int id,
                 @Field("name") String name,
                 @Field("message") String message,
                 Callback<GameResponse> success);


}
