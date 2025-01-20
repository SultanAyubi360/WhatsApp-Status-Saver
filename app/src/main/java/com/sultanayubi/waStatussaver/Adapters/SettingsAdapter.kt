package com.sultanayubi.waStatussaver.Adapters

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sultanayubi.waStatussaver.R
import com.sultanayubi.waStatussaver.databinding.DialogGuideBinding
import com.sultanayubi.waStatussaver.databinding.ItemSettingsBinding
import com.sultanayubi.waStatussaver.Models.SettingsModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsAdapter(var list: ArrayList<SettingsModel>, var context: Context) :
    RecyclerView.Adapter<SettingsAdapter.viewHolder>() {

    inner class viewHolder(var binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SettingsModel, position: Int) {
            binding.apply {
                settingsTitle.text = model.title
                settingsDesc.text = model.desc

                root.setOnClickListener {
                    when (position) {
                        0 -> {
                            // how to use 1st item
                            val dialog = Dialog(context)
                            val dialogBinding =
                                DialogGuideBinding.inflate((context as Activity).layoutInflater)
                            dialogBinding.okayBtn.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.setContentView(dialogBinding.root)
                            dialog.window?.setLayout(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                ActionBar.LayoutParams.WRAP_CONTENT
                            )
                            dialog.show()
                        }

                        2 -> {
                            MaterialAlertDialogBuilder(context).apply {
                                setTitle("Disclaimer")
                                setMessage("This app is intended for personal use only. WhatsApp is a trademark of WhatsApp Inc., and this app is not affiliated with, endorsed, or sponsored by WhatsApp Inc. \n" +
                                        "\n" +
                                        "Please respect the privacy and intellectual property rights of others. Do not download, share, or distribute any content without permission from the owner. The creator of this app is not responsible for any misuse or legal issues that may arise from the use of this app.\n" +
                                        "\n" +
                                        "By using this app, you agree to comply with all relevant laws and regulations regarding the downloading and sharing of content.")
                                setPositiveButton("Okay",null)
                                show()
                            }
                        }

                        3 -> {
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SultanAyubi360")).apply {
                                context.startActivity(this)
                            }
                        }

                        4 -> {
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT,context.getString(R.string.app_name))
                                putExtra(Intent.EXTRA_TEXT,"My App is Awesome please download it :https://play.google.com/store/apps/details?id=${context.packageName}")
                                context.startActivity(this)
                            }
                        }

                        5 -> {
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)
                            ).apply {
                                context.startActivity(this)
                            }

                        }
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemSettingsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(model = list[position], position)
    }
}