package ru.iu9.game.dungeonsandcode.code;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;

public class CodeFragment extends Fragment {

    private OnCodeBtnListener mCodeBtnListener;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCodeBtnListener = (OnCodeBtnListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button upButton = view.findViewById(R.id.up_btn);
        Button leftButton = view.findViewById(R.id.left_btn);
        Button rightButton = view.findViewById(R.id.right_btn);
        Button downButton = view.findViewById(R.id.down_btn);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeBtnListener.goUp();
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeBtnListener.goLeft();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeBtnListener.goRight();
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeBtnListener.goDown();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCodeBtnListener = null;
    }

    public interface OnCodeBtnListener {
        void goUp();

        void goLeft();

        void goRight();

        void goDown();
    }
}
