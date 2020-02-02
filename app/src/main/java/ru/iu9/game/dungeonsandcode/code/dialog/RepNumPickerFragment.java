package ru.iu9.game.dungeonsandcode.code.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import ru.iu9.game.dungeonsandcode.R;

public class RepNumPickerFragment extends DialogFragment {

    public static final String EXTRA_NUM = "ru.iu9.game.dungeonsandcode.repnum";
    private static final int NUM_MIN_VALUE = 1;
    private static final int NUM_MAX_VALUE = 100;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final NumberPicker numberPicker = (NumberPicker) LayoutInflater
                .from(getActivity())
                .inflate(R.layout.rep_num_picker, null);

        numberPicker.setMinValue(NUM_MIN_VALUE);
        numberPicker.setMaxValue(NUM_MAX_VALUE);
        numberPicker.setWrapSelectorWheel(false);

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(numberPicker)
                .setTitle(getString(R.string.select_rep_num))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(numberPicker.getValue());
                    }
                })
                .create();
    }

    private void sendResult(int repNum) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NUM, repNum);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
