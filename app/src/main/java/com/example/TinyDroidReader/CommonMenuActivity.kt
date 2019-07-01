package com.example.TinyDroidReader

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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.open_local_book -> {
                val fileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//                val fileIntent = Intent(Intent.ACTION_GET_CONTENT,null, this, this::class.java)
//                val fileIntent = Intent(Intent.ACTION_GET_CONTENT,null, this, CommonMenuActivity::class.java)
                //
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
                fileIntent.setType("*/*")
//                startActivityForResult(Intent.createChooser(pdfIntent, @string/file_picker_title))
//                super.onActivityResult(PICK_LOCAL_FILE, Activity.RESULT_OK, fileIntent)
                startActivityForResult(Intent.createChooser(fileIntent,"ᠹᠠᠢᠯ ᠰᠣᠩᠭᠣᠬᠤ"), PICK_LOCAL_FILE)
//                this.openFile(type)
//                startActivity(Intent.createChooser(fileIntent,"ᠹᠠᠢᠯ ᠰᠣᠩᠭᠣᠬᠤ"), PICK_LOCAL_FILE) //type mismatch: required bundle, found int
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

/*
    fun openFile(requestCode: Int)
    {
        //browser open
    }
*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?){
        super.onActivityResult(requestCode, resultCode, resultData)
        if(requestCode == PICK_LOCAL_FILE /*&& resultCode == Activity.RESULT_OK && resultData != null*/){
            val selectedFile: Uri? = resultData?.data
            val intent = Intent(this@CommonMenuActivity, ViewActivity::class.java)
            intent.putExtra("ViewType", "localFile")
            intent.putExtra("FileUri", selectedFile.toString())
            startActivity(intent)

        }
    }

    fun showHelp(){

    }
}