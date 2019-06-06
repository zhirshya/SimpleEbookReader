package com.example.simpleebookreader

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reading_pane.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reading_pane)

        if (intent != null) {
            val viewType: String = intent.getStringExtra("ViewType")
            if (!TextUtils.isEmpty(viewType)) {
                if (viewType.equals("localFile")) {
                    val selectedFile: Uri = Uri.parse(intent.getStringExtra("FileUri"))
                    //pdf
                    if (selectedFile != null) {
                        if (selectedFile.toString()?.endsWith(".pdf")) {
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
                        } //epub
                        else if (selectedFile.toString()?.endsWith(".epub")) {

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

}
