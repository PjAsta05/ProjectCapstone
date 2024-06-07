package com.example.capstoneproject.ui.camera

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import com.example.capstoneproject.ml.AgemanModelWithMetadata
import com.example.capstoneproject.ui.result.ResultActivity
import com.example.capstoneproject.ui.uriToBitmap
import com.google.common.util.concurrent.ListenableFuture
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category

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

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            val bitmap = uriToBitmap(this, currentImageUri!!)
            if (bitmap != null) {
                analyzeImage(bitmap)
            } else {
                Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
        startGallery()
        takePicture()
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

    private fun analyzeImage(bitmap: Bitmap) {
        val model = AgemanModelWithMetadata.newInstance(applicationContext)
        val image = TensorImage.fromBitmap(bitmap)
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList.maxByOrNull { it.score }
        if (probability == null) {
            Log.d("CLASSIFICATION", "No result")
        } else {
            navigateToResult(probability)
        }
        model.close()
        Log.d("CLASSIFICATION", probability.toString())
    }

    private fun navigateToResult(probability: Category) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, currentImageUri.toString())
        intent.putExtra(EXTRA_LABEL, probability.label)
        intent.putExtra(EXTRA_SCORE, probability.score)
        startActivity(intent)
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        currentImageUri = outputFileResults.savedUri
        val bitmap = uriToBitmap(this, currentImageUri!!)
        if (bitmap != null) {
            analyzeImage(bitmap)
        } else {
            Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(exception: ImageCaptureException) {
        exception.printStackTrace()
    }

    companion object {
        const val EXTRA_IMAGE = "Image"
        const val EXTRA_LABEL = "Label"
        const val EXTRA_SCORE = "Score"
    }
}