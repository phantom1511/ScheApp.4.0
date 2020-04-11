package com.dastan.scheapp4_0.ui.saturday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SaturdayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SaturdayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is saturday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}