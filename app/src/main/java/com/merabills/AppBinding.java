package com.merabills;

import android.widget.ArrayAdapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.merabills.adapters.PaymentAdapter;
import com.merabills.interfaces.OnRemoveListener;
import com.merabills.viewmodels.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppBinding {

    @BindingAdapter("setTypes")
    public static void setTypes(MaterialAutoCompleteTextView textView, List<String> types) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(textView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, types);
        textView.setAdapter(adapter);
    }

    @BindingAdapter("errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    @BindingAdapter({"setAdapter", "removeListener"})
    public static void setAdapter(RecyclerView recyclerView, ArrayList<PaymentViewModel> viewModels, OnRemoveListener listener) {
        PaymentAdapter paymentAdapter;
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            paymentAdapter = new PaymentAdapter(recyclerView.getContext(), viewModels, listener);
            recyclerView.setAdapter(paymentAdapter);
        } else {
            paymentAdapter = (PaymentAdapter) recyclerView.getAdapter();
            paymentAdapter.updateItems(viewModels);
        }
    }
}
