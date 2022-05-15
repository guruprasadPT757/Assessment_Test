package com.app.assessment.test.camera

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.app.assessment.test.databinding.FragmentCameraBinding
import com.app.assessment.test.movie.list.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias CornersListener = () -> Unit

class CameraFragment: BottomSheetDialogFragment(){

    private lateinit var cameraBinding: FragmentCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cameraBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return cameraBinding.root
    }

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null


    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService


    private fun getStatusBarHeight(): Int {
        val resourceId = requireContext().resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            requireContext().resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       startCamera()
//        camera_capture_button.setOnClickListener { takePhoto() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
//        cameraExecutor = Executors.newCachedThreadPool()
    }

    private fun startCamera() {

//        OpenCVLoader.initDebug()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder().build()

            imageCapture = ImageCapture.Builder().build()

            imageAnalyzer = ImageAnalysis.Builder().build().apply {
                setAnalyzer(Executors.newSingleThreadExecutor(), CornerAnalyzer {
                    val bitmap = cameraBinding.viewFinder.bitmap
//                    val img = Mat()
//                    Utils.bitmapToMat(bitmap, img)
                    bitmap?.recycle()
                    // Do image analysis here if you need bitmap
                })
            }
            // Select back camera
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalyzer, preview, imageCapture)
                preview?.setSurfaceProvider(cameraBinding.viewFinder.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create timestamped output file to hold the image
        val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val msg = "Photo capture succeeded: $savedUri"
                showToast(msg)
                Log.d(TAG, msg)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isOffline = true
    }

    override fun onResume() {
        super.onResume()
        isOffline = false
    }

    fun getOutputDirectory(): File {

        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, "Images").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    companion object {
        val TAG = "CameraXFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        var isOffline = false // prevent app crash when goes offline
    }

    private class CornerAnalyzer(private val listener: CornersListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            if (!isOffline) {
                listener()
            }
            imageProxy.close() // important! if it is not closed it will only run once
        }
    }

}