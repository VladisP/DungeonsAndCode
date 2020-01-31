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
import java.util.Objects;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.list_entities.CodeAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandAdapter;

public class CodeFragment extends Fragment implements CodeEditor {

    private HeroMoveListener mHeroMoveListener;
    private RecyclerView mCodeList;
    private List<CommandListItem> mCommandListItems;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mHeroMoveListener = (HeroMoveListener) context;
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

        view.findViewById(R.id.run_program_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runProgram();
            }
        });

        view.findViewById(R.id.temp_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeroMoveListener.moveUp();
            }
        });

        view.findViewById(R.id.temp_down_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeroMoveListener.moveDown();
            }
        });

        view.findViewById(R.id.temp_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeroMoveListener.moveLeft();
            }
        });

        view.findViewById(R.id.temp_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeroMoveListener.moveRight();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHeroMoveListener = null;
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
        Objects.requireNonNull(mCodeList.getItemAnimator()).setRemoveDuration(0L);
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
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (codeAdapter != null) {
            codeAdapter.addCodeLine(codeLine);
            codeAdapter.notifyItemInserted(codeAdapter.getItemCount() - 1);
            mCodeList.smoothScrollToPosition(codeAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void removeLastLine() {
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (codeAdapter != null && codeAdapter.getItemCount() > 0) {
            codeAdapter.removeLastLine();
            codeAdapter.notifyItemRemoved(codeAdapter.getItemCount());
        }
    }

    @Override
    public void runProgram() {
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (codeAdapter != null) {
            Interpreter.run(codeAdapter.getProgram(), mHeroMoveListener);
        }
    }

    public interface HeroMoveListener {
        void moveUp();

        void moveLeft();

        void moveRight();

        void moveDown();
    }
}
