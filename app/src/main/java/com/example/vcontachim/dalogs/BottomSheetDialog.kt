package com.example.vcontachim.dalogs

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.vcontachim.R
import com.example.vcontachim.databinding.BottomSheetDialogBinding
import com.example.vcontachim.models.ItemVideo
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialog(
    val itemVideo: ItemVideo,
    context: Context,
    val addVideoListener: VideoListener
) : BottomSheetDialog(context) {

    private var binding: BottomSheetDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_dialog)

        val rootLayout: View = findViewById(R.id.dialog_button_layout)!!
        binding = BottomSheetDialogBinding.bind(rootLayout)

        binding!!.nameVideo.text = itemVideo.title

        binding!!.copyLink.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
            }
        })

        binding!!.removeFromMyVideos.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                addVideoListener.onVideoDelete(itemVideo)
                dismiss()
            }
        })
    }

    interface VideoListener {
        fun onVideoDelete(video: ItemVideo)
    }
}