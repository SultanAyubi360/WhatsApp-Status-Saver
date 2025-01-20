package com.sultanayubi.waStatussaver.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sultanayubi.waStatussaver.Data.StatusRepo
import com.sultanayubi.waStatussaver.databinding.FragmentMediaBinding
import com.sultanayubi.waStatussaver.Models.MediaModel
import com.sultanayubi.waStatussaver.Utils.Constants
import com.sultanayubi.waStatussaver.Models.StatusViewModel
import com.sultanayubi.waStatussaver.Models.StatusViewModelFactory
import com.sultanayubi.waStatussaver.Adapters.MediaAdapter

class MediaFragment : Fragment() {

    private val binding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: StatusViewModel
    lateinit var adapter: MediaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {
                val repo = StatusRepo(requireActivity())
                viewModel = ViewModelProvider(
                    requireActivity(),
                    StatusViewModelFactory(repo)
                )[StatusViewModel::class.java]
                val mediaType = it.getString(Constants.MEDIA_TYPE_KEY, "")
                when (mediaType) {
                    Constants.MEDIA_TYPE_WHATSAPP_IMAGES -> {
                        viewModel.whatsAppImagesLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }

                            val list = ArrayList<MediaModel>()
                            filteredList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                            if (list.size == 0) {
                                tempMediaText.visibility = View.VISIBLE
                            } else {
                                tempMediaText.visibility = View.GONE
                            }

                        }
                    }

                    Constants.MEDIA_TYPE_WHATSAPP_VIDEOS -> {
                        viewModel.whatsAppVideosLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }

                            val list = ArrayList<MediaModel>()
                            filteredList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                            if (list.size == 0) {
                                tempMediaText.visibility = View.VISIBLE
                            } else {
                                tempMediaText.visibility = View.GONE
                            }
                        }
                    }

                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES -> {
                        viewModel.whatsAppBusinessImagesLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }

                            val list = ArrayList<MediaModel>()
                            filteredList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                            if (list.size == 0) {
                                tempMediaText.visibility = View.VISIBLE
                            } else {
                                tempMediaText.visibility = View.GONE
                            }
                        }
                    }

                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS -> {
                        viewModel.whatsAppBusinessVideosLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredList = unFilteredList.distinctBy { model ->
                                model.fileName
                            }

                            val list = ArrayList<MediaModel>()
                            filteredList.forEach { model ->
                                list.add(model)
                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                            if (list.size == 0) {
                                tempMediaText.visibility = View.VISIBLE
                            } else {
                                tempMediaText.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showExitConfirmationDialog()
                }
            })

        return view
    }

    private fun showExitConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Exit the app
                requireActivity().finish()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog and do nothing
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

}