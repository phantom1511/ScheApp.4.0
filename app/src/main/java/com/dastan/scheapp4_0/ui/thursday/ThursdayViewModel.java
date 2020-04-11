package com.dastan.scheapp4_0.ui.thursday;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThursdayViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ThursdayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is thursday fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

