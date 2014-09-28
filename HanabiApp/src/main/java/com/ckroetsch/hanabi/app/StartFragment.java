package com.ckroetsch.hanabi.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ckroetsch.hanabi.R;
import com.ckroetsch.hanabi.model.requests.CreateGameRequest;
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
public class StartFragment extends RoboFragment implements CreateGameDialogFragment.CreateDialogListener {

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
    }

    public void showCreateDialog() {
        final DialogFragment createDialog = CreateGameDialogFragment.createInstance(this);
        createDialog.show(getActivity().getSupportFragmentManager(), "create");
    }

    @Override
    public void onCreate(String name, boolean rainbow) {
        Log.d(TAG, "CREATED: name = " + name + " rainbow = " + rainbow);
        mHanabiAPI.create(new CreateGameRequest(name, rainbow), new Callback<Integer>() {
            @Override
            public void success(Integer integer, Response response) {
                Log.d(TAG, "Game create() : id = " + integer);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "error: " + error.getMessage());
            }
        });
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "CANCELLED");
    }
}
