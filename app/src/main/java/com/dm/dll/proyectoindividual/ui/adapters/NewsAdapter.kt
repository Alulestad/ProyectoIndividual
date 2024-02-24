package com.dm.dll.proyectoindividual.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dm.dll.proyectoindividual.R
import com.dm.dll.proyectoindividual.data.network.entities.news.Article
import com.dm.dll.proyectoindividual.data.network.entities.news.NewsX
import com.dm.dll.proyectoindividual.databinding.ItemNoticiaBinding


class NewsAdapter : ListAdapter<Article,NewsAdapter.NewsVH>(DiffUtilNewsCallback) {


    class NewsVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: ItemNoticiaBinding = ItemNoticiaBinding.bind(view)

        fun render(item: Article) {
            binding.imgNobel.load("https://openclipart.org/image/800px/167281")
            binding.txtAnio.text = item.author
            binding.txtCategoria.text = item.content!!
            binding.txtNombre.text = item.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val inflater = LayoutInflater.from(parent.context)
        return NewsVH(inflater.inflate(R.layout.item_noticia, parent, false))
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.render(getItem(position))
    }
}

    object DiffUtilNewsCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return (oldItem.author == newItem.author)
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return (oldItem == newItem)
        }
}