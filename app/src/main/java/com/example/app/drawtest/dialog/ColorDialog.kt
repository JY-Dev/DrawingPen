package com.example.app.drawtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.SeekBar
import android.widget.TextView
import com.example.app.drawtest.R
import kotlinx.android.synthetic.main.color_dialog.*

class ColorDialog(context: Context, onConfirmCallBack: (Int, Int, Int, String) -> Unit, red: Int, green: Int, blue: Int) :
    Dialog(context) {
    private var red = 0
    private var green = 0
    private var blue = 0
    private var colorCode:String = ""
    private var sss = 0L
    private val drawable: Drawable? = ContextCompat.getDrawable(context,
        R.drawable.roundsquareleft
    )

    init {
        this.setContentView(R.layout.color_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        this.red = red
        this.green = green
        this.blue = blue



        // Text set
        color_red_seek_tv.text = red.toString()
        color_green_seek_tv.text = green.toString()
        color_blue_seek_tv.text = blue.toString()

        colorCode(color_tv)

        //drawable set
        drawableSet(red, green, blue)

        //seekbar progress set
        color_red_seek.progress = red
        color_green_seek.progress = green
        color_blue_seek.progress = blue

        //seekbar listener
        color_red_seek.setOnSeekBarChangeListener(seekbarListner(ColorEnum.RED.getValue()))
        color_green_seek.setOnSeekBarChangeListener(seekbarListner(ColorEnum.GREEN.getValue()))
        color_blue_seek.setOnSeekBarChangeListener(seekbarListner(ColorEnum.BLUE.getValue()))

        color_dialog_confirm_btn.setOnClickListener {
            onConfirmCallBack(this.red, this.green, this.blue,this.colorCode)
            this.dismiss()
        }

        this.show()
    }

    fun seekbarListner(count: Int) = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p2: Boolean) {
            when (count) {
                ColorEnum.RED.getValue() -> {
                    red = progress
                    color_red_seek_tv.text = progress.toString()
                }
                ColorEnum.GREEN.getValue() -> {
                    green = progress
                    color_green_seek_tv.text = progress.toString()
                }
                ColorEnum.BLUE.getValue() -> {
                    blue = progress
                    color_blue_seek_tv.text = progress.toString()
                }
            }
            drawableSet(red, green, blue)
            colorCode(color_tv)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    }

    fun drawableSet(red: Int, green: Int, blue: Int) {
        drawable?.setColorFilter(Color.rgb(red, green, blue), PorterDuff.Mode.MULTIPLY)
        color_view.setBackgroundDrawable(drawable)
    }

    fun colorCode(tv:TextView){
        var redCode = this.red.toString(16)
        var greenCode = this.green.toString(16)
        var blueCode =  this.blue.toString(16)
        if (redCode == "0") redCode += "0"
        if (greenCode == "0") greenCode += "0"
        if (blueCode == "0") blueCode += "0"
        this.colorCode = "#$redCode$greenCode$blueCode"
        tv.text = this.colorCode
    }

}

enum class ColorEnum(val i: Int) {
    RED(0),
    GREEN(1),
    BLUE(2);

    fun getValue() = i
}