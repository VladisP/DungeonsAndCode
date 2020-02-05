package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.dialog.RepNumPickerFragment;
import ru.iu9.game.dungeonsandcode.code.dialog.TrapTypePickerFragment;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;

public class CommandHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int REQUEST_REP_NUM = 0;
    public static final int REQUEST_IF_TRAP_TYPE = 1;
    public static final int REQUEST_ELIF_TRAP_TYPE = 2;

    private static final String DIALOG_REP_TAG = "RepNum";
    private static final String DIALOG_TRAP_TAG = "TrapType";

    private ImageButton mCommandImageButton;
    private CodeEditor mCodeEditor;
    private CommandListItem mCommandListItem;

    CommandHolder(@NonNull View itemView, CodeEditor codeEditor) {
        super(itemView);
        mCommandImageButton = itemView.findViewById(R.id.command_image_button);
        mCodeEditor = codeEditor;
        mCommandImageButton.setOnClickListener(this);
    }

    void bind(CommandListItem item) {
        mCommandListItem = item;
        mCommandImageButton.setImageResource(item.getImageId());
    }

    @Override
    public void onClick(View v) {
        if (mCommandListItem.getType() == CommandType.REPEAT) {
            showNumPickDialog();
        } else if (mCommandListItem.getType() == CommandType.IF) {
            showTrapTypePickDialog(REQUEST_IF_TRAP_TYPE);
        } else if (mCommandListItem.getType() == CommandType.ELIF) {
            showTrapTypePickDialog(REQUEST_ELIF_TRAP_TYPE);
        } else {
            mCodeEditor.addCodeLine(
                    new CodeLine(
                            mCommandListItem.getType(),
                            mCodeEditor.getNestingLevel()
                    )
            );

            if (mCommandListItem.getType() == CommandType.ELSE) {
                mCodeEditor.incNestingLevel();
            }
        }
    }

    private void showNumPickDialog() {
        FragmentManager fragmentManager = ((Fragment) mCodeEditor).getFragmentManager();

        if (fragmentManager != null) {
            RepNumPickerFragment dialog = new RepNumPickerFragment();
            dialog.setTargetFragment((Fragment) mCodeEditor, REQUEST_REP_NUM);
            dialog.show(fragmentManager, DIALOG_REP_TAG);
        }
    }

    private void showTrapTypePickDialog(int requestCode) {
        FragmentManager fragmentManager = ((Fragment) mCodeEditor).getFragmentManager();

        if (fragmentManager != null) {
            TrapTypePickerFragment dialog = new TrapTypePickerFragment();
            dialog.setTargetFragment((Fragment) mCodeEditor, requestCode);
            dialog.show(fragmentManager, DIALOG_TRAP_TAG);
        }
    }
}
