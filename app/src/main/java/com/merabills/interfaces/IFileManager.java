package com.merabills.interfaces;

import com.merabills.models.Payment;

import java.util.ArrayList;

public interface IFileManager {
    void addPayments(ArrayList<Payment> payments);

    ArrayList<Payment> getPayments();

    boolean isPaymentTypeAvailable(ArrayList<Payment> payments, String paymentType);
}
