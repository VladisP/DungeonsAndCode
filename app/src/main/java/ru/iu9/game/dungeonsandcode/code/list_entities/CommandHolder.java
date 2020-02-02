package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.dialog.RepNumPickerFragment;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;

public class CommandHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final int REQUEST_REP_NUM = 0;
    private static final String DIALOG_TAG = "RepNum";

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
        //TODO: add repeat check... AND UNCOMMENT THIS
//        mCodeEditor.addCodeLine(mCommandListItem.getCommandText());

        FragmentManager fragmentManager = ((Fragment) mCodeEditor).getFragmentManager();

        if (fragmentManager != null) {
            RepNumPickerFragment dialog = new RepNumPickerFragment();
            dialog.setTargetFragment((Fragment) mCodeEditor, REQUEST_REP_NUM);
            dialog.show(fragmentManager, DIALOG_TAG);
        }
    }
}
