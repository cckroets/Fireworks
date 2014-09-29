package com.ckroetsch.hanabi.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;
import com.ckroetsch.hanabi.network.HanabiFrontEndAPI;
import com.google.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author curtiskroetsch
 */
public class StartFragment extends RoboFragment implements
        CreateGameDialogFragment.CreateDialogListener,
        EnterGameDialogFragment.EnterDialogListener {

    public static final String TAG = StartFragment.class.getName();

    @InjectView(R.id.start_create_button)
    Button mCreateButton;

    @InjectView(R.id.start_enter_button)
    Button mEnterButton;

    @Inject
    HanabiFrontEndAPI mHanabiAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateDialog();
            }
        });
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEnterDialog();
            }
        });
    }

    public void showCreateDialog() {
        final DialogFragment createDialog = CreateGameDialogFragment.createInstance(this);
        createDialog.show(getActivity().getSupportFragmentManager(), "create");
    }

    public void showEnterDialog() {
        final DialogFragment enterDialog = EnterGameDialogFragment.createInstance(this);
        enterDialog.show(getActivity().getSupportFragmentManager(), "enter");
    }

    @Override
    public void onGameCreate(final String name, boolean rainbow, DialogInterface dialogInterface) {
        Log.d(TAG, "CREATED: name = " + name + " rainbow = " + rainbow);
        mHanabiAPI.create(name, rainbow, new Callback<Game>() {
            @Override
            public void success(Game game, Response response) {
                Log.d(TAG, "Game create() : id = " + game.getId());
                ((MainActivity) getActivity()).openGameFragment(game, name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onGameEnter(int id, final String name, final DialogInterface dialogInterface) {
        Log.d(TAG, "ENTERED: id = " + id + " name = " + name);
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), "Name must not be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        mHanabiAPI.enter(id, name, new Callback<GameResponse>() {
            @Override
            public void success(GameResponse response, Response networkResponse) {
                if (response.isSuccess()) {
                    Log.d(TAG, "Enter success: " + response.getGame().getLives());
                    ((MainActivity) getActivity()).openGameFragment(response.getGame(), name);

                } else {
                    Log.d(TAG, "Enter failed");
                    dialogInterface.dismiss();
                    Toast.makeText(getActivity(), "Could not enter game.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Enter error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        Log.d(TAG, "CANCELLED");
        dialogInterface.dismiss();
    }
}
