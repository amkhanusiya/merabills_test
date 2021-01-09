package com.merabills.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.merabills.AddPaymentDialogFragment;
import com.merabills.interfaces.OnAddPaymentListener;

public class AddPaymentViewFactory implements ViewModelProvider.Factory {

    private final AddPaymentDialogFragment dialogFragment;
    private final OnAddPaymentListener listener;
    private final Context context;

    public AddPaymentViewFactory(Context c, AddPaymentDialogFragment fragment, OnAddPaymentListener paymentListener) {
        this.dialogFragment = fragment;
        this.listener = paymentListener;
        this.context = c;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddPaymentDialogViewModel(context, dialogFragment, listener);
    }
}
