package com.example.aslcodingtestproject.presentation.photocomment.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aslcodingtestproject.databinding.FragmentPhotoCommentBinding
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.presentation.photocomment.adapter.PhotoCommentAdapter
import com.example.aslcodingtestproject.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//
@AndroidEntryPoint
class PhotoCommentFragment : Fragment() {

    private var _binding: FragmentPhotoCommentBinding? = null

    private val binding get() = _binding!!
    private val photoViewModel: PhotoViewModel by viewModels()
    private lateinit var photoCommentAdapter: PhotoCommentAdapter
    private val args by navArgs<PhotoCommentFragmentArgs>()

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
        _binding = FragmentPhotoCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.tag("photoComment").d("args.photoItem: ${args.photoItem}")
        binding.llRefresh.isRefreshing = true
        initUI()
        initAdapter()
        viewModelInit()
    }

    private fun initUI() {
        binding.photo = args.photoItem
        binding.executePendingBindings()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(PhotoCommentFragmentDirections.actionPhotoCommentFragmentToPhotoThumbnailListFragment())
        }
        binding.llRefresh.setOnRefreshListener {
            getPhotoCommentFromApi()
        }
    }

    private fun initAdapter() {
        photoCommentAdapter = PhotoCommentAdapter(requireActivity())
        binding.rvPhotoThumbnail.adapter = photoCommentAdapter
    }

    private fun viewModelInit() {
        // Directly observe database data
        photoViewModel.photoComments.observe(viewLifecycleOwner) { data ->
            Timber.tag("fragment").d("photoViewModel.photo.observe(this), data: %s", data)
            if (data != null ) {
                if(data.count() > 19){
                    photoCommentAdapter.submitList((0 until 20).map { data[it]})
                }else{
                    photoCommentAdapter.submitList(data)
                }
            }
        }
        // Observe the api status
        photoViewModel.status.observe(viewLifecycleOwner){
                status ->
            binding.llRefresh.isRefreshing = status == Resource.Status.LOADING
        }
    }

    private fun getPhotoCommentFromApi() {
        photoViewModel.getPhotoCommentFromApi(args.photoItem.id.toString())
    }

    override fun onResume() {
        super.onResume()
        getPhotoCommentFromApi()
    }
}