package com.example.choco.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.choco.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

@SuppressLint("InflateParams")
object MessageViewer {
    private var mDialog: Dialog? = null
    private var mToast: Toast? = null
    fun createDialog(context: Context?, layout: Int, cancelable: Boolean): View {
        hideDialog()
        val view = LayoutInflater.from(context).inflate(layout, null)
        mDialog = MaterialAlertDialogBuilder(context!!)
            .setView(view)
            .setCancelable(cancelable)
            .show()
        return view
    }

    fun startAlertDialog(activity: Activity, content: String, clickListener: DialogClickListener?) {
        startAlertDialog(activity, content, activity.getString(R.string.ok), clickListener)
    }

    private fun startAlertDialog(
        activity: Activity, content: String, buttonText: String,
        clickListener: DialogClickListener?
    ) {
        hideDialog()
        val view = createDialog(activity, R.layout.dialog_alert_layout, false)
        (view.findViewById<View>(R.id.content_text_view) as TextView).text = content
        val okButton = view.findViewById<Button>(R.id.btn_ok)
        okButton.text = buttonText
        okButton.setOnClickListener { v: View? ->
            dismissDialog()
            clickListener?.okBtnClicked()
        }
    }

    fun startConfirmDialog(
        activity: Activity, okBtnTxtResId: Int,
        contentResId: Int,
        clickListener: DialogClickListener?
    ) {
        startConfirmDialog(
            activity, activity.getString(okBtnTxtResId),
            activity.getString(contentResId), clickListener
        )
    }

    fun startConfirmDialog(
        activity: Activity, okBtnTxt: String?, content: String?,
        clickListener: DialogClickListener?
    ) {
        startConfirmDialog(activity, null, content, okBtnTxt, null, clickListener)
    }

    fun startConfirmDialog(
        activity: Activity, title: String?,
        content: String?, okBtnTxt: String?, cancelBtnTxt: String?,
        clickListener: DialogClickListener?
    ) {
        var okBtnTxt = okBtnTxt
        hideDialog()
        val view = createDialog(activity, R.layout.dialog_confirm_layout, true)
        if (!TextUtils.isEmpty(title)) {
            view.findViewById<View>(R.id.title_tex_view).visibility = View.VISIBLE
            (view.findViewById<View>(R.id.title_tex_view) as TextView).text = title
        }
        (view.findViewById<View>(R.id.content_text_view) as TextView).text = content
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        if (!InputHelper.isEmpty(cancelBtnTxt)) {
            cancelButton.text = cancelBtnTxt
        }
        cancelButton.setOnClickListener { v: View? ->
            dismissDialog()
            clickListener?.cancelBtnClicked()
        }
        val okButton = view.findViewById<Button>(R.id.btn_ok)
        if (InputHelper.isEmpty(okBtnTxt)) {
            okBtnTxt = activity.getString(R.string.ok)
        }
        okButton.text = okBtnTxt
        okButton.setOnClickListener { v: View? ->
            dismissDialog()
            clickListener?.okBtnClicked()
        }
    }

    private fun showDialog() {
        try {
            mDialog!!.show()
        } catch (e: Exception) {

        }
    }

    private fun hideDialog() {
        if (mDialog != null) {
            mDialog!!.hide()
            mDialog = null
        }
    }

    fun dismissDialog() {
        try {
            mDialog!!.dismiss()
            mDialog = null
        } catch (e: Exception) {

        }
    }

    fun startToast(activity: Activity?, textId: Int) {
        if (activity == null) return
        startToast(activity, activity.getString(textId))
    }

    fun startToast(activity: Activity?, text: String?) {
        if (activity == null) return
        startToast(activity, text, Toast.LENGTH_SHORT)
    }

    private fun startToast(activity: Activity?, text: String?, duration: Int) {
        if (activity == null) return
        cancelToast()
        val toast = Toast(activity)
        val view: View = activity.layoutInflater.inflate(R.layout.layout_custom_black_toast, null)
        val textView = view.findViewById<TextView>(R.id.text_view)
        textView.text = text
        toast.setView(view)
        toast.duration = duration
        mToast = toast
        toast.show()
    }

    private fun cancelToast() {
        if (mToast != null) {
            mToast!!.cancel()
        }
    }

    interface DialogClickListener {
        fun okBtnClicked()
        fun cancelBtnClicked() {}
    }
}
