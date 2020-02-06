package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.helpers.ProgramType;

public class CommandAdapter extends RecyclerView.Adapter<CommandHolder> {

    private Context mContext;
    private CodeEditor mCodeEditor;
    private List<CommandListItem> mCommandListItems;
    private int mMainNestingLevel;
    private int mDodgeScriptNestingLevel;

    public CommandAdapter(Context context, CodeEditor codeEditor, List<CommandListItem> items) {
        mContext = context;
        mCodeEditor = codeEditor;
        mCommandListItems = items;
        mMainNestingLevel = 0;
        mDodgeScriptNestingLevel = 0;
    }

    @NonNull
    @Override
    public CommandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.command_list_item, parent, false);

        return new CommandHolder(view, mCodeEditor);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandHolder holder, int position) {
        CommandListItem item = mCommandListItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mCommandListItems.size();
    }

    private int getCurrentNestingLevel() {
        return mCodeEditor.getEditProgramType() == ProgramType.MAIN ? mMainNestingLevel : mDodgeScriptNestingLevel;
    }

    private void incCurrentNestingLevel() {
        if (mCodeEditor.getEditProgramType() == ProgramType.MAIN) {
            mMainNestingLevel++;
        } else {
            mDodgeScriptNestingLevel++;
        }
    }

    private void decCurrentNestingLevel() {
        if (mCodeEditor.getEditProgramType() == ProgramType.MAIN) {
            mMainNestingLevel--;
        } else {
            mDodgeScriptNestingLevel--;
        }
    }

    private void clearCurrentNestingLevel() {
        if (mCodeEditor.getEditProgramType() == ProgramType.MAIN) {
            mMainNestingLevel = 0;
        } else {
            mDodgeScriptNestingLevel = 0;
        }
    }

    public int getNestingLevel() {
        return getCurrentNestingLevel();
    }

    public void incNestingLevel() {
        incCurrentNestingLevel();
    }

    public void decNestingLevel() {
        decCurrentNestingLevel();
    }

    public void clearNestingLevel() {
        clearCurrentNestingLevel();
    }

    public void setCommandListItems(List<CommandListItem> commandListItems) {
        mCommandListItems = commandListItems;
        notifyDataSetChanged();
    }
}
