package com.example.whatsappresponse.service.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class EditTextWatcher implements TextWatcher {

    private int position;
    private InterfaceRepositories.CallBackTextWatcher callBackTextWatcher;

    public EditTextWatcher(InterfaceRepositories.CallBackTextWatcher callBackTextWatcher){
        this.callBackTextWatcher = callBackTextWatcher;
    }

    public void setPosition(int position){
        this.position = position;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence message, int start, int before, int count) {
        callBackTextWatcher.callback(position, message.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
