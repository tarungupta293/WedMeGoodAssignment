package com.example.tarun.wedmegoodassignment.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static void setFragment(int containerViewId, FragmentManager fragmentManager, Fragment fragment, boolean addBackStack) {
        try {
            if (!addBackStack) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment);
            if (addBackStack) {
                fragmentTransaction.addToBackStack("");
            }

            fragmentTransaction.commit();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static String changeDateFormate(String date,String fromChangeDateFormat, String toChangeDateFormat){

        SimpleDateFormat dateFormat = new SimpleDateFormat(fromChangeDateFormat);
        String dateString = null;
        try {
            Date d = dateFormat.parse(date);
            dateFormat.applyPattern(toChangeDateFormat);
            dateString = dateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }
}
