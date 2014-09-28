package com.ckroetsch.hanabi.network;

import com.ckroetsch.hanabi.model.Message;
import com.ckroetsch.hanabi.model.requests.CreateGameRequest;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.requests.GiveColorHintRequest;
import com.ckroetsch.hanabi.model.requests.GiveNumberHintRequest;
import com.ckroetsch.hanabi.model.requests.PlayCardRequest;
import com.ckroetsch.hanabi.model.Response;

import retrofit.Callback;
import retrofit.converter.Converter;
import retrofit.converter.JacksonConverter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * @author curtiskroetsch
 */
public interface HanabiFrontEndAPI {

    Converter JACKSON_CONVERTER = new JacksonConverter();

    @GET(Constants.API_GAME)
    void getGame(@Path("id") int id, Callback<Game> callback);

    @POST(Constants.API_CREATE)
    void create(@Body CreateGameRequest creation, Callback<Integer> callback);

    @POST(Constants.API_ENTER)
    void enter(@Path("id") int id, @Body String name, Callback<Response> callback);

    @POST(Constants.API_JOIN)
    void join(@Path("id") int id, @Body String name, Callback<Boolean> callback);

    @POST(Constants.API_START)
    void start(@Path("id") int id, Callback<Boolean> callback);

    @POST(Constants.API_PLAY)
    void play(@Path("id") int id, @Body PlayCardRequest play, Callback<Response> callback);

    @POST(Constants.API_DISCARD)
    void discard(@Path("id") int id, @Body PlayCardRequest discard, Callback<Response> callback);

    @POST(Constants.API_HINT_COLOR)
    void giveColorHint(@Path("id") int id, @Body GiveColorHintRequest hint, Callback<Response> callback);

    @POST(Constants.API_HINT_NUMBER)
    void giveNumberHint(@Path("id") int id, @Body GiveNumberHintRequest hint, Callback<Response> callback);

    @POST(Constants.API_END)
    void end(@Path("id") int id, Callback<Boolean> callback);

    @POST(Constants.API_MESSAGE)
    void message(@Path("id") int id, @Body Message message, Callback<Boolean> success);


}
