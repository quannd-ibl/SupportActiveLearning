package com.login_signup_screendesign_demo;


import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService ;
import org.web3j.utils.Numeric;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

import static java.util.Arrays.asList;


/**
 * Created by DANG QUAN on 3/2/2018.
 */

public class MainWork_Fragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static CheckBox a,b,c,d ;
    private static Button submit ;
    private static Button result ;
    private static TextView mTextView ;
    private static TextView rTextView ;
    public static final int NETWORK_ID_MAIN = 1;
    public static final int NETWORK_ID_ROPSTEN = 3;
    public static String infuraAccessToken = "OeZsMeqktJHllYIpzsYI";
    public static String infuraTestNetRopstenUrl = "https://ropsten.infura.io/" + infuraAccessToken;
    private static EditText edt;
    public static final BigInteger gasPrice = BigInteger.valueOf(450000000);
    public static final BigInteger gasLimit = BigInteger.valueOf(3300000);
    public static final BigInteger value = BigInteger.valueOf(0) ;
    public static final String addressSmartContract = "0x271ca06a08ade675745099eec6eb1ad60a0ef1f3" ;
    public static final String privateKey = "0xbd85535e41c5c53bd43e6193f7dc572d7bc97cd34bee97c1ac08c1ea88bc5905" ;

   // private static WebView wb
    public static String apikey = "K7BEBSHYDRFJJ2J8NSPYNWHD1B7GGKVEKD" ;
    public MainWork_Fragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_work, container,
               false);
        //wb=(WebView) view.findViewById(R.id.web_view);
        //WebSettings webSettings=wb.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //wb.loadUrl("https://etherscan.io");
        initViews();
        setListeners();
        return view ;
    }
    private void initViews() {
        a = (CheckBox) view.findViewById(R.id.checkBox);
        b = (CheckBox) view.findViewById(R.id.checkBox2);
        c = (CheckBox) view.findViewById(R.id.checkBox3);
        d = (CheckBox) view.findViewById(R.id.checkBox4);
        submit = (Button) view.findViewById(R.id.button) ;
        result = (Button) view.findViewById(R.id.result) ;
        mTextView =  (TextView) view.findViewById(R.id.text);
        rTextView =  (TextView) view.findViewById(R.id.textView5);
        edt = (EditText)  view.findViewById(R.id.edt) ;
    }
    private void setListeners() {
        result.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //Web3j web3 = Web3jFactory.build(new HttpService(infuraTestNetRopstenUrl));
                String url = "https://api-ropsten.etherscan.io/api?module=proxy&action=eth_getTransactionCount&address=0xaF03c96a72f7F9cB51b643f7269eac7F1434962B&tag=latest&apikey=K7BEBSHYDRFJJ2J8NSPYNWHD1B7GGKVEKD" ;
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(responseBody != null) {
                            try {
                                JSONObject result = new JSONObject(new String(responseBody));
                                String data = result.getString("result") ;
                                int nonce = Integer.decode(data);
                                String answer;
                                if (a.isChecked()){
                                    answer = "A";
                                } else if(b.isChecked()) {
                                    answer = "B" ;
                                }
                                else if(c.isChecked()) {
                                    answer = "C";
                                } else {
                                    answer = "D";
                                }
                                Type index = new Uint256(BigInteger.valueOf(0));
                                Type address = new Address("0xEBc3D75CCB8325Cb016af7d1A46e0458A48d7a44");
                                Type content = new Utf8String(answer) ;
                                Function _answer = new Function(
                                        "answer",  // function we're calling
                                        asList(address,index,content),
                                        Collections.<TypeReference<?>>emptyList()
                                );

                                String encodedFunction = FunctionEncoder.encode(_answer) ;
                                RawTransaction rawTransaction = RawTransaction.createTransaction(BigInteger.valueOf(nonce), gasPrice,gasLimit , addressSmartContract, value,encodedFunction);

                                Credentials credentials = Credentials.create(privateKey) ;
                                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                                String hexValue = Numeric.toHexString(signedMessage);
                                String url = "https://api-ropsten.etherscan.io/api?module=proxy&action=eth_sendRawTransaction&hex="+ hexValue +"&apikey=K7BEBSHYDRFJJ2J8NSPYNWHD1B7GGKVEKD" ;
                                AsyncHttpClient client = new AsyncHttpClient();
                                client.post(url, new
                                        AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                if(responseBody != null) {
                                                    mTextView.setText(new String(responseBody));
                                                }

                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                            }
                                        });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }

                });
                //String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            case  R.id.result :
                Type index = new Uint256(BigInteger.valueOf(0));
                Type address = new Address("0xEBc3D75CCB8325Cb016af7d1A46e0458A48d7a44");
                Function _result = new Function(
                        "result",  // function we're calling
                        asList(address,index),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {},
                                new TypeReference<Utf8String>() { },
                                new TypeReference<Bool>() {}
                        )
                );
                String encodeResult = FunctionEncoder.encode(_result) ;
                String url1 = "https://api-ropsten.etherscan.io/api?module=proxy&action=eth_call&to="+addressSmartContract+"&data="+encodeResult+"&tag=latest&apikey=K7BEBSHYDRFJJ2J8NSPYNWHD1B7GGKVEKD" ;
                AsyncHttpClient client1 = new AsyncHttpClient();
                client1.get(url1, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(responseBody != null) {
                            try {

                                JSONObject result = new JSONObject(new String(responseBody));
                                String inputData = result.getString("result");
                                Function _result = new Function(
                                        "result",  // function we're calling
                                        Arrays.<Type>asList(),
                                        Arrays.<TypeReference<?>>asList(
                                                new TypeReference<Address>() {},
                                                new TypeReference<Uint256>() {},
                                                new TypeReference<Bool>() {}
                                        )
                                );

                                List s =  FunctionReturnDecoder.decode(inputData,_result.getOutputParameters());
                                boolean res = (s.get(2).hashCode() != 0)  ;
                                rTextView.setText("Address: "+s.get(0).toString()+"  "+
                                        "index :"+Integer.toString(s.get(1).hashCode())+"\n"+
                                       "result: "+ res);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
        }
    }


}
