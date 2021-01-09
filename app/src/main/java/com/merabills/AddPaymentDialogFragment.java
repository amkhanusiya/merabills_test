package com.merabills;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.merabills.databinding.FragmentAddPaymentDialogBinding;
import com.merabills.interfaces.OnAddPaymentListener;
import com.merabills.viewmodels.AddPaymentDialogViewModel;
import com.merabills.viewmodels.AddPaymentViewFactory;
import com.merabills.viewmodels.MainViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddPaymentDialogFragment extends DialogFragment {

    private final OnAddPaymentListener addPaymentListener;

    public AddPaymentDialogFragment(OnAddPaymentListener addPaymentListener) {
        this.addPaymentListener = addPaymentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentAddPaymentDialogBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_payment_dialog, container, false);
        final AddPaymentDialogViewModel addPaymentDialogViewModel = new ViewModelProvider(this, new AddPaymentViewFactory(getContext(), this, addPaymentListener)).get(AddPaymentDialogViewModel.class);
        binding.setViewModel(addPaymentDialogViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }
}