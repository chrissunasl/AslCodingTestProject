package com.example.aslcodingtestproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aslcodingtestproject.R
import com.example.aslcodingtestproject.databinding.FragmentFirstBinding
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import com.example.aslcodingtestproject.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val photoViewModel: PhotoViewModel by viewModels()
    private var photo : GetPhotoRespX? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("onViewCreated")
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        getPhoto()
    }

    private fun getPhoto() {
        Timber.i("getPhoto()")
        Log.d("chris", "photoViewModel.photo")
        photoViewModel.photo.observe(viewLifecycleOwner) { data ->
            this.photo = data
            Log.d("chris","photoViewModel.photo.observe(this): $data")
            Log.d("chris","photoViewModel.photo.observe(this): ${this.photo}")
        }

        Log.d("chris", "photoViewModel.photo value $photo")


        CoroutineScope(Dispatchers.IO).launch {
            photoViewModel.getPhoto()
        }.invokeOnCompletion {
            Log.d("chris","photo: ${this.photo}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getPhoto()
    }
}