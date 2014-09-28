package com.ckroetsch.hanabi.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ckroetsch.hanabi.R;

import roboguice.fragment.RoboDialogFragment;

/**
 * @author curtiskroetsch
 */
public class EnterGameDialogFragment extends RoboDialogFragment {

    EnterDialogListener mListener;

    public static EnterGameDialogFragment createInstance(EnterDialogListener listener) {
        EnterGameDialogFragment fragment = new EnterGameDialogFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.dialog_enter, null);
        final EditText nameView = (EditText) view.findViewById(R.id.enter_name);
        final EditText idView = (EditText) view.findViewById(R.id.enter_id);
        builder.setTitle("Enter Game")
                .setView(view)
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            final int id = Integer.parseInt(idView.getText().toString());
                            mListener.onGameEnter(id, nameView.getText().toString());
                        } catch (NumberFormatException e) {
                            dialogInterface.dismiss();
                            Toast.makeText(getActivity(), "That Game ID is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onCancel();
                    }
                });
        return builder.create();
    }


    public interface EnterDialogListener {
        void onGameEnter(int id, String name);
        void onCancel();
    }
}
