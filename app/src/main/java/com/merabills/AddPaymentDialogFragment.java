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

import com.merabills.databinding.FragmentAddPaymentDialogBinding;
import com.merabills.interfaces.OnAddPaymentListener;
import com.merabills.viewmodels.AddPaymentDialogViewModel;

import java.util.Objects;

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
        binding.setViewModel(new AddPaymentDialogViewModel(getContext(), this, addPaymentListener));
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
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