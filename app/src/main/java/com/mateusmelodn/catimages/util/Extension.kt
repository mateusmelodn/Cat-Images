package com.mateusmelodn.catimages.util

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mateusmelodn.catimages.R
import com.mateusmelodn.catimages.ui.adapter.CatImagesAdapter

//  INIT EXTENSION --------------------------------------------------------------
fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit) {
    this.addOnScrollListener(object: RecyclerView.OnScrollListener() {
        private val VISIBLE_THRESHOLD = 30
        private var loading = true
        private var previousTotal = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            with(layoutManager as GridLayoutManager) {
                val visibleItemCount = childCount
                val totalItemCount = itemCount
                val firstVisibleItem = findFirstVisibleItemPosition()

                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }

                if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                    onScrolledToEnd()
                    loading = true
                }
            }
        }
    })
}

fun CatImagesAdapter.ViewHolder.showCatImageDialog(drawable: Drawable?, title: String) {
    val activity = itemView.context as Activity

    val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
    val root: View = activity.layoutInflater.inflate(R.layout.dialog_cat_image, null)
    builder.setView(root)

    val catImageDialog = builder.create()
    catImageDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    catImageDialog.setCancelable(true)
    catImageDialog.setCanceledOnTouchOutside(true)

    val catImageImageView: ImageView = root.findViewById(R.id.catImageImageView)
    val titleTextView: TextView = root.findViewById(R.id.catImageTitleTextView)

    catImageImageView.setImageDrawable(drawable)
    titleTextView.text = title

    catImageDialog.show()
}
//  END EXTENSION --------------------------------------------------------------