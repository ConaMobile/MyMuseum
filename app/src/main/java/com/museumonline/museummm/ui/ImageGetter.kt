package com.museumonline.museummm.ui

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.museumonline.museummm.R
import com.squareup.picasso.Picasso
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Obfuscate
class ImageGetter(
    private val res: Resources,
    private val tView: TextView
) : Html.ImageGetter {


    override fun getDrawable(url: String): Drawable {
        val holder = BitmapDrawablePlaceHolder(res, null)
        GlobalScope.launch(Dispatchers.IO) {
            runCatching {
                val bitmap = Picasso.get().load("https://first.mrdimuseum.uz/$url").placeholder(R.drawable.placeholder).get()
                val drawable = BitmapDrawable(res, bitmap)
                val width = getScreenWidth() - 150
                val aspectRatio: Float =
                    (drawable.intrinsicWidth.toFloat()) / (drawable.intrinsicHeight.toFloat())
                val height = width / aspectRatio
                drawable.setBounds(10, 20, width, height.toInt())
                holder.setDrawable(drawable)
                holder.setBounds(10, 20, width, height.toInt())

                withContext(Dispatchers.Main) {
                    tView.text = tView.text
                }

            }


        }

        return holder
    }

    internal class BitmapDrawablePlaceHolder(res: Resources, bitmap: Bitmap?) :
        BitmapDrawable(res, bitmap) {
        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.run { draw(canvas) }
        }

        fun setDrawable(drawable: Drawable) {
            this.drawable = drawable
        }
    }

    private fun getScreenWidth() =
        Resources.getSystem().displayMetrics.widthPixels

}
