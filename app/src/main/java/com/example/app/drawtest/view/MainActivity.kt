package com.example.app.drawtest.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.app.drawtest.dialog.ColorDialog
import com.example.app.drawtest.dialog.InputDialog
import com.example.app.drawtest.R
import com.example.app.drawtest.dialog.ThickDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private lateinit var fos: FileOutputStream
    private lateinit var folder: File
    private lateinit var file: File
    private var red = 0
    private var green = 0
    private var blue = 0
    private var thick: Float = 13f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mview = MView(this)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            }
        }

        color_btn.setOnClickListener {
            ColorDialog(this, { red, green, blue, colorCode ->
                this.red = red
                this.green = green
                this.blue = blue
                mview.color = colorCode
            }, red, green, blue)
        }

        clear_btn.setOnClickListener {
            mview.point.clear()
            mview.postInvalidate()
        }
        capture_btn.setOnClickListener {
            InputDialog(this) { it ->
                mview.buildDrawingCache()
                bitmap = mview.drawingCache
                folder = File(getExternalStorageDirectory().toString() + File.separator + "DrawingImg")
                file =
                        File(getExternalStorageDirectory().toString() + File.separator + "/DrawingImg/$it.jpeg")
                try {
                    if (!folder.exists()) folder.mkdirs()
                    if (!file.exists()) {
                        fos = FileOutputStream(getExternalStorageDirectory().toString() + "/DrawingImg/$it.jpeg")
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        mview.destroyDrawingCache()
                        Toast.makeText(this, "파일이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, "같은이름의 파일이 이미존재합니다.", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }

        }
        thick_btn.setOnClickListener {
            ThickDialog(this, thick) { thick ->
                mview.paint = Paint()
                mview.paint.strokeWidth = thick
                mview.thick = thick
                this.thick = thick
            }
        }

        view.addView(mview)
    }

}

