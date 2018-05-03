package com.login_signup_screendesign_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by nguyendangquan on 14/04/2018.
 */

public class Course_Frament extends Fragment implements View.OnClickListener {
    private static View view;
    private static Button all_course ;
    private static Button check_in ;
    private static Button scan ;
    private static ListView listview ;
    private static FragmentManager fragmentManager;
    private final String arr[] = {"Active Learning","PPL"} ;
    //private ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.activity_list_item,arr) ;

    public Course_Frament(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.course, container,
                false);


        initViews();
        setListeners();
        return view ;
    }
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        all_course =(Button) view.findViewById(R.id.bt2) ;
        check_in = (Button) view.findViewById(R.id.bt3);
        scan = (Button) view.findViewById(R.id.scan);
        listview = (ListView) view.findViewById(R.id.listview) ;
    }
    private void setListeners() {
        all_course.setOnClickListener(this);
        check_in.setOnClickListener(this);
        scan.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt2:
                //listview.setAdapter(adapter);
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new MainWork_Fragment(),
                                Utils.MainWork_Fragment).commit();
                break ;
            case R.id.bt3 :
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new Check_in(),
                                Utils.Check_in).commit();
                break ;
            case R.id.scan :
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ScanQRCode(),
                                Utils.scan_qrcode).commit();
                break ;
        }
    }

}
