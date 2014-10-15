package com.ckroetsch.hanabi.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Game;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import roboguice.activity.RoboFragmentActivity;


public class MainActivity extends RoboFragmentActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String json = "\n" +
                "{\n" +
                "  \"currentPlayer\": \"Tt\", \n" +
                "  \"discarded\": [\n" +
                "    {\n" +
                "      \"number\": 2, \n" +
                "      \"suit\": \"RED\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 2, \n" +
                "      \"suit\": \"BLUE\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 3, \n" +
                "      \"suit\": \"BLUE\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 4, \n" +
                "      \"suit\": \"BLUE\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 1, \n" +
                "      \"suit\": \"RED\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 1, \n" +
                "      \"suit\": \"BLUE\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 3, \n" +
                "      \"suit\": \"RED\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 2, \n" +
                "      \"suit\": \"RED\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"number\": 1, \n" +
                "      \"suit\": \"YELLOW\"\n" +
                "    }\n" +
                "  ], \n" +
                "  \"hasEnded\": false, \n" +
                "  \"hasStarted\": true, \n" +
                "  \"id\": 43, \n" +
                "  \"isRainbow\": false, \n" +
                "  \"numCardsRemaining\": 31, \n" +
                "  \"numHints\": 8, \n" +
                "  \"numLives\": 3, \n" +
                "  \"order\": [\n" +
                "    \"Curtis\", \n" +
                "    \"Tt\"\n" +
                "  ], \n" +
                "  \"played\": [], \n" +
                "  \"players\": [\n" +
                "    {\n" +
                "      \"hand\": [\n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"GREEN\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 4, \n" +
                "          \"suit\": \"GREEN\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"WHITE\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"YELLOW\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 2, \n" +
                "          \"suit\": \"YELLOW\"\n" +
                "        }\n" +
                "      ], \n" +
                "      \"name\": \"Curtis\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"hand\": [\n" +
                "        {\n" +
                "          \"number\": 5, \n" +
                "          \"suit\": \"YELLOW\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"BLUE\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 5, \n" +
                "          \"suit\": \"GREEN\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"RED\"\n" +
                "        }, \n" +
                "        {\n" +
                "          \"number\": 1, \n" +
                "          \"suit\": \"GREEN\"\n" +
                "        }\n" +
                "      ], \n" +
                "      \"name\": \"Tt\"\n" +
                "    }\n" +
                "  ], \n" +
                "  \"score\": 0, \n" +
                "  \"spectators\": [\n" +
                "    {\n" +
                "      \"name\": \"gggg\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"Tim\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"Clement\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"tbone\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"name\": \"dkfk\"\n" +
                "    }\n" +
                "  ], \n" +
                "  \"turnsLeft\": -1\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        Game game = null;
        try {
            game = mapper.readValue(json, Game.class);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        openGameFragment(game, "Curtis");
    }

    public void openStartFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new StartFragment())
                .commit();
    }

    public void openGameFragment(Game game, String name) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment gameFragment = GameFragment.createInstance(game, name);
        transaction.replace(R.id.fragment_container, gameFragment)
                .commit();
    }

}
