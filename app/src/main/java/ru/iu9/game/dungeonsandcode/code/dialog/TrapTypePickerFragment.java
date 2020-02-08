package ru.iu9.game.dungeonsandcode.code.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import ru.iu9.game.dungeonsandcode.R;

public class TrapTypePickerFragment extends DialogFragment {

    public static final String EXTRA_TRAP_TYPE = "ru.iu9.game.dungeonsandcode.traptype";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final RadioGroup trapTypeRadio = (RadioGroup) LayoutInflater
                .from(getActivity())
                .inflate(R.layout.trap_type_picker, null);

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(trapTypeRadio)
                .setTitle(getString(R.string.choose_trap_type))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioButton checked = trapTypeRadio.findViewById(trapTypeRadio.getCheckedRadioButtonId());
                        int checkedIndex = trapTypeRadio.indexOfChild(checked);

                        sendResult(checkedIndex);
                    }
                })
                .create();
    }

    private void sendResult(int type) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TRAP_TYPE, type);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
