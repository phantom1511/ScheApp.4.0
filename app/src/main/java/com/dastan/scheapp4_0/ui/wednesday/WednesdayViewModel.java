package com.dastan.scheapp4_0.ui.wednesday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WednesdayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WednesdayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is wednesday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}