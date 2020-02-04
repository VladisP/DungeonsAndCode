package ru.iu9.game.dungeonsandcode.dungeon.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.DialogEventListener;

public class LoseFragment extends DialogFragment {

    private static final String TITLE = "Игра окончена";
    private static final String BUTTON_TEXT = "Начать заново";

    private DialogEventListener mDialogEventListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mDialogEventListener = (DialogEventListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.lose_dialog, null);

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setTitle(TITLE)
                .setPositiveButton(BUTTON_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogEventListener.restartGame();
                    }
                })
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDialogEventListener = null;
    }
}
