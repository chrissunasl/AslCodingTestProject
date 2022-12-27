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
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.view.adapter.PhotoListAdapter
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import com.example.aslcodingtestproject.view.viewdata.PhotoItem
import com.example.aslcodingtestproject.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class PhotoThumbnailListFragment : Fragment() {

    private var _binding: FragmentPhotoThumbnailListBinding? = null
    private val photoViewModel: PhotoViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var photoListAdapter: PhotoListAdapter
    private var photoDataList: ArrayList<GetPhotoRespItem> = ArrayList()

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
            if (binding.etSearch.text.toString().isNotEmpty() && photoDataList.isNotEmpty()) {
                binding.rvPhotoThumbnail.scrollToPosition(
                    photoViewModel.findPositionByTitle(
                        binding.etSearch.text.toString(),
                        photoDataList
                    )
                )

            }
        }
    }

    private fun viewModelInit() {
        // Directly observe database data
        photoViewModel.photos.observe(viewLifecycleOwner) { data ->
            Timber.tag("PhotoFragment").d("photoViewModel.photo, data: %s", data)
            if (data.isNullOrEmpty()) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = getString(R.string.common_no_record)

                if (!CheckInternet.isOnline(requireActivity())) {
                    binding.tvStatus.text = getString(R.string.common_no_internet)
                }

            } else {
                binding.tvStatus.visibility = View.GONE
                updateUI(data)

            }
        }
    }

//    private fun observeDataFromDatabase() {
//        photoViewModel.observePhotoFromDb().observe(viewLifecycleOwner) {
//                data ->
////            val list: ArrayList<GetPhotoRespItem> = ArrayList()
////            data.forEach {
////                list.add(it)
////            }
////            updateUI(list)
//        }
//    }


    private fun updateUI(data: List<PhotoItem>) {
        Timber.tag("photoListFragment").d("updateUI: %s", data)
        if (data.isNotEmpty()) {
            //photoDataList = data
            photoListAdapter.submitList(data)
        }
    }

    private fun getPhotoFromApi() {

        photoViewModel.getPhotoFromApi(
            onLoadingListener = object : OnLoadingEventListener {
                override fun startLoading() {
                    binding.llRefresh.isRefreshing = true
                }

                override fun stopLoading() {
                    Timber.tag("photoListFragment").d("stopLoading()")
                    binding.llRefresh.isRefreshing = false
                }
            })

    }

    override fun onResume() {
        super.onResume()
        getPhotoFromApi()
    }
}