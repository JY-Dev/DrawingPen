package com.example.app.drawtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.app.drawtest.R
import kotlinx.android.synthetic.main.input_dialog.*

class InputDialog(context: Context, onConfirmCallBack : (String) -> Unit ) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.input_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        input_dialog_btn.setOnClickListener {
            onConfirmCallBack(input_dialog_et.text.toString())
            this.dismiss()
        }
        cancel_dialog_btn.setOnClickListener {
            this.dismiss()
        }
        this.show()
    }
}