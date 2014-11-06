package com.ckroetsch.hanabi.game;

import android.util.Log;

import com.ckroetsch.hanabi.events.BusSingleton;
import com.ckroetsch.hanabi.events.socket.GameChangeEvent;
import com.ckroetsch.hanabi.events.socket.meta.EnterGameEvent;
import com.ckroetsch.hanabi.model.Game;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author curtiskroetsch
 */
@Deprecated
@Singleton
public class GameContextProvider implements Provider<GameContext> {

    Map<Integer, GameContext> mGameContexts;

    int mCurrentGame;

    @Inject
    private GameContextProvider() {
        Log.d(GameContextProvider.class.getName(), "CREATED");
        BusSingleton.get().register(this);
        mGameContexts = new HashMap<Integer, GameContext>();
    }

    @Override
    public GameContext get() {
        GameContext gameContext = mGameContexts.get(mCurrentGame);
        if (gameContext == null) {
            throw new IllegalStateException("No Current game");
        }

        return mGameContexts.get(mCurrentGame);
    }

    private GameContext createGameContext(final Game game, final String name) {
        return new GameContext() {
            @Override
            public Game get() {
                return game;
            }

            @Override
            public int getId() {
                return game.getId();
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Subscribe
    public void onGameEnter(EnterGameEvent event) {
        final GameContext game = mGameContexts.put(event.game.getId(), event);
        mCurrentGame = game.getId();
    }

    @Subscribe
    public void onGameChange(GameChangeEvent event) {
        final GameContext game = createGameContext(event.game, event.name);
        Log.d(GameContextProvider.class.getName(), "GAME CHANGE");
        mGameContexts.put(event.game.getId(), game);
        mCurrentGame = game.getId();
    }


}
