package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;

public class CodeAdapter extends RecyclerView.Adapter<CodeHolder> {

    private Context mContext;
    private List<CodeLine> mCodeLines;

    public CodeAdapter(Context context, List<CodeLine> codeLines) {
        mContext = context;
        mCodeLines = codeLines;
    }

    @NonNull
    @Override
    public CodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.code_list_item, parent, false);

        return new CodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeHolder holder, int position) {
        CodeLine codeLine = mCodeLines.get(position);
        holder.bind(codeLine, position);
    }

    @Override
    public int getItemCount() {
        return mCodeLines.size();
    }

    public void addCodeLine(CodeLine codeLine) {
        mCodeLines.add(codeLine);
    }

    public void removeLastLine() {
        mCodeLines.remove(mCodeLines.size() - 1);
    }

    public void removeAllLines() {
        mCodeLines.clear();
    }

    public List<CodeLine> getProgram() {
        return mCodeLines;
    }
}
