package com.example.androidproficiencyexcersciseinternship

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants

class MainActivity : AppCompatActivity() {
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var guri: Uri? = null
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                guri=uri
                Log.e("TAG", "Just Seeing $guri $uri")
                imageView.setImageURI(uri)
            }
        }
        btn_edit.visibility= View.INVISIBLE
        btn_takeselfie.setOnClickListener{
            launcher.launch(
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .cropOval()	    		//Allow dimmed layer to have a circle inside
                    .cropFreeStyle()
                    .cameraOnly() // or galleryOnly()
                    .createIntent()
            )
            Handler(Looper.getMainLooper()).postDelayed({
                btn_edit.visibility=View.VISIBLE
            },1000)
        }

        btn_uploadfromgalley.setOnClickListener{
            launcher.launch(
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .cropOval()	    		//Allow dimmed layer to have a circle inside
                    .cropFreeStyle()
                    .galleryOnly()
                    .createIntent()
            )
            Handler(Looper.getMainLooper()).postDelayed({
                btn_edit.visibility=View.VISIBLE
            },1000)
        }
        btn_edit.setOnClickListener {
            val editIntent = Intent(this,DsPhotoEditorActivity::class.java)
            Log.e("TAG", "Just Seeing $guri")
            editIntent.setData(guri)
            val toolstohide = arrayOf(DsPhotoEditorActivity.TOOL_ORIENTATION,DsPhotoEditorActivity.TOOL_CROP)
            editIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,toolstohide)
            startActivityForResult(editIntent, 200)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if(requestCode==200){
                val outputUri = data?.data
                imageView.setImageURI(outputUri)
            }
        }
    }

}