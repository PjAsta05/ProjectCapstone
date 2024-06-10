package com.example.capstoneproject.ui.camera

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
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
import com.example.capstoneproject.helper.ImageClassifierHelper
import com.example.capstoneproject.ui.result.ResultActivity
import com.example.capstoneproject.ui.uriToBitmap
import com.google.common.util.concurrent.ListenableFuture
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.vision.classifier.Classifications

class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedCallback {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
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
    private var label: String = ""
    private var score: Int = 0

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            showLoading(true)
            currentImageUri = uri
            analyzeImage()
            //analyzeImage2(currentImageUri!!)
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
            showLoading(true)
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

//    private fun analyzeImage2() {
//        val model = AgemanModelWithMetadata250.newInstance(this)
//        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, currentImageUri)
//        val img = Bitmap.createScaledBitmap(bitmap, 250, 250, true)
//        val image = TensorImage.fromBitmap(img)
//        val outputs = model.process(image)
//        val probability = outputs.probabilityAsCategoryList
//        Log.d("ImageClassifierHelper", "Result: $probability")
//
//        model.close()
//    }

    private fun analyzeImage() {
        currentImageUri?.let {
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        showToast("Error: $error")
                    }

                    override fun onResult(result: List<Classifications>?) {
                        result?.forEach { classifications ->
                            classifications.categories.forEach { category ->
                                label = category.label
                                score = (category.score * 100).toInt()
                                Log.d("Label", label)
                                Log.d("Score", score.toString())
                            }
                        }
                        navigateToResult(label, score)
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(it)
        }?: showToast("No Image Selected")
        showLoading(false)
    }

    private fun navigateToResult(label: String, score: Int) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, currentImageUri.toString())
        intent.putExtra(EXTRA_LABEL, label)
        intent.putExtra(EXTRA_SCORE, score)
        intent.putExtra("token", token)
        startActivity(intent)
        finish()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        currentImageUri = outputFileResults.savedUri
        analyzeImage()
        //analyzeImage2(currentImageUri!!)
    }

    override fun onError(exception: ImageCaptureException) {
        exception.printStackTrace()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE = "Image"
        const val EXTRA_LABEL = "Label"
        const val EXTRA_SCORE = "Score"
    }
}