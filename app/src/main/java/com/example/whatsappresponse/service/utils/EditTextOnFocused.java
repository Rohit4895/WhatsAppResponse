package com.example.whatsappresponse.service.utils;

import android.view.View;
import android.widget.EditText;

public class EditTextOnFocused implements View.OnFocusChangeListener {

    private EditTextWatcher editTextWatcher;

    public EditTextOnFocused(EditTextWatcher editTextWatcher){
        this.editTextWatcher = editTextWatcher;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (view instanceof EditText){
            EditText editText = (EditText)view;

            if (hasFocus){
                int position = (int) view.getTag();

                editTextWatcher.setPosition(position);
                editText.addTextChangedListener(editTextWatcher);
            }else {
                editText.removeTextChangedListener(editTextWatcher);
            }
        }
    }
}
