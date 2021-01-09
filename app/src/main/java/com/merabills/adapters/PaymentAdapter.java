package com.merabills.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.merabills.R;
import com.merabills.databinding.RowPaymentBinding;
import com.merabills.interfaces.OnRemoveListener;
import com.merabills.viewmodels.PaymentViewModel;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    private ArrayList<PaymentViewModel> payments;
    private final LayoutInflater layoutInflater;
    private final OnRemoveListener onRemoveListener;

    public PaymentAdapter(Context context, ArrayList<PaymentViewModel> payments, OnRemoveListener listener) {
        this.payments = payments;
        this.onRemoveListener = listener;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RowPaymentBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_payment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PaymentViewModel viewModel = payments.get(position);
        holder.bind(viewModel);
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void updateItems(ArrayList<PaymentViewModel> viewModels) {
        payments = viewModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final RowPaymentBinding paymentBinding;

        public ViewHolder(@NonNull RowPaymentBinding myListBinding) {
            super(myListBinding.getRoot());
            this.paymentBinding = myListBinding;
            myListBinding.getRoot().setOnClickListener(this);
        }

        public void bind(PaymentViewModel viewModel) {
            this.paymentBinding.setViewModel(viewModel);
            paymentBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            onRemoveListener.OnRemove(getAdapterPosition());
        }
    }
}
