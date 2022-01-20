package com.example.memesharing

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var Currentimageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
   private fun loadMeme() {
       progressbar.visibility=View.VISIBLE

       val url = "https://meme-api.herokuapp.com/gimme"

           val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
           { response ->
                Currentimageurl=response.getString("url")
               Glide.with(this).load(Currentimageurl).listener(object:RequestListener<Drawable>{
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbar.visibility=View.GONE
                       return false


                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbar.visibility=View.GONE
                        return false

                   }
               }).into(memeImageview)
           },
           { error ->
               Toast.makeText(this, "Opps Contact Shekhar ", Toast.LENGTH_SHORT).show()

           })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
   }


    fun shareMeme(view: android.view.View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey checkout this meme from shekhar's app $Currentimageurl")
        val chooser=Intent.createChooser(intent,"share this meme using ")
        startActivity(chooser)
    }
    fun NextMeme(view: android.view.View) {
        loadMeme()
    }
}