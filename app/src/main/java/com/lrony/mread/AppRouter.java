package com.lrony.mread;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrony.mread.data.bean.Book;
import com.lrony.mread.presentation.book.BookDetailActivity;
import com.lrony.mread.util.DensityUtil;

/**
 * Created by lrony on 2018/4/7.
 * app路由，界面跳转帮助类，所有的界面跳转通过此类进行跳转,包括组件交互
 */
public class AppRouter {

    /**
     * 获取全局加载dialog
     */
    public static Dialog getLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.AlertDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        dialog.setContentView(view, new ViewGroup.LayoutParams(DensityUtil.dp2px(context, 96), DensityUtil.dp2px(context, 96)));
        return dialog;
    }

    /**
     * 跳转到当前应用设置界面
     */
    public static void showAppDetailSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

//    public static void showBookDetailActivity(Context context, Book book) {
//        context.startActivity(BookDetailActivity.newIntent(context, book));
//    }
}
