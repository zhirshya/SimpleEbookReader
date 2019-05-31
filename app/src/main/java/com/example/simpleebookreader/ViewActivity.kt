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
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        if (intent != null) {
            val viewType: String = intent.getStringExtra("ViewType")
            if (!TextUtils.isEmpty(viewType)) {
                if (viewType.equals("localFile")) {
                    //pdf
                    if (intent.getType().equals("application/pdf")) {
                        val selectedPdf: Uri = Uri.parse(intent.getStringExtra("FileUri"))
                        pdfView.fromUri(selectedPdf)
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
                                    "Error while opening page " + page,
                                    Toast.LENGTH_SHORT
                                )
                                Log.d("ERROR", "" + t.localizedMessage)
                            }.onTap { false }
                            .onRender {
                                page -> pdfView.fitToWidth(page)
                            }.enableAnnotationRendering(true)
                            .load()
                    } //epub
                    else if(intent.getType().equals("application/epub")){

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
