package ru.iu9.game.dungeonsandcode.constructor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorEventListener;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorPartType;

public class EditorFragment extends Fragment {

    private ConstructorEventListener mConstructorEventListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mConstructorEventListener = (ConstructorEventListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup constructorRadio = view.findViewById(R.id.constructor_radio);
        constructorRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.constructor_radio_wall:
                        mConstructorEventListener.switchPartType(ConstructorPartType.WALL);
                        break;
                    case R.id.constructor_radio_hero:
                        mConstructorEventListener.switchPartType(ConstructorPartType.HERO);
                        break;
                    case R.id.constructor_radio_treasure:
                        mConstructorEventListener.switchPartType(ConstructorPartType.TREASURE);
                        break;
                    case R.id.constructor_radio_monster:
                        mConstructorEventListener.switchPartType(ConstructorPartType.MONSTER);
                        break;
                    case R.id.constructor_radio_trap_left:
                        mConstructorEventListener.switchPartType(ConstructorPartType.TRAP_LEFT);
                        break;
                    case R.id.constructor_radio_trap_top:
                        mConstructorEventListener.switchPartType(ConstructorPartType.TRAP_TOP);
                        break;
                    case R.id.constructor_radio_trap_right:
                        mConstructorEventListener.switchPartType(ConstructorPartType.TRAP_RIGHT);
                        break;
                    case R.id.constructor_radio_trap_bottom:
                        mConstructorEventListener.switchPartType(ConstructorPartType.TRAP_BOTTOM);
                        break;
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConstructorEventListener = null;
    }
}
