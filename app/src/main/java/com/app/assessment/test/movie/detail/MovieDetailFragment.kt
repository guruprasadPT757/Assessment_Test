package com.app.assessment.test.movie.detail

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.app.assessment.test.Preference
import com.app.assessment.test.R
import com.app.assessment.test.databinding.FragmentMovieDetailBinding
import com.app.assessment.test.models.movie.MovieItem
import com.app.assessment.test.movie.list.showSnackBar
import com.app.assessment.test.movie.list.showToast


class MovieDetailFragment: Fragment() {

    private lateinit var movieDetailBinding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieDetailBinding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return movieDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieItem = arguments?.getParcelable<MovieItem>("movie_item")

        movieDetailBinding.movieItem = movieItem
        val viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        viewModel.videoLinkLiveData.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            movieDetailBinding.progress.hide()
            if (it.site == "YouTube") {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=${it.key}")
                    )
                )
                //making it null to avoid loading video again when orientation is changed
                viewModel.videoLinkLiveData.value = null
            } else {
                showSnackBar(getString(R.string.trailer_not_available))
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){
            movieDetailBinding.progress.hide()
            showSnackBar(it)
        }
        movieDetailBinding.btnWatchTrailer.setOnClickListener {
            movieDetailBinding.progress.show()
            movieItem?.id?.let {
                viewModel.getVideoInformation(it)
            }
        }

        movieDetailBinding.ibComment.setOnClickListener {
            val comment = movieDetailBinding.etMovieComment.text.toString()
            if (comment.isNotEmpty()) {
                Preference(requireContext()).putString(Preference.MOVIE_COMMENTS, comment)
            } else {
                showToast(getString(R.string.please_enter_comments))
            }
            movieDetailBinding.etMovieComment.setText("")
        }

        movieDetailBinding.btnSetProfile.setOnClickListener {
            when(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)) {
                PackageManager.PERMISSION_GRANTED -> {
                    cameraPermission.launch(Manifest.permission.CAMERA)
                }
                PackageManager.PERMISSION_DENIED -> {
                    requestCameraPermission()
                }
            }
        }
    }

    private val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            cameraActivityResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        } else {
            requestCameraPermission()
        }
    }

    private val cameraActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {
            AlertDialog.Builder(requireActivity())
                .setTitle("Permission needed")
                .setMessage("This permission is needed to capture image for profile")
                .setPositiveButton("permit") { dialog, which ->
                    cameraPermission.launch(Manifest.permission.CAMERA) }
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.dismiss() }
                .create().show()
        } else {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
    }


}