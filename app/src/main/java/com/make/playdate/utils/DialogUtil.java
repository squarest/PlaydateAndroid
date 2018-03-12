package com.make.playdate.utils;

import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.make.playdate.R;

/**
 * Created by Chudofom on 30.12.17.
 */

public class DialogUtil {
    private Context context;

    public DialogUtil(Context context) {
        this.context = context;
    }

    public ProgressDialog getProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        return progressDialog;
    }

    public AlertDialog getAlertDialog(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", (dialog, which) -> dialog.cancel());

        return alertDialog.create();
    }

    public AlertDialog getAlertDialog(String title, String msg, AlertDialog.OnClickListener positiveClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", positiveClickListener);
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return alertDialog.create();
    }
}
