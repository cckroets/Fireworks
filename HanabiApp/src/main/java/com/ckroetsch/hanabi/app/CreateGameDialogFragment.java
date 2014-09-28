package com.ckroetsch.hanabi.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ckroetsch.hanabi.R;

import roboguice.fragment.RoboDialogFragment;

/**
 * @author curtiskroetsch
 */
public class CreateGameDialogFragment extends RoboDialogFragment {

    CreateDialogListener mListener;

    public static CreateGameDialogFragment createInstance(CreateDialogListener listener) {
        CreateGameDialogFragment fragment = new CreateGameDialogFragment();
        fragment.mListener = listener;
        return fragment;
    }


    public interface CreateDialogListener {
        public void onCreate(String name, boolean rainbow);
        public void onCancel();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.dialog_create, null);
        final EditText nameView = (EditText) view.findViewById(R.id.create_name);
        final CheckBox rainbowView = (CheckBox) view.findViewById(R.id.create_rainbow);
        builder.setTitle("Create Game")
                .setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onCreate(nameView.getText().toString(), rainbowView.isChecked());
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
}
