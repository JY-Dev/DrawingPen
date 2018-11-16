package com.jaeyoungkim.app.drawtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.SeekBar
import com.jaeyoungkim.app.drawtest.R
import kotlinx.android.synthetic.main.eraser_thick_dialog.*


class EraserThickDialog(context: Context, progress : Float, onConfirmCallBack: (Float) -> Unit) : Dialog(context){
    init {
        var thick = progress
        this.setContentView(R.layout.eraser_thick_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)
        thick_tv.text = progress.toInt().toString()
        thick_seek.progress = progress.toInt()
        thick_seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                thick_tv.text = progress.toString()
                thick = progress.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        thick_confirm_btn.setOnClickListener {
            onConfirmCallBack(thick)
            this.dismiss()
        }
        this.show()
    }
}