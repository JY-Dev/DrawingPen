package com.jaeyoungkim.app.drawtest.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jaeyoungkim.app.drawtest.dialog.ColorDialog
import com.jaeyoungkim.app.drawtest.dialog.InputDialog
import com.jaeyoungkim.app.drawtest.R
import com.jaeyoungkim.app.drawtest.dialog.EraserThickDialog
import com.jaeyoungkim.app.drawtest.dialog.ThickDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private lateinit var fos: FileOutputStream
    private lateinit var folder: File
    private lateinit var file: File
    private var backKeyPressedTime = 0L
    private var red = 0
    private var green = 0
    private var blue = 0
    private var thick: Float = 13f
    private var eraserThick: Float = 13f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mview = MView(this)
        setContentView(R.layout.activity_main)


        // 퍼미션 처리
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

        // 새그림
        clear_btn.setOnClickListener {
            mview.point.clear()
            mview.postInvalidate()
        }

        // 선두께
        thick_btn.setOnClickListener {
            eraser_btn.isChecked = false
            ThickDialog(this, thick) { thick ->
                mview.paint = Paint()
                mview.tool = "pen"
                mview.paint.strokeWidth = thick
                mview.thick = thick
                this.thick = thick
                mview.postInvalidate()
            }
        }

        // 선색상
        color_btn.setOnClickListener {
            ColorDialog(this, { red, green, blue, colorCode ->
                this.red = red
                this.green = green
                this.blue = blue
                mview.color = colorCode
                mview.postInvalidate()
            }, red, green, blue)
        }

        // 지우개
        eraser_btn.setOnClickListener {
            if (eraser_btn.isChecked) EraserThickDialog(this, eraserThick) { eraserThick ->
                this.eraserThick = eraserThick
                mview.tool = "eraser"
                mview.eraserThick = eraserThick
            } else mview.tool = "pen"

            mview.postInvalidate()
        }

        // 저장
        capture_btn.setOnClickListener {
            InputDialog(this) { it ->
                mview.buildDrawingCache()
                bitmap = mview.drawingCache
                folder = File(getExternalStorageDirectory().toString() + File.separator + "DrawingImg")
                file =
                        File(getExternalStorageDirectory().toString() + File.separator + "/DrawingImg/$it.png")
                try {
                    if (!folder.exists()) folder.mkdirs()
                    if (!file.exists()) {
                        fos = FileOutputStream(getExternalStorageDirectory().toString() + "/DrawingImg/$it.png")
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        mview.destroyDrawingCache()
                        Toast.makeText(this, "파일이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, "같은이름의 파일이 이미존재합니다.", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }

        }

        more_btn.setOnClickListener {
            Toast.makeText(this, "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show()
        }

        // 그리기
        view.addView(mview)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로가기' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }
}

