package com.museumonline.museummm.ui.home

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.museumonline.museummm.LoadingDialog
import com.museumonline.museummm.R
import com.museumonline.museummm.databinding.FragmentHomeBinding
import com.museumonline.museummm.databinding.OverlayViewBinding
import com.museumonline.museummm.model.Art
import com.museumonline.museummm.network.Contents
import com.museumonline.museummm.ui.adapter.ArtAdapter
import com.museumonline.museummm.ui.langugae.RuntimeLocaleChanger
import com.museumonline.museummm.viewModel.MyViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var overlayViewBinding: OverlayViewBinding
    private val myViewModel by activityViewModels<MyViewModel>()
    lateinit var loadingDialog: LoadingDialog
    private lateinit var artList: List<Art>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        loadingDialog = LoadingDialog(requireActivity())
        setupViewModel()
    }

    private fun setupViewModel() {

        myViewModel.artDesc.observe(requireActivity()) {
            loadingDialog.startLoadingDialog()
            binding.apply {
                txtName.text = it.nomi
                txtCategory.text = it.cat
                txtDesc.text = it.about
            }

        }

        myViewModel.arts.observe(requireActivity()) { arts ->
            arts.arts?.let {
                artList = it
            }

            Log.d("@@@", "setupViewModel: ${arts}")
            setupRV(artList)
            loadingDialog.dismissDialog()
        }

    }

    private fun setupRV(artList: List<Art>) {
        overlayViewBinding =
            OverlayViewBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        overlayViewBinding.infoLayout.apply {
            setPaddingRelative(
                paddingStart,
                paddingTop,
                paddingEnd,
                getNavigationBarHeight(context, VERTICAL)
            )
        }


        myViewModel.artInfo.observe(requireActivity()) {
            overlayViewBinding.apply {
                tvName.text = it.nomi.toString()
                tvAuthor.text = it.avtor.toString()
                tvTechnique.text = it.texnika.toString()
                tvYear.text = it.year.toString()
                tvSize.text = it.razmer.toString()
                tvAbout.text = it.about.toString()

                tvMore.setOnClickListener {
                    if (infoContainer.visibility == VISIBLE) {
                        infoContainer.visibility = GONE
                        tvMore.text = getString(R.string.show_more)
                    } else {
                        infoContainer.visibility = VISIBLE
                        tvMore.text = getString(R.string.show_less)
                    }
                }
            }
        }


        binding.rvArts.adapter = ArtAdapter(artList, getScreenWidth()) { pos ->
            myViewModel.getArtInfo(
                artList[pos].id.toString(),
                RuntimeLocaleChanger.getLocale(requireContext())
            )

            val imageList = arrayListOf<String>()
            (artList[pos].img?.split("$$"))?.forEach {
                if (it.isNotBlank()) {
                    imageList.add(it)

                }
            }

            StfalconImageViewer.Builder(
                requireContext(),
                imageList
            ) { view, image ->
                Picasso.get().load(Contents.BASE_URL.plus("/images/asar/").plus(image)).into(view)
            }.withHiddenStatusBar(false)
                .withOverlayView(overlayViewBinding.root)
                .withDismissListener {
                    if (overlayViewBinding.root.parent != null)
                        (overlayViewBinding.root.parent as ViewGroup).removeView(overlayViewBinding.root)
                }
                .withImageChangeListener {

                }
                .show()

        }
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getNavigationBarHeight(context: Context, orientation: Int): Int {
        val resources: Resources = context.resources
        val id = resources.getIdentifier(
            if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_height_landscape",
            "dimen", "android"
        )
        return if (id > 0) {
            resources.getDimensionPixelSize(id)
        } else 0
    }


}