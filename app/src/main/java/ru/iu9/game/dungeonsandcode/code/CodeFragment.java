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
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.list_entities.CodeAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandAdapter;

public class CodeFragment extends Fragment {

    private OnCodeBtnListener mCodeBtnListener;
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
        commandList.setAdapter(new CommandAdapter(getContext(), mCommandListItems));
    }

    private void initCodeList(View view) {
        RecyclerView codeList = view.findViewById(R.id.code_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        codeList.setLayoutManager(layoutManager);
        codeList.setAdapter(new CodeAdapter(getContext(), new ArrayList<String>()));
    }

    private void createCommandListItems() {
        mCommandListItems = new ArrayList<>();

        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_move_black_24dp,
                        "move();"
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_left_black_24dp,
                        "turnLeft();"
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_right_black_24dp,
                        "turnRight();"
                )
        );
    }

    public interface OnCodeBtnListener {
        void goUp();

        void goLeft();

        void goRight();

        void goDown();
    }
}
