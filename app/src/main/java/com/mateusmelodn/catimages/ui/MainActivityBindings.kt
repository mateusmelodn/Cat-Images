package com.mateusmelodn.catimages.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.ProgressIndicator
import com.mateusmelodn.catimages.ui.adapter.CatImagesAdapter
import com.mateusmelodn.catimages.core.model.CatImages
import com.mateusmelodn.catimages.util.*

/**
 * Custom bindings for the [MainActivity] view
 */

@BindingAdapter("downloadedCatImages")
fun setDownloadedCatImages(recyclerView: RecyclerView, catImages: AsyncState<List<CatImages>>?) {
    val catImagesObject = (catImages as? Success<List<CatImages>>)?.value

    catImagesObject?.let {

        if (catImagesObject.isNotEmpty()) {
            if (MainActivity.currentPage == 0) {
                recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 4)
                recyclerView.adapter = CatImagesAdapter(catImagesObject as ArrayList)
                recyclerView.addItemDecoration(SpacesItemDecoration(2))
                recyclerView.addOnScrolledToEnd {
                    (recyclerView.context as MainActivity).onScrollFinished()
                }
            } else {
                (recyclerView.adapter as CatImagesAdapter).addAll(catImagesObject)
            }
        }
    }
}

@BindingAdapter("downloadedCatImages")
fun setDownloadedCatImages(textView: TextView, catImages: AsyncState<List<CatImages>>?) {
    val catImagesObject = (catImages as? Success<List<CatImages>>)?.value

    catImagesObject?.let {
        if (catImagesObject.isEmpty() && MainActivity.currentPage == 0) {
            textView.visibility = View.VISIBLE
        }
    }
}

/**
 * Shows a progressIndicator or hides it based on some data's [AsyncState]
 */
@BindingAdapter("progressVisibility")
fun setProgressVisibility(progressIndicator: ProgressIndicator, state: AsyncState<Any?>?) {
    if (state == Loading) {
        progressIndicator.visibility = View.VISIBLE
    } else {
        progressIndicator.visibility = View.INVISIBLE
    }
}
