package ru.iu9.game.dungeonsandcode.code;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.dialog.RepNumPickerFragment;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.code.list_entities.CodeAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandHolder;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class CodeFragment extends Fragment implements CodeEditor {

    private HeroMoveListener mHeroMoveListener;
    private RecyclerView mCommandList;
    private RecyclerView mCodeList;
    private List<CommandListItem> mCommandListItems;
    private ImageButton mRunButton;
    private TextView mNestingLevelTextView;

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

        mNestingLevelTextView = view.findViewById(R.id.nesting_level_text);

        view.findViewById(R.id.remove_line_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastLine();
            }
        });

        view.findViewById(R.id.delete_program_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProgram();
            }
        });

        view.findViewById(R.id.inc_nest_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incNestingLevel();
            }
        });

        view.findViewById(R.id.dec_nest_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decNestingLevel();
            }
        });

        mRunButton = view.findViewById(R.id.run_program_button);
        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runProgram();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHeroMoveListener = null;
    }

    private void initCommandList(View view) {
        mCommandList = view.findViewById(R.id.command_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        mCommandList.setLayoutManager(layoutManager);
        mCommandList.setAdapter(new CommandAdapter(getContext(), this, mCommandListItems));
    }

    private void initCodeList(View view) {
        mCodeList = view.findViewById(R.id.code_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mCodeList.setLayoutManager(layoutManager);
        mCodeList.setAdapter(new CodeAdapter(getContext(), this, new ArrayList<CodeLine>()));
        Objects.requireNonNull(mCodeList.getItemAnimator()).setRemoveDuration(0L);
    }

    private void createCommandListItems() {
        mCommandListItems = new ArrayList<>();

        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_move_black_24dp,
                        CommandType.MOVE
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_left_black_24dp,
                        CommandType.TURN_LEFT
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_turn_right_black_24dp,
                        CommandType.TURN_RIGHT
                )
        );
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_repeat_black_24dp,
                        CommandType.REPEAT
                )
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }

        if (requestCode == CommandHolder.REQUEST_REP_NUM) {
            int repNum = data.getIntExtra(RepNumPickerFragment.EXTRA_NUM, 1);

            CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

            if (commandAdapter != null) {
                addCodeLine(new CodeLine(CommandType.REPEAT, commandAdapter.getNestingLevel(), repNum));
                incNestingLevel();
            }
        }
    }

    @Override
    public void addCodeLine(CodeLine codeLine) {
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
            mRunButton.setEnabled(false);

            Interpreter.run(codeAdapter.getProgram(), mHeroMoveListener, new InterpreterActionListener() {
                @Override
                public void onInterpretationFinished() {
                    mRunButton.setEnabled(true);
                }
            });
        }
    }

    @Override
    public void deleteProgram() {
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (codeAdapter != null && codeAdapter.getItemCount() > 0) {
            int linesCount = codeAdapter.getItemCount();

            codeAdapter.removeAllLines();
            codeAdapter.notifyItemRangeRemoved(0, linesCount);
        }

        clearNestingLevel();
    }

    @Override
    public int getNestingLevel() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

        return commandAdapter == null ? 0 : commandAdapter.getNestingLevel();
    }

    @Override
    public void incNestingLevel() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

        if (commandAdapter != null) {
            commandAdapter.incNestingLevel();
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", commandAdapter.getNestingLevel()));
        }
    }

    @Override
    public void decNestingLevel() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

        if (commandAdapter != null && commandAdapter.getNestingLevel() > 0) {
            commandAdapter.decNestingLevel();
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", commandAdapter.getNestingLevel()));
        }
    }

    @Override
    public void clearNestingLevel() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

        if (commandAdapter != null) {
            commandAdapter.clearNestingLevel();
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", 0));
        }
    }

    public interface HeroMoveListener {
        void moveUp(MoveAction onMoveEndAction);

        void moveLeft(MoveAction onMoveEndAction);

        void moveRight(MoveAction onMoveEndAction);

        void moveDown(MoveAction onMoveEndAction);

        void changeDirection(HeroDirection heroDirection);
    }

    public interface InterpreterActionListener {
        void onInterpretationFinished();
    }
}
