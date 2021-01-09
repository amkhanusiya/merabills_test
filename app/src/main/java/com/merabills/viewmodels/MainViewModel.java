package com.merabills.viewmodels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.merabills.AddPaymentDialogFragment;
import com.merabills.interfaces.IFileManager;
import com.merabills.interfaces.OnAddPaymentListener;
import com.merabills.interfaces.OnRemoveListener;
import com.merabills.models.Payment;

import java.util.ArrayList;

import dagger.hilt.android.qualifiers.ActivityContext;

public class MainViewModel extends ViewModel implements OnAddPaymentListener, OnRemoveListener {

    private final IFileManager fileManager;
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    public MutableLiveData<ArrayList<Payment>> payments = new MutableLiveData<>();
    public MutableLiveData<ArrayList<PaymentViewModel>> paymentViewModels = new MutableLiveData<>();
    final public MutableLiveData<Float> _totalAmount = new MutableLiveData<Float>(0f);

    @ViewModelInject
    public MainViewModel(@ActivityContext Context context, IFileManager fileManager) {
        this.fileManager = fileManager;
        this.context = context;
        payments.observe((LifecycleOwner) context, payments -> {
            calculateTotal();
            updateViewModel();
        });
        payments.setValue(fileManager.getPayments());
    }

    private void updateViewModel() {
        final ArrayList<Payment> _payments = payments.getValue();
        if (!_payments.isEmpty()) {
            ArrayList<PaymentViewModel> _paymentsViewModels = paymentViewModels.getValue();
            if (_paymentsViewModels == null) {
                _paymentsViewModels = new ArrayList<>();
            }
            _paymentsViewModels.clear();
            for (Payment payment : _payments) {
                _paymentsViewModels.add(new PaymentViewModel(context, payment));
            }
            paymentViewModels.setValue(_paymentsViewModels);
        }
    }

    private void calculateTotal() {
        final ArrayList<Payment> _payments = payments.getValue();
        float total = 0;
        if (!_payments.isEmpty()) {
            for (Payment payment : _payments) {
                total += Float.parseFloat(payment.getAmount());
            }
        }
        _totalAmount.setValue(total);
    }


    public void openAddPaymentDialog(View view) {
        new AddPaymentDialogFragment(this).show(((AppCompatActivity) context).getSupportFragmentManager(), AddPaymentDialogFragment.class.getSimpleName());

    }

    @Override
    public void onPayment(Payment payment) {
        ArrayList<Payment> paymentArrayList = this.payments.getValue();
        if (paymentArrayList == null) {
            paymentArrayList = new ArrayList<>();
        }
        /*if (!fileManager.isPaymentTypeAvailable(paymentArrayList, payment.getPaymentType())) {
            paymentArrayList.add(payment);
            payments.setValue(paymentArrayList);
        } else {
            Snackbar.make(((Activity) context).getWindow().getDecorView().getRootView(), "You can't add same payment type transaction.", Snackbar.LENGTH_SHORT).show();
        }*/

    }

    @Override
    public void OnRemove(int position) {
        ArrayList<Payment> paymentArrayList = this.payments.getValue();
        if (paymentArrayList == null) {
            paymentArrayList = new ArrayList<>();
        }
        if (!paymentArrayList.isEmpty()) {
            paymentArrayList.remove(position);
            payments.setValue(paymentArrayList);
        }
    }

    public void save(View view) {
        ArrayList<Payment> paymentArrayList = this.payments.getValue();
        if (paymentArrayList.isEmpty()) {
            Snackbar.make(view, "No details found to save", Snackbar.LENGTH_SHORT).show();
            return;
        }
        fileManager.addPayments(paymentArrayList);
        Snackbar.make(view, "All payment transaction has been saved.", Snackbar.LENGTH_SHORT).show();
    }
}