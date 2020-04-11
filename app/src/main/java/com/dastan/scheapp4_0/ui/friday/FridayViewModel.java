package com.dastan.scheapp4_0.ui.friday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FridayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FridayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}