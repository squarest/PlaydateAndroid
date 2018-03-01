package com.prince.logan.playdate.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.prince.logan.playdate.R;

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
}
