package com.sultanayubi.waStatussaver.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sultanayubi.waStatussaver.Models.MediaModel
import com.sultanayubi.waStatussaver.Utils.Constants
import com.sultanayubi.waStatussaver.Adapters.ImagePreviewAdapter
import com.sultanayubi.waStatussaver.databinding.ActivityImagePreviewBinding

class ImagePreviewActivity : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityImagePreviewBinding.inflate(layoutInflater)
    }
    lateinit var adapter: ImagePreviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val list =
                intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)
            adapter = ImagePreviewAdapter(list, activity)
            imagesViewPager.adapter = adapter
            imagesViewPager.currentItem = scrollTo

            // Setup toolbar navigation
            toolBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        }
    }
}