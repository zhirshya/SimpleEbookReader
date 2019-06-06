package com.example.simpleebookreader

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


open class CommonMenuActivity : AppCompatActivity() {

    companion object{
        const val PICK_LOCAL_FILE = 25519
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.common_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.open_local_book -> {
//                val fileIntent = Intent(Intent.ACTION_GET_CONTENT,null, this, this::class.java)
                val fileIntent = Intent(Intent.ACTION_GET_CONTENT,null, this, CommonMenuActivity::class.java)
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
//                startActivityForResult(Intent.createChooser(pdfIntent, @string/file_picker_title))
                startActivityForResult(Intent.createChooser(fileIntent,"ᠹᠠᠢᠯ ᠰᠣᠩᠭᠣᠬᠤ"), PICK_LOCAL_FILE)
                true
            }
            R.id.search_local_books -> {

                true
            }
            R.id.show_bookmarks -> {

                true
            }
            R.id.help_feedback -> {
                showHelp()
                true
            }
            R.id.browse_internet_books -> {

                true
            }
            R.id.browse_lan_books -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_LOCAL_FILE && resultCode == Activity.RESULT_OK && data != null){
            val selectedFile: Uri? = data.data
            val intent = Intent(this@CommonMenuActivity, ViewActivity::class.java)
            intent.putExtra("ViewType", "localFile")
            intent.putExtra("FileUri", selectedFile.toString())
            startActivity(intent)

        }
    }

    fun showHelp(){

    }
}