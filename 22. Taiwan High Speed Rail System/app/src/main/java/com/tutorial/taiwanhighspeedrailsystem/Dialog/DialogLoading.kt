package com.tutorial.taiwanhighspeedrailsystem.Dialog

import android.app.Activity
import android.app.AlertDialog
import com.tutorial.taiwanhighspeedrailsystem.R

class DialogLoading(private val mActivity: Activity){
    private lateinit var dialog: AlertDialog
    fun startLoading() {
        // set View
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_loading, null)
        // set Dialog
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }
    fun endLoading() {
        dialog.dismiss()
    }
}