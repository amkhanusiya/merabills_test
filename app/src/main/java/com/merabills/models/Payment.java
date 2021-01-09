package com.merabills.models;

public class Payment {
    private String paymentType;
    private String amount;
    private String provider;
    private String transactionRef;

    public Payment(String paymentType, String amount, String provider, String transactionRef) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.provider = provider;
        this.transactionRef = transactionRef;
    }


    public String getPaymentType() {
        return paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public String getProvider() {
        return provider;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public static Payment addPayment(String paymentType, String amount, String provider, String transactionRef) {
        return new Payment(paymentType, amount, provider, transactionRef);
    }

    public static Payment addCashPayment(String paymentType, String amount) {
        return addPayment(paymentType, amount, "", "");
    }
}
