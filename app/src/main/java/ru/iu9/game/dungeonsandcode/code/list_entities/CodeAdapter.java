package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;
import ru.iu9.game.dungeonsandcode.code.helpers.ProgramType;

public class CodeAdapter extends RecyclerView.Adapter<CodeHolder> {

    private Context mContext;
    private CodeEditor mCodeEditor;
    private List<CodeLine> mMainCodeLines;
    private List<CodeLine> mDodgeScriptCodeLines;

    public CodeAdapter(Context context, CodeEditor codeEditor) {
        mContext = context;
        mCodeEditor = codeEditor;
        mMainCodeLines = new ArrayList<>();
        mDodgeScriptCodeLines = new ArrayList<>();
    }

    @NonNull
    @Override
    public CodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.code_list_item, parent, false);

        return new CodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeHolder holder, int position) {
        CodeLine codeLine = getCurrentCodeLines().get(position);
        holder.bind(codeLine, position);
    }

    @Override
    public int getItemCount() {
        return getCurrentCodeLines().size();
    }

    private List<CodeLine> getCurrentCodeLines() {
        return mCodeEditor.getEditProgramType() == ProgramType.MAIN ?
                mMainCodeLines :
                mDodgeScriptCodeLines;
    }

    public void addCodeLine(CodeLine codeLine) {
        getCurrentCodeLines().add(codeLine);
    }

    public void removeLastLine() {
        if (getCurrentCodeLines().get(getItemCount() - 1).getCommandType() == CommandType.REPEAT) {
            mCodeEditor.decNestingLevel();
        }

        getCurrentCodeLines().remove(getCurrentCodeLines().size() - 1);
    }

    public void removeAllLines() {
        getCurrentCodeLines().clear();
    }

    public List<CodeLine> getMainProgram() {
        return mMainCodeLines;
    }
}
