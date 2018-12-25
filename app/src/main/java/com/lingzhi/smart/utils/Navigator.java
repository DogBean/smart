package com.lingzhi.smart.utils;

import android.content.Context;
import android.content.Intent;

import com.lingzhi.smart.module.guide.GuideActivity;
import com.lingzhi.smart.module.main.MainActivity;
import com.lingzhi.smart.module.music.MusicPlayerActivity;
import com.lingzhi.smart.module.playList.PlayListActivity;
import com.lingzhi.smart.module.requisite.RequisiteActivity;
import com.lingzhi.smart.module.sort.SortActivity;


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

    public static void navigateToMusicPlay(Context context) {
        if (context != null) {
            Intent intentToLaunch = MusicPlayerActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }


    public static void navigateToMusicPlayList(Context context, int oid, String albumName, String albumPath) {
        if (context != null) {
            Intent intentToLaunch = PlayListActivity.getCallingIntent(context, oid, albumName, albumPath);
            context.startActivity(intentToLaunch);
        }
    }

    public static void navigateToGuide(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, GuideActivity.class));
        }
    }

    public static void navigateToMain(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    public static void navigateRequisite(Context context) {
        if (context != null) {
            Intent intentToLaunch = RequisiteActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}