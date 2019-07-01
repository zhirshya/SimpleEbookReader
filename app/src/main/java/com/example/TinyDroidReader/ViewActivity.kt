package com.example.TinyDroidReader

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reading_pane.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.math.*

//class ViewActivity : AppCompatActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnTouchListener
class ViewActivity : AppCompatActivity(), View.OnTouchListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reading_pane)

        if (intent != null) {
            val viewType: String = intent.getStringExtra("ViewType")
            if (!TextUtils.isEmpty(viewType)) {
                if (viewType.equals("localFile")) {
                    val selectedFile: Uri = Uri.parse(intent.getStringExtra("FileUri"))
                    val ext: String = selectedFile.toString()?.substring(selectedFile.toString()?.lastIndexOf('.')+1)
                    //pdf
                    if (selectedFile != null) {
                        when {
                            ext?.equals("pdf",true) ->
                                pdfView.fromUri(selectedFile)
                                    .password(null)
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .enableDoubletap(true)
                                    .swipeHorizontal(true)
                                    .pageSnap(true)
                                    .autoSpacing(true)
                                    .pageFling(true)
                                    .onDraw { canvas, pageWidth, pageHeight, displayedPage ->

                                    }.onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->

                                    }.onPageChange { page, pageCount ->

                                    }.onPageError { page, t ->
                                        Toast.makeText(
                                            this@ViewActivity,
                                            "Error while opening page $page",
                                            Toast.LENGTH_SHORT
                                        )
                                        Log.d("ERROR", t.localizedMessage.toString())
                                    }.onTap { false }
                                    .onRender { page ->
                                        pdfView.fitToWidth(page)
                                    }.enableAnnotationRendering(true)
                                    .load()
                            ext.equals("epub", true) -> {}
                            ext.equals("txt",true) ->
                                try {
                                    var inputStream: InputStream = getContentResolver().openInputStream(selectedFile)
                                    if (inputStream != null) {
                                        val br: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                                        var content: String = ""
                                        var line: String? = null
                                        while ({ line = br.readLine(); line }() != null) {
                                            content += line + '\n'
                                        }
                                        defaultView.setText(content)
                                        defaultView.setTextSize(mRatio + 13)
                                    }
                                } catch (e: Exception) {
                                    defaultView.setText("Sorry, an internal error occurred.")
                                }
                            else -> defaultView.setText("Sorry, file type is unclear, unsure how to open the selected file.")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.viewer_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.find_in_current_book -> {

                true
            }
            R.id.bookmark_current_page -> {

                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
/*
    override fun onDown(event: MotionEvent): Boolean{
        return true
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onScroll(event1: MotionEvent, event2: MotionEvent, distanceX: Float, distanceY: Float): Boolean{
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }
*/

    /*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    */

/*
//    var gestureDetector: GestureDetectorCompat? = null
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor: Float = 1.0f

//    https://github.com/pftbest/zoom-example/blob/master/src/main/java/org/tmpfs/zoomtest/ZoomFrame.kt
    private inner class ScaleListener: ScaleGestureDetector.SimpleOnScaleGestureListener() {
*/

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.getPointerCount() == 2) {
            var action: Int = event.getAction()
            var pureAction: Int = action and MotionEvent.ACTION_MASK
            if (pureAction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event)
                mBaseRatio = mRatio
            } else {
                var delta: Float = (getDistance(event) - mBaseDist) / STEP
                var multi: Float = 2.toFloat().pow(delta)
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi))
                defaultView.setTextSize(mRatio + 13)
            }
        }
        return true
    }

    private fun getDistance(event: MotionEvent): Int {
        /*var dx: Int = (event.getX(0) - event.getX(1)).toInt()
        var dy: Int = (event.getY(0) - event.getY(1)).toInt()
        return sqrt((dx*dx + dy*dy).toDouble()).toInt()*/
        return sqrt((event.getX(0) - event.getX(1)).pow(2) + (event.getY(0) - event.getY(1)).pow(2)).toInt()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }

    private var mBaseDist: Int = 0
    private var mBaseRatio: Float = 1.0f
    private var mRatio: Float = 1.0f
    private val STEP: Float = 200.0f
}