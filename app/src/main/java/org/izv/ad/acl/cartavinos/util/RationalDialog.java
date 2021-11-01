package org.izv.ad.acl.cartavinos.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.izv.ad.acl.cartavinos.R;

//uso esta clase para mostar dialogos en caso de error o de necesitarlos
public class RationalDialog {

    public static void showRationaleDialog (Context context, String title, String message) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alertOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No hagas nada
                    }
                });
        builder.create().show();
    }
}
