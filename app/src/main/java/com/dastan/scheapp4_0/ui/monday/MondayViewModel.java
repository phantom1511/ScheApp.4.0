package com.dastan.scheapp4_0.ui.monday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MondayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MondayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is monday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}