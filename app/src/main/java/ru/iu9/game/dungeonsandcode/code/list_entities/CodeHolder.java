package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.iu9.game.dungeonsandcode.R;

class CodeHolder extends RecyclerView.ViewHolder {

    private TextView mCodeTextView;

    CodeHolder(@NonNull View itemView) {
        super(itemView);
        mCodeTextView = itemView.findViewById(R.id.code_line);
    }

    void bind(String codeLine) {
        mCodeTextView.setText(codeLine);
    }
}
