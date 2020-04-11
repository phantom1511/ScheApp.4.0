package com.dastan.scheapp4_0.ui.tuesday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TuesdayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TuesdayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tuesday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}