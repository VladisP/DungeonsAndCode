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
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;

public class CommandAdapter extends RecyclerView.Adapter<CommandHolder> {

    private Context mContext;
    private CodeEditor mCodeEditor;
    private List<CommandListItem> mCommandListItems;
    private int mNestingLevel;

    public CommandAdapter(Context context, CodeEditor codeEditor, List<CommandListItem> items) {
        mContext = context;
        mCodeEditor = codeEditor;
        mCommandListItems = items;
        mNestingLevel = 0;
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

    public int getNestingLevel() {
        return mNestingLevel;
    }

    public void incNestingLevel() {
        mNestingLevel++;

        if (mCommandListItems.get(getItemCount() - 1).getType() != CommandType.CANCEL) {
            mCommandListItems.add(
                    new CommandListItem(
                            R.drawable.ic_command_cancel_red_24dp,
                            CommandType.CANCEL
                    )
            );

            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void decNestingLevel() {
        mNestingLevel--;

        if (mNestingLevel == 0 && mCommandListItems.get(getItemCount() - 1).getType() == CommandType.CANCEL) {
            mCommandListItems.remove(getItemCount() - 1);

            notifyItemRemoved(getItemCount());
        }
    }
}
