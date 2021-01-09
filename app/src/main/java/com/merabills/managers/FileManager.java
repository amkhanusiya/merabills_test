package com.merabills.managers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.merabills.interfaces.IFileManager;
import com.merabills.models.Payment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.inject.Inject;

public class FileManager implements IFileManager {
    final private Gson _gson = new Gson();

    private static final String FILE_NAME = "LastPayment.json";
    private ArrayList<Payment> _payments;
    protected Context context;


    @Inject
    FileManager(Context context) {
        this.context = context;
        fetchPayments();
    }

    @Override
    public void addPayments(ArrayList<Payment> payments) {
        this._payments = payments;
        Log.e(getClass().getSimpleName(), "FileManager add payment: " + _gson.toJson(payments));
        writeToFile(_gson.toJson(_payments));
    }

    @Override
    public ArrayList<Payment> getPayments() {
        return _payments;

    }

    private void fetchPayments() {
        final String _dataFromFile = readFromFile();
        if (!TextUtils.isEmpty(_dataFromFile)) {
            Log.e(getClass().getSimpleName(), "getPayments: " + _dataFromFile);
            _payments = _gson.fromJson(_dataFromFile, new TypeToken<ArrayList<Payment>>() {
            }.getType());
        } else {
            _payments = new ArrayList<>();
        }
    }


    @Override
    public boolean isPaymentTypeAvailable(ArrayList<Payment> payments, String paymentType) {
        boolean isAvailable = false;

        if (!payments.isEmpty()) {
            for (Payment payment : payments) {
                if (payment.getPaymentType().equalsIgnoreCase(paymentType)) {
                    isAvailable = true;
                    break;
                }
            }
        }

        return isAvailable;
    }

    private void writeToFile(String data) {
        try {
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(getClass().getSimpleName(), "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Can not read file: " + e.toString());
        }

        return ret;
    }
}
