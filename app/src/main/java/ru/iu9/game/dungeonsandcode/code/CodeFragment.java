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
import java.util.Stack;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.dialog.RepNumPickerFragment;
import ru.iu9.game.dungeonsandcode.code.dialog.TrapTypePickerFragment;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeEditor;
import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandListItem;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;
import ru.iu9.game.dungeonsandcode.code.helpers.Frame;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.code.helpers.ProgramType;
import ru.iu9.game.dungeonsandcode.code.helpers.RepeatConfig;
import ru.iu9.game.dungeonsandcode.code.list_entities.CodeAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandAdapter;
import ru.iu9.game.dungeonsandcode.code.list_entities.CommandHolder;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.DialogEventListener;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.DodgeAction;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class CodeFragment extends Fragment implements CodeEditor {

    private HeroMoveListener mHeroMoveListener;
    private DialogEventListener mDialogEventListener;

    private ProgramType mOpenProgramType;
    private RecyclerView mCommandList;
    private RecyclerView mCodeList;
    private List<CommandListItem> mCommandListItems;
    private List<CommandListItem> mDefendCommandListItems;

    private ImageButton mRunButton;
    private TextView mNestingLevelTextView;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mHeroMoveListener = (HeroMoveListener) context;
        mDialogEventListener = (DialogEventListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpenProgramType = ProgramType.MAIN;
        createCommandListItems();
        createDefendCommandListItems();
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

        view.findViewById(R.id.restart_game_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogEventListener.restartGame();
            }
        });

        final ImageButton openDodgeListButton = view.findViewById(R.id.open_dodge_list_button);
        final ImageButton openSubroutineButton = view.findViewById(R.id.open_subroutine_button);

        openDodgeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOpenProgramType == ProgramType.MAIN) {
                    openDodgeList();
                    openDodgeListButton.setImageResource(R.drawable.ic_open_main_list_24dp);
                } else if (mOpenProgramType == ProgramType.DODGE_SCRIPT) {
                    openMainList();
                    openDodgeListButton.setImageResource(R.drawable.ic_open_dodge_list_24dp);
                } else if (mOpenProgramType == ProgramType.SUBROUTINE) {
                    openDodgeList();
                    openDodgeListButton.setImageResource(R.drawable.ic_open_main_list_24dp);
                    openSubroutineButton.setImageResource(R.drawable.ic_open_subroutine);
                }
            }
        });

        openSubroutineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOpenProgramType == ProgramType.MAIN) {
                    openSubroutine();
                    openSubroutineButton.setImageResource(R.drawable.ic_open_main_list_24dp);
                } else if (mOpenProgramType == ProgramType.DODGE_SCRIPT) {
                    openSubroutine();
                    openDodgeListButton.setImageResource(R.drawable.ic_open_dodge_list_24dp);
                    openSubroutineButton.setImageResource(R.drawable.ic_open_main_list_24dp);
                } else if (mOpenProgramType == ProgramType.SUBROUTINE) {
                    openMainList();
                    openSubroutineButton.setImageResource(R.drawable.ic_open_subroutine);
                }
            }
        });

        mRunButton = view.findViewById(R.id.run_program_button);

        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runProgram();
                } catch (StackOverflowError error) {
                    mDialogEventListener.showEndgameDialog(R.string.recursion_message);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mHeroMoveListener = null;
        mDialogEventListener = null;
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
        mCodeList.setAdapter(new CodeAdapter(getContext(), this));
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
        mCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_subroutine,
                        CommandType.SUBROUTINE
                )
        );
    }

    private void createDefendCommandListItems() {
        mDefendCommandListItems = new ArrayList<>();

        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_if,
                        CommandType.IF
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_elif,
                        CommandType.ELIF
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_else,
                        CommandType.ELSE
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_dodge_left_24dp,
                        CommandType.DODGE_LEFT
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_dodge_top_24dp,
                        CommandType.DODGE_TOP
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_dodge_right_24dp,
                        CommandType.DODGE_RIGHT
                )
        );
        mDefendCommandListItems.add(
                new CommandListItem(
                        R.drawable.ic_command_dodge_bottom_24dp,
                        CommandType.DODGE_BOTTOM
                )
        );
    }

    private TrapType convertToTrapType(int type) {
        switch (type) {
            case 0:
                return TrapType.LEFT;
            case 1:
                return TrapType.TOP;
            case 2:
                return TrapType.RIGHT;
            case 3:
                return TrapType.BOTTOM;
            default:
                return null;
        }
    }

    private void addCondLine(Intent data, CommandType condCommand) {
        int type = data.getIntExtra(TrapTypePickerFragment.EXTRA_TRAP_TYPE, 0);

        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();

        if (commandAdapter != null) {
            addCodeLine(
                    new CodeLine(
                            condCommand,
                            commandAdapter.getNestingLevel(),
                            convertToTrapType(type)
                    )
            );

            incNestingLevel();
        }
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
        } else if (requestCode == CommandHolder.REQUEST_IF_TRAP_TYPE) {
            addCondLine(data, CommandType.IF);
        } else if (requestCode == CommandHolder.REQUEST_ELIF_TRAP_TYPE) {
            addCondLine(data, CommandType.ELIF);
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

    private boolean isSyntaxCorrect(CodeAdapter codeAdapter) {
        return Parser.parseProgram(codeAdapter.getMainProgram()) &&
                Parser.parseDodgeScript(codeAdapter.getDodgeScript()) &&
                Parser.parseProgram(codeAdapter.getSubroutine());
    }

    @Override
    public void runProgram() {
        final CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (codeAdapter != null && isSyntaxCorrect(codeAdapter)) {
            mRunButton.setEnabled(false);

            final DodgeAction dodgeAction = new DodgeAction() {
                @Override
                public boolean isDodge(TrapType trapType) {
                    return Interpreter.isDodged(trapType, codeAdapter.getDodgeScript());
                }
            };

            final Stack<Frame> callStack = new Stack<>();
            callStack.push(new Frame(codeAdapter.getMainProgram()));

            Interpreter.run(
                    codeAdapter.getMainProgram(),
                    mHeroMoveListener,
                    dodgeAction,
                    new InterpreterActionListener() {

                        InterpreterActionListener mInterpreterActionListener = this;

                        @Override
                        public void onSubroutineCall(RepeatConfig mainConfig, Stack<RepeatConfig> outerConfigs) {
                            callStack.peek().setRepeatConfigs(mainConfig, outerConfigs);

                            Interpreter.run(
                                    codeAdapter.getSubroutine(),
                                    mHeroMoveListener,
                                    dodgeAction,
                                    new InterpreterActionListener() {
                                        @Override
                                        public void onSubroutineCall(RepeatConfig mainConfig, Stack<RepeatConfig> outerConfigs) {
                                            callStack.push(new Frame(codeAdapter.getSubroutine()));
                                            mInterpreterActionListener.onSubroutineCall(mainConfig, outerConfigs);
                                        }

                                        @Override
                                        public void onInfinityRecursion() {
                                            mDialogEventListener.showEndgameDialog(R.string.recursion_message);
                                        }

                                        @Override
                                        public void onInterpretationFinished() {
                                            Interpreter.restoreCurrentLine();

                                            Frame frame = callStack.pop();
                                            InterpreterActionListener listener;

                                            if (callStack.empty()) {
                                                callStack.push(new Frame(codeAdapter.getMainProgram()));
                                                listener = mInterpreterActionListener;
                                            } else {
                                                listener = this;
                                            }

                                            if (frame.getMainConfig() == null && frame.getOuterConfigs() == null) {
                                                Interpreter.run(
                                                        frame.getCurrentProgram(),
                                                        mHeroMoveListener,
                                                        dodgeAction,
                                                        listener
                                                );
                                            } else {
                                                Interpreter.repeat(
                                                        frame.getMainConfig(),
                                                        frame.getOuterConfigs()
                                                );
                                            }
                                        }
                                    }
                            );
                        }

                        @Override
                        public void onInfinityRecursion() {
                            mDialogEventListener.showEndgameDialog(R.string.recursion_message);
                        }

                        @Override
                        public void onInterpretationFinished() {
                            mRunButton.setEnabled(true);
                        }
                    }
            );
        } else {
            mDialogEventListener.showErrorMessage(getString(R.string.syntax_error));
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

    @Override
    public ProgramType getEditProgramType() {
        return mOpenProgramType;
    }

    private void openMainList() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (commandAdapter != null && codeAdapter != null) {
            mOpenProgramType = ProgramType.MAIN;

            codeAdapter.notifyDataSetChanged();
            commandAdapter.setCommandListItems(mCommandListItems);
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", commandAdapter.getNestingLevel()));
        }
    }

    private void openDodgeList() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (commandAdapter != null && codeAdapter != null) {
            mOpenProgramType = ProgramType.DODGE_SCRIPT;

            codeAdapter.notifyDataSetChanged();
            commandAdapter.setCommandListItems(mDefendCommandListItems);
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", commandAdapter.getNestingLevel()));
        }
    }

    private void openSubroutine() {
        CommandAdapter commandAdapter = (CommandAdapter) mCommandList.getAdapter();
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();

        if (commandAdapter != null && codeAdapter != null) {
            mOpenProgramType = ProgramType.SUBROUTINE;

            codeAdapter.notifyDataSetChanged();
            commandAdapter.setCommandListItems(mCommandListItems);
            mNestingLevelTextView.setText(String.format(Locale.US, "%d", commandAdapter.getNestingLevel()));
        }
    }

    private void resetScript(CodeAdapter codeAdapter) {
        if (codeAdapter.getItemCount() > 0) {
            codeAdapter.removeAllLines();
        }

        clearNestingLevel();
    }

    private void resetAllScripts() {
        CodeAdapter codeAdapter = (CodeAdapter) mCodeList.getAdapter();
        ProgramType currentProgramType = mOpenProgramType;

        if (codeAdapter != null) {
            mOpenProgramType = ProgramType.MAIN;
            resetScript(codeAdapter);

            mOpenProgramType = ProgramType.DODGE_SCRIPT;
            resetScript(codeAdapter);

            mOpenProgramType = ProgramType.SUBROUTINE;
            resetScript(codeAdapter);

            mOpenProgramType = currentProgramType;
            codeAdapter.notifyDataSetChanged();
        }
    }

    public void reset() {
        resetAllScripts();
        Interpreter.reset();
        mRunButton.setEnabled(true);
    }

    public interface HeroMoveListener {
        void moveUp(MoveAction onMoveEndAction, DodgeAction dodgeAction);

        void moveLeft(MoveAction onMoveEndAction, DodgeAction dodgeAction);

        void moveRight(MoveAction onMoveEndAction, DodgeAction dodgeAction);

        void moveDown(MoveAction onMoveEndAction, DodgeAction dodgeAction);

        void changeDirection(HeroDirection heroDirection);
    }

    public interface InterpreterActionListener {
        void onSubroutineCall(RepeatConfig mainConfig, Stack<RepeatConfig> outerConfigs);

        void onInfinityRecursion();

        void onInterpretationFinished();
    }
}
