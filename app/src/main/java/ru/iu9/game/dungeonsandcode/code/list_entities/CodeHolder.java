package ru.iu9.game.dungeonsandcode.code.list_entities;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.iu9.game.dungeonsandcode.R;

class CodeHolder extends RecyclerView.ViewHolder {

    private TextView mLineNumberTextView;
    private TextView mCodeTextView;

    CodeHolder(@NonNull View itemView) {
        super(itemView);
        mLineNumberTextView = itemView.findViewById(R.id.line_number);
        mCodeTextView = itemView.findViewById(R.id.code_line);
    }

    void bind(String codeLine, int position) {
        mLineNumberTextView.setText(String.format(Locale.US, "%d", position + 1));
        mCodeTextView.setText(codeLine);
    }
}
