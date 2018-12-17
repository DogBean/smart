package com.smart.linguoyong.smart.utils;

import android.content.Context;
import android.content.Intent;

import com.smart.linguoyong.smart.module.sort.SortActivity;



/**
 * Class used to navigate through the application.
 */
public class Navigator {

    public Navigator() {
        //empty
    }

    public static void navigateToSort(Context context, int userId) {
        if (context != null) {
            Intent intentToLaunch = SortActivity.getCallingIntent(context, userId);
            context.startActivity(intentToLaunch);
        }
    }
}