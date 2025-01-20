package com.sultanayubi.waStatussaver.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultanayubi.waStatussaver.R
import com.sultanayubi.waStatussaver.databinding.ItemImagePreviewBinding
import com.sultanayubi.waStatussaver.Models.MediaModel
import com.sultanayubi.waStatussaver.Utils.saveStatus

class ImagePreviewAdapter (val list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImagePreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaModel: MediaModel) {
            binding.apply {
                Glide.with(context)
                    .load(mediaModel.pathUri.toUri())
                    .into(zoomableImageView)

                if (mediaModel.isDownloaded) {
                    tools.statusDownload.setImageResource(R.drawable.ic_downloaded)
                    tools.statusDownloadText.setText(R.string.saved)
                } else {
                    tools.statusDownload.setImageResource(R.drawable.ic_download)
                    tools.statusDownloadText.setText(R.string.save)
                }

                tools.download.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded) {
                        // status is downloaded
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        mediaModel.isDownloaded = true
                        tools.statusDownload.setImageResource(R.drawable.ic_downloaded)
                        tools.statusDownloadText.setText(R.string.saved)
                    } else {
                        // unable to download status
                        Toast.makeText(context, "Unable to Save", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagePreviewAdapter.ViewHolder {
        return ViewHolder(ItemImagePreviewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ImagePreviewAdapter.ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount() = list.size

}