package com.merabills.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.merabills.R;
import com.merabills.models.Payment;

public class PaymentViewModel extends ViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();

    public PaymentViewModel(Context context, Payment payment) {
        title.setValue(String.format("%s : %s%s", payment.getPaymentType(), context.getString(R.string.rs_symbol), payment.getAmount()));
    }



}
