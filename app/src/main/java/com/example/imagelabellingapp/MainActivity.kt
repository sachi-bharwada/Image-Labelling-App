package com.example.imagelabellingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var Gate = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var  text1: TextView = findViewById(R.id.text1)

        val image: ImageView = findViewById(R.id.imageView3)
        val button: Button = findViewById(R.id.imagelabelling)
        val image_name = "sample.jpg"
        val bitmap: Bitmap? = assetsToBitmap(image_name)
        bitmap?.apply { image.setImageBitmap(this) }
        button.setOnClickListener {

            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val input_image = InputImage.fromBitmap(bitmap!!,0)
            labeler.process(input_image).addOnSuccessListener { labels -> FindImageContetns(labels,text1) }
            Gate = 1

        }

        if (Gate == 0){
            button.alpha = 1.0F
        }
        else {
            button.alpha = 0.6F
        }
    }

    private fun FindImageContetns(labels: List<ImageLabel>, text1: TextView) {
        var outputText: String? = ""
        for (label in labels){
            outputText += "class=${label.text}:conf=${label.confidence}\n"
        }
        text1.text = outputText
        text1.alpha = 0.6F
        Gate = 0
    }


    fun Context.assetsToBitmap(imagename: String): Bitmap?{
        return try{with(assets.open(imagename)){ BitmapFactory.decodeStream(this)}} catch(e: IOException) {null}
    }
}