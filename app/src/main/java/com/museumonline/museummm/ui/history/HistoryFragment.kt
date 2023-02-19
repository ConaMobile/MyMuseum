package com.museumonline.museummm.ui.history

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.museumonline.museummm.LoadingDialog
import com.museumonline.museummm.R
import com.museumonline.museummm.databinding.FragmentHistoryBinding
import com.museumonline.museummm.databinding.OverlayViewBinding
import com.museumonline.museummm.network.Contents
import com.museumonline.museummm.ui.ImageGetter
import com.museumonline.museummm.ui.langugae.RuntimeLocaleChanger
import com.museumonline.museummm.viewModel.MyViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint
import io.michaelrocks.paranoid.Obfuscate
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

@Obfuscate
@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var overlayViewBinding: OverlayViewBinding
    private lateinit var myViewModel: MyViewModel
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        myViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(infltr: LayoutInflater, cntnr: ViewGroup?, svdIns: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(infltr, cntnr, false)
        overlayViewBinding =
            OverlayViewBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        openLoadingDialog()
        if (myViewModel.history.value == null) {
            myViewModel.getHistory(RuntimeLocaleChanger.getLocale(requireContext()))
        }

        myViewModel.history.observe(viewLifecycleOwner) {
            val history = it?.historyList?.get(0)?.content.toString()
            displayHtml(history)
        }
    }

    private fun displayHtml(html: String) {
        var styledText: Spanned


        val time = measureTimeMillis {
            runBlocking {
                val imageGetter = ImageGetter(resources, binding.textHistory)

                styledText = HtmlCompat.fromHtml(
                    html,
                    HtmlCompat.FROM_HTML_MODE_LEGACY,
                    imageGetter, null
                )
                coroutineScope {
                    imageClick(styledText as Spannable)
                    binding.textHistory.text = styledText
                    binding.textHistory.movementMethod = LinkMovementMethod.getInstance()
                }
            }
        }

    }

    private suspend fun imageClick(html: Spannable) {
        coroutineScope {
            for (span in html.getSpans(0, html.length, ImageSpan::class.java)) {
                val flags = html.getSpanFlags(span)
                val start = html.getSpanStart(span)
                val end = html.getSpanEnd(span)
                html.setSpan(object : URLSpan(span.source) {
                    override fun onClick(v: View) {
                        val imgList = listOf<String>(span.source.toString())
                        StfalconImageViewer.Builder(
                            requireContext(),
                            imgList
                        ) { view, image ->
                            Picasso.get().load(Contents.BASE_URL.plus("/").plus(image))
                                .placeholder(R.drawable.placeholder)
                                .into(view)
                        }.withHiddenStatusBar(false)
                            .withDismissListener {
                                if (overlayViewBinding.root.parent != null)
                                    (overlayViewBinding.root.parent as ViewGroup).removeView(
                                        overlayViewBinding.root
                                    )
                            }
                            .withImageChangeListener {

                            }
                            .show()
                    }
                }, start, end, flags)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("@@@", "onStop: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("@@@", "onDetach: ")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("@@@", "onAttach: ")

    }

    private fun openLoadingDialog() {
        loadingDialog = LoadingDialog(requireActivity())

        loadingDialog!!.let {
            it.startLoadingDialog()
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1500)
                Log.d("@@@", "openLoadingDialog: sdfsdf")
                it.dismissDialog()
            }
        }

    }

}