package com.mateusmelodn.catimages.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mateusmelodn.catimages.R
import com.mateusmelodn.catimages.core.model.CatImages
import com.mateusmelodn.catimages.util.showCatImageDialog

class CatImagesAdapter(private val catImages: ArrayList<CatImages>) : RecyclerView.Adapter<CatImagesAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catImageImageView: ImageView = view.findViewById(R.id.catImageImageView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat_image, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

//        TODO Lógica somente pega o primeiro item de Images de cada conjunto de dados, pois a API não é maleável o suficiente para retornar somente os dados necessários
//        TODO A ideia era não ter get(0), ou seja, refatorar o modelo CatImages, mas para isso seria necessária uma refatoração na API
//        TODO Da forma como está, outras Images podem ser ignoradas, quando houver mais de uma Images associada ao mesmo título.
        // O true na expressão lógica foi adicionado, pois a lib Glide consegue realizar preview não somente de fotos
        // Além de que a API não retorna somente jpeg e png, quando se usa q_type na busca avançada
        val catImage = catImages[position]
        catImage.images?.get(0)?.let {image ->
            if (image.type.endsWith("jpeg") || image.type.endsWith(".png") || true) {
                Glide.with(viewHolder.itemView.context)
                        .load(image.link)
                        .centerCrop()
                        .placeholder(R.drawable.empty)
                        .into(viewHolder.catImageImageView)
            }
        }

        viewHolder.itemView.setOnClickListener() {
            viewHolder.showCatImageDialog(viewHolder.catImageImageView.drawable, catImage.title)
        }

        viewHolder.itemView.setOnLongClickListener {
            viewHolder.showCatImageDialog(viewHolder.catImageImageView.drawable, catImage.title)
            true
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = catImages.size

    fun addAll(newItems: List<CatImages>) {
        newItems.forEach {
            catImages.add(it)
            notifyItemInserted(catImages.size - 1)
        }
    }
}