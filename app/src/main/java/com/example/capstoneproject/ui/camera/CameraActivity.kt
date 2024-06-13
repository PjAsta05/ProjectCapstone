package com.example.capstoneproject.ui.camera

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.capstoneproject.databinding.ActivityCameraBinding
import com.example.capstoneproject.ui.result.ResultActivity
import com.google.common.util.concurrent.ListenableFuture

class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedCallback {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var camera: Camera
    private lateinit var imageCapture: ImageCapture
    private val backCameraSelector by lazy {
        getCameraSelector(CameraSelector.LENS_FACING_BACK)
    }
    private val frontCameraSelector by lazy {
        getCameraSelector(CameraSelector.LENS_FACING_FRONT)
    }
    private var currentImageUri: Uri? = null
    private var token: String = ""

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            navigateToResultActivity()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getToken()
        setupActionBar()
        startCamera()
        startGallery()
        takePicture()
    }

    private fun getToken() {
        token = intent.getStringExtra("token").toString()
    }

    private fun getCameraSelector(lensFacing: Int) = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider, backCameraSelector)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun startGallery() {
        binding.buttonGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider, cameraSelector: CameraSelector) {
        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(binding.cameraX.surfaceProvider)
        imageCapture = ImageCapture.Builder().setTargetRotation(binding.cameraX.display.rotation).build()

        camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun takePicture() {
        binding.buttonCapture.setOnClickListener {
            imageCapture.takePicture(
                getImageOutputOptions(),
                ContextCompat.getMainExecutor(this),
                this
            )
        }
    }

    private fun getImageOutputOptions(): ImageCapture.OutputFileOptions {
        val contentResolver = applicationContext.contentResolver
        val contentValue = ContentValues()
        contentValue.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}")
        contentValue.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        return ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValue
        ).build()
    }

    private fun navigateToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, currentImageUri.toString())
        intent.putExtra(EXTRA_TOKEN, token)
        startActivity(intent)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        currentImageUri = outputFileResults.savedUri
        navigateToResultActivity()
    }

    override fun onError(exception: ImageCaptureException) {
        exception.printStackTrace()
    }

    companion object {
        const val EXTRA_IMAGE = "Image"
        const val EXTRA_TOKEN = "Token"
    }
}