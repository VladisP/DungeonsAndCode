package ru.iu9.game.dungeonsandcode.code;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.list_entities.CodeAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandAdapter;

public class CodeFragment extends Fragment implements CodeEditor {

    private OnCodeBtnListener mCodeBtnListener;
    private RecyclerView mCodeList;
    private List<CommandListItem> mCommandListItems;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCodeBtnListener = (OnCodeBtnListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCommandListItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initCommandList(view);
        initCodeList(view);

        view.findViewById(R.id.remove_line_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastLine();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCodeBtnListener = null;
    }

    private void initCommandList(View view) {
        RecyclerView commandList = view.findViewById(R.id.command_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        commandList.setLayoutManager(layoutManager);
        commandList.setAdapter(new CommandAdapter(getContext(), this, mCommandListItems));
    }

    private void initCodeList(View view) {
        mCodeList = view.findViewById(R.id.code_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mCodeList.setLayoutManager(layoutManager);
        mCodeList.setAdapter(new CodeAdapter(getContext(), new ArrayList<String>()));
    }

    private void createCommandListItems() {
        mCommandListItems = new ArrayList<>();

        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_move_black_24dp,
                        "move()"
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_left_black_24dp,
                        "turnLeft()"
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_right_black_24dp,
                        "turnRight()"
                )
        );
    }

    @Override
    public void addCodeLine(String codeLine) {
        RecyclerView.Adapter adapter = mCodeList.getAdapter();

        if (adapter != null) {
            CodeEditor codeEditor = (CodeEditor) adapter;
            codeEditor.addCodeLine(codeLine);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            mCodeList.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }

    @Override
    public void removeLastLine() {
        RecyclerView.Adapter adapter = mCodeList.getAdapter();

        if (adapter != null && adapter.getItemCount() > 0) {
            CodeEditor codeEditor = (CodeEditor) adapter;
            codeEditor.removeLastLine();
            adapter.notifyItemRemoved(adapter.getItemCount());
        }
    }

    public interface OnCodeBtnListener {
        void goUp();

        void goLeft();

        void goRight();

        void goDown();
    }
}
