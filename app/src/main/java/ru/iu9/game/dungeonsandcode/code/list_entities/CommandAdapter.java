package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;

public class CommandAdapter extends RecyclerView.Adapter<CommandHolder> {

    private Context mContext;
    private List<CommandListItem> mCommandListItems;

    public CommandAdapter(Context context, List<CommandListItem> items) {
        mContext = context;
        mCommandListItems = items;
    }

    @NonNull
    @Override
    public CommandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.command_list_item, parent, false);

        return new CommandHolder(view);
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
}
