package com.example.aslcodingtestproject.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.view.viewmanager.ImageHandler
import com.example.aslcodingtestproject.databinding.FragmentPhotoDetailBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.view.adapter.PhotoCommentAdapter
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import com.example.aslcodingtestproject.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

//
@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val photoViewModel: PhotoViewModel by viewModels()
    private lateinit var photoCommentAdapter: PhotoCommentAdapter
    private lateinit var args: PhotoDetailFragmentArgs

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
    ): View? {

        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = PhotoDetailFragmentArgs.fromBundle(requireArguments())

        Log.d("chris", "args.photoItem: ${args.photoItem}")
        initUI()
        initAdapter()
        viewModelInit()

    }

    private fun initUI() {
        binding.tvTitle.title = args.photoItem.title
        binding.tvTitleSupport.text = args.photoItem.title
        ImageHandler.bindImageWithUrl(binding.ivPhoto, args.photoItem.url)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {

        photoCommentAdapter = PhotoCommentAdapter(requireActivity(),
            onCustomItemClickListener = object : OnCustomItemClickListener<GetPhotoDetailRespItem> {
                override fun onClick(view: View?, item: GetPhotoDetailRespItem) {}
            }
        )
        binding.rvPhotoThumbnail.adapter = photoCommentAdapter


        binding.llRefresh.setOnRefreshListener {
            getPhotoFromApi()
        }

    }

    private fun viewModelInit() {

        photoViewModel.getPhotoDetailFromDb().observe(viewLifecycleOwner) { data ->
            Log.d("chris", "viewModelInit() data: $data")

            photoCommentAdapter.addList(data)
            photoCommentAdapter.notifyDataSetChanged()

        }
    }

    private fun getPhotoFromApi(){
        photoViewModel.getPhotoDetailFromApi(viewLifecycleOwner,
            onLoadingListener = object : OnLoadingEventListener {
                override fun startLoading() {
                    binding.llRefresh.isRefreshing = true
                }

                override fun stopLoading() {
                    Log.d("chris", "stopLoading(): $this.photo")
                    binding.llRefresh.isRefreshing = false
                }
            }, args.photoItem.id.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getPhotoFromApi()
    }
}