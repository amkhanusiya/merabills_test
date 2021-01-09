package com.merabills.viewmodels;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.merabills.AddPaymentDialogFragment;
import com.merabills.R;
import com.merabills.interfaces.OnAddPaymentListener;
import com.merabills.models.Payment;

import java.util.Arrays;
import java.util.List;

public class AddPaymentDialogViewModel extends ViewModel {

    public final MutableLiveData<String> amount = new MutableLiveData<>();
    public final MutableLiveData<Boolean> amountErrorEnable = new MutableLiveData<>(false);
    public final MutableLiveData<String> amountErrorMsg = new MutableLiveData<>("");


    public final MutableLiveData<String> provider = new MutableLiveData<>();
    public final MutableLiveData<Boolean> providerErrorEnable = new MutableLiveData<>(false);
    public final MutableLiveData<String> providerErrorMsg = new MutableLiveData<>("");

    public final MutableLiveData<String> transactionRef = new MutableLiveData<>();
    public final MutableLiveData<Boolean> transactionRefErrorEnable = new MutableLiveData<>(false);
    public final MutableLiveData<String> transactionRefErrorMsg = new MutableLiveData<>("");

    public final MutableLiveData<List<String>> types = new MutableLiveData<>();

    public final MutableLiveData<String> selectedType = new MutableLiveData<>();
    public final MutableLiveData<Boolean> selectedTypeErrorEnable = new MutableLiveData<>(false);
    public final MutableLiveData<String> selectedTypeErrorMsg = new MutableLiveData<>("");

    public final MutableLiveData<Boolean> isCashPayment = new MutableLiveData<>(true);
    private final AddPaymentDialogFragment dialogFragment;
    private OnAddPaymentListener addPaymentListener;


    public AddPaymentDialogViewModel(Context context, AddPaymentDialogFragment dialog, OnAddPaymentListener listener) {
        this.dialogFragment = dialog;
        this.addPaymentListener = listener;
        types.setValue(Arrays.asList(context.getResources().getStringArray(R.array.transfer_types)));
        selectedType.observe(dialog, s -> {
            isCashPayment.setValue(s.equalsIgnoreCase("Cash"));
        });
    }

    public void addPayment(View view) {
        if (validatePayment()) {
            final String enteredAmount = amount.getValue();
            final String enteredProvider = provider.getValue();
            final String enteredTransactionRef = transactionRef.getValue();
            final String type = selectedType.getValue();
            final boolean isCashPaymentType = isCashPayment.getValue();

            Payment _payment = Payment.addCashPayment(type, enteredAmount);
            if (!isCashPaymentType) {
                _payment = Payment.addPayment(type, enteredAmount, enteredProvider, enteredTransactionRef);
            }

            addPaymentListener.onPayment(_payment);
            cancelPayment(view);
        }

    }

    private boolean validatePayment() {
        boolean isValid = true;
        final String enteredAmount = amount.getValue();
        final String enteredProvider = provider.getValue();
        final String enteredTransactionRef = transactionRef.getValue();
        final String type = selectedType.getValue();
        final boolean isCashPaymentType = isCashPayment.getValue();

        if (TextUtils.isEmpty(enteredAmount)) {
            isValid = false;
            amountErrorEnable.setValue(true);
            amountErrorMsg.setValue("Please enter amount");
        } else {
            amountErrorEnable.setValue(false);
        }

        if (TextUtils.isEmpty(type)) {
            isValid = false;
            selectedTypeErrorEnable.setValue(true);
            selectedTypeErrorMsg.setValue("Please select payment type");
        } else {
            selectedTypeErrorEnable.setValue(false);
        }

        if (!isCashPaymentType && TextUtils.isEmpty(enteredProvider)) {
            isValid = false;
            providerErrorEnable.setValue(true);
            providerErrorMsg.setValue("Please enter provider");
        } else {
            providerErrorEnable.setValue(false);
        }

        if (!isCashPaymentType && TextUtils.isEmpty(enteredTransactionRef)) {
            isValid = false;
            transactionRefErrorEnable.setValue(true);
            transactionRefErrorMsg.setValue("Please enter transaction reference");
        } else {
            transactionRefErrorEnable.setValue(false);
        }

        return isValid;
    }

    public void cancelPayment(View view) {
        dialogFragment.dismissAllowingStateLoss();
    }
}
