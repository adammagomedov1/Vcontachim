package com.example.vcontachim.dalogs

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.vcontachim.R
import com.example.vcontachim.databinding.VideoMenuBottomSheetDialogBinding
import com.example.vcontachim.models.ItemVideo
import com.google.android.material.bottomsheet.BottomSheetDialog

class VideoMenuBottomSheetDialog(
    val itemVideo: ItemVideo,
    context: Context,
    val addVideoListener: VideoListener
) : BottomSheetDialog(context) {

    private var binding: VideoMenuBottomSheetDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_menu_bottom_sheet_dialog)

        val rootLayout: View = findViewById(R.id.dialog_button_layout)!!
        binding = VideoMenuBottomSheetDialogBinding.bind(rootLayout)

        binding!!.nameVideo.text = itemVideo.title

        binding!!.copyLink.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                addVideoListener.copyVideoLink(itemVideo)
                dismiss()
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
        fun copyVideoLink(itemVideo: ItemVideo)
    }
}