package com.museumonline.museummm

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.museumonline.museummm.R
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class LoadingDialog(private var activity: Activity) {
    private var alertDialog: AlertDialog? = null

//    companion object {
//        private var INSTANCE: LoadingDialog? = null
//
//        fun getInstance(activity: Activity): LoadingDialog? {
//            if (INSTANCE != null) {
//                return INSTANCE
//            } else {
//                INSTANCE = LoadingDialog(activity)
//            }
//            return INSTANCE
//        }
//    }

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(
            layoutInflater.inflate(
                R.layout.dialog_loading,
                RelativeLayout(activity),
                false
            )
        )


        builder.setCancelable(false)

        alertDialog = builder.create()
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 60)
        alertDialog!!.window?.setBackgroundDrawable(inset)
        alertDialog?.show()
    }

    fun dismissDialog() {
        alertDialog?.dismiss()
    }

}