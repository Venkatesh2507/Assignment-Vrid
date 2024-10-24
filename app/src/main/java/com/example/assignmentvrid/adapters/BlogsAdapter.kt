package com.example.assignmentvrid.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentvrid.R
import com.example.assignmentvrid.utils.Util
import com.example.assignmentvrid.modal.Blog

class BlogsAdapter: RecyclerView.Adapter<BlogsAdapter.BlogViewHolder>() {
    inner class BlogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Blog>() {
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)
    val convert = Util()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.blog_cardview,parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = differ.currentList[position]
        val title: TextView = holder.itemView.findViewById(R.id.title)
        val date: TextView = holder.itemView.findViewById(R.id.date)
        holder.itemView.apply {
            Glide.with(this).load(differ.currentList[position].jetpackFeaturedMediaUrl).into(findViewById(
                R.id.imageView
            ))
            title.text = differ.currentList[position].title.rendered
            date.text = convert.convertDateFormat(differ.currentList[position].date)

            setOnClickListener{
                onItemClickListener?.let { it(blog) }
            }
        }

    }

    private var onItemClickListener: ((Blog) -> Unit)? = null
    fun setOnItemClickListener(listener: (Blog) -> Unit){
        onItemClickListener = listener
    }

}