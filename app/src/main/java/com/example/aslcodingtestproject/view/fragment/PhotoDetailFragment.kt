package com.example.aslcodingtestproject.view.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aslcodingtestproject.databinding.FragmentPhotoDetailBinding
import com.example.aslcodingtestproject.view.adapter.PhotoCommentAdapter
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import com.example.aslcodingtestproject.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//
@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val photoViewModel: PhotoViewModel by viewModels()
    private lateinit var photoCommentAdapter: PhotoCommentAdapter
    private val args by navArgs<PhotoDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.tag("photoDetail").d("args.photoItem: %s", args.photoItem)
        initUI()
        initAdapter()
        viewModelInit()
    }

    private fun initUI() {
        binding.photo = args.photoItem
        binding.executePendingBindings()
    }

    private fun initAdapter() {
        photoCommentAdapter = PhotoCommentAdapter(requireActivity())
        binding.rvPhotoThumbnail.adapter = photoCommentAdapter
        binding.llRefresh.setOnRefreshListener {
            getPhotoDetailFromApi()
        }
    }

    private fun viewModelInit() {
        // Directly observe database data
        photoViewModel.photoComments.observe(viewLifecycleOwner) { data ->
            Timber.tag("fragment").d("photoViewModel.photo.observe(this), data: %s", data)
            if (data != null ) {
                if(data.count() > 19){
                    photoCommentAdapter.setData((0 until 20).map { data[it] }.toCollection(ArrayList()))
                }else{
                    photoCommentAdapter.setData(data)
                }
            }
        }
    }

    private fun getPhotoDetailFromApi() {

        photoViewModel.getPhotoDetailFromApi(args.photoItem.id.toString(),
            onLoadingListener = object : OnLoadingEventListener {
                override fun startLoading() {
                    binding.llRefresh.isRefreshing = true
                }
                override fun stopLoading() {
                    Timber.tag("getPhotoDetailFromApi").d( "stopLoading()")
                    binding.llRefresh.isRefreshing = false
                }
            })
    }

    override fun onResume() {
        super.onResume()
        getPhotoDetailFromApi()
    }
}