package com.museumonline.museummm.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import com.museumonline.museummm.databinding.FilterLayoutBinding
import com.museumonline.museummm.databinding.LanguageChangeLayoutBinding
import com.museumonline.museummm.model.Direction
import com.museumonline.museummm.ui.adapter.FilterAdapter
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
object Utils {

    fun showLanguageChangeMenu(view: View, setLang: (lang: Int) -> Unit): PopupWindow? {
        var mDropdown: PopupWindow? = null
        var inflater: LayoutInflater? = null

        try {
            inflater =
                view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = LanguageChangeLayoutBinding.inflate(inflater)

            binding.root.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            mDropdown = PopupWindow(
                binding.root, FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, true
            )

            binding.btnEnglish.setOnClickListener {
                setLang(0)
                mDropdown.dismiss()

            }

            binding.btnUzbek.setOnClickListener {
                setLang(1)
                mDropdown.dismiss()
            }

            binding.btnRussian.setOnClickListener {
                setLang(2)
                mDropdown.dismiss()
            }


            mDropdown.showAsDropDown(view, 5, 5)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mDropdown
    }


    fun showFilterMenu(
        view: View,
        items: List<Direction>,
        selectDirection: (direction: Direction) -> Unit
    ): PopupWindow? {
        var mDropdown: PopupWindow? = null
        var inflater: LayoutInflater? = null

        try {
            inflater =
                view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = FilterLayoutBinding.inflate(inflater)

            binding.root.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
            )
            mDropdown = PopupWindow(
                binding.root, FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, true
            )

            binding.filterRV.adapter = FilterAdapter(items) {
                selectDirection(it)
                mDropdown.dismiss()
            }

            mDropdown.showAsDropDown(view, 5, 5)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mDropdown
    }
}