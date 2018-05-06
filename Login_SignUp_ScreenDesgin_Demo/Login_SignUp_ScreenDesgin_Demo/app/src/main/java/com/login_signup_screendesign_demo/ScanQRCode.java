package com.login_signup_screendesign_demo;


import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.zxing.Result;



import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by nguyendangquan on 30/04/2018.
 */

public class ScanQRCode extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    public ScanQRCode() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
