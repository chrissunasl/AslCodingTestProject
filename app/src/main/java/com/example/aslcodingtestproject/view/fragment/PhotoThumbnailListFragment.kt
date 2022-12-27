package com.example.aslcodingtestproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.constant.util.OnCustomItemClickListener
import com.example.aslcodingtestproject.databinding.FragmentPhotoThumbnailListBinding
import com.example.aslcodingtestproject.model.remote.CheckInternet
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.view.adapter.PhotoListAdapter
import com.example.aslcodingtestproject.view.viewdata.PhotoItem
import com.example.aslcodingtestproject.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class PhotoThumbnailListFragment : Fragment() {

    private var _binding: FragmentPhotoThumbnailListBinding? = null
    private val photoViewModel: PhotoViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var photoListAdapter: PhotoListAdapter
    private var photoDataList: List<PhotoItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoThumbnailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setActionBar(binding.toolbar)
        init()
        viewModelInit()
    }

    // init of late init adapter, button event
    private fun init() {
        photoListAdapter = PhotoListAdapter(
            onCustomItemClickListener = object : OnCustomItemClickListener<PhotoItem> {
                override fun onClick(view: View?, item: PhotoItem) {
                    val extras = FragmentNavigatorExtras(view!! to "ivPhotoBig")
                    val args = Bundle()
                    args.putParcelable("photoItem", item)

                    findNavController().navigate(
                        resId = R.id.actionPhotoThumbnailListFragmentToPhotoDetailFragment,
                        args = args,
                        navOptions = null,
                        navigatorExtras = extras
                    )
                }
            }
        )
        binding.rvPhotoThumbnail.adapter = photoListAdapter

        binding.llRefresh.setOnRefreshListener {
            getPhotoFromApi()
        }

        binding.ivSearch.setOnClickListener {
            if (binding.etSearch.text.toString().isNotEmpty()) {
                binding.rvPhotoThumbnail.scrollToPosition(
                    photoViewModel.findPositionByTitle(
                        binding.etSearch.text.toString(),
                        photoDataList
                    )
                )

            }
        }
    }

    // init of viewModel observe
    private fun viewModelInit() {
        // Directly observe photos, view only observe
        photoViewModel.photos.observe(viewLifecycleOwner) { data ->
            if (data.isNullOrEmpty()) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = getString(R.string.common_no_record)

                // Checking Internet needs context
                if (!CheckInternet.isOnline(requireActivity())) {
                    binding.tvStatus.text = getString(R.string.common_no_internet)
                }
            } else {
                photoDataList = data
                binding.tvStatus.visibility = View.GONE
                photoListAdapter.submitList(data)
            }
        }
        // Observe status replace passing loading listener avoid memory leak
        photoViewModel.status.observe(viewLifecycleOwner){
            status ->
            Timber.i("status, $status")
            binding.llRefresh.isRefreshing = status == Resource.Status.LOADING
        }
    }

    private fun getPhotoFromApi() {
        photoViewModel.getPhotoFromApi()
    }

    override fun onResume() {
        super.onResume()
        getPhotoFromApi()
    }
}