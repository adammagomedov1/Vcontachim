package com.example.vcontachim.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vcontachim.R
import com.example.vcontachim.VcontachimApplication
import com.example.vcontachim.adapter.PhotoCommentsAdapter
import com.example.vcontachim.databinding.FragmentPhotoCommentsBinding
import com.example.vcontachim.models.ItemPhotos
import com.example.vcontachim.models.PhotoCommentsUi
import com.example.vcontachim.viewmodel.PhotoCommentsViewModel
import java.io.Serializable

class PhotoCommentsFragment : Fragment(R.layout.fragment_photo_comments) {
    private var binding: FragmentPhotoCommentsBinding? = null

    private val viewModel: PhotoCommentsViewModel by lazy {
        ViewModelProvider(this)[PhotoCommentsViewModel::class.java]
    }

    private var photoCommentsAdapter =
        PhotoCommentsAdapter(likeListener = object : PhotoCommentsAdapter.LikeListener {
            override fun onClick(
                photoCommentsUi: PhotoCommentsUi
            ) {
                if (photoCommentsUi.userLikes == 0) {
                    viewModel.likeComment(photoCommentsUi)
                } else {
                    viewModel.deleteLikeComment(photoCommentsUi)
                }
            }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoCommentsBinding.bind(view)

        val photosSerializable: Serializable =
            arguments?.getSerializable(SAVE_PHOTOS_COMMENTS_KEY)!!
        val photos = photosSerializable as ItemPhotos

        binding!!.toolbarFromPhotoCommentsScreen.setNavigationOnClickListener {
            VcontachimApplication.router.exit()
        }

        binding!!.editText.addTextChangedListener(object : TextWatcher {
            //Вызывается ДО изменения текста
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            //Вызывается ВО ВРЕМЯ изменения текста
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            //Вызывается ПОСЛЕ изменения текста (используется, в основном)
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    binding!!.imageView.setImageResource(R.drawable.send_28__3_)
                } else {
                    binding!!.imageView.setImageResource(R.drawable.send_28__2_)
                    binding!!.imageView.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View) {
                            viewModel.createCommentPhotos(photos, s.toString())
                            s!!.clear()

                            binding!!.editText.hideKeyboard()

                            viewModel.addCommentLiveData.observe(viewLifecycleOwner) {
                                val toast = Toast.makeText(
                                    requireContext(),
                                    R.string.сomment_added,
                                    Toast.LENGTH_LONG
                                )
                                toast.show()
                            }
                        }
                    })
                }
            }
        })

        binding!!.toolbarFromPhotoCommentsScreen.subtitle = photos.comments.count

        binding!!.recyclerViewFromPhotoCommentsScreen.adapter = photoCommentsAdapter

        viewModel.photoCommentsLiveData.observe(viewLifecycleOwner) {
            photoCommentsAdapter.submitList(it)

        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_LONG
            )
            toast.show()
        }

        viewModel.loadPhotoComments(photos)
    }

    companion object {
        private const val SAVE_PHOTOS_COMMENTS_KEY = "photoComments"

        fun createFragment(photos: ItemPhotos): Fragment {
            val fragment = PhotoCommentsFragment()
            val bundle = Bundle()
            bundle.putSerializable(SAVE_PHOTOS_COMMENTS_KEY, photos)
            fragment.arguments = bundle

            return fragment
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}