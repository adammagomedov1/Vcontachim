package com.example.vcontachim.dalogs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.vcontachim.R
import com.example.vcontachim.databinding.BottomSheetDialogBinding
import com.example.vcontachim.models.ItemVideo
import com.example.vcontachim.models.VideoDelete
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddBottomDialogDeleteVideo(val itemVideo: ItemVideo,context: Context, val addVideoListener: AddVideoListener) :
    BottomSheetDialog(context) {

    private var binding: BottomSheetDialogBinding? = null

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_dialog)

        val rootLayout: View? = findViewById(R.id.dialog_button_layout)

        binding = BottomSheetDialogBinding.bind(rootLayout!!)

        binding!!.textViewName.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        binding!!.copyLink.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        binding!!.removeFromMyVideos.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                addVideoListener.onVideoDelete(itemVideo)
            }
        })

        dismiss()
    }

    interface AddVideoListener {
        fun onVideoDelete(video: ItemVideo)
    }
}