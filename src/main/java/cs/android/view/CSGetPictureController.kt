package cs.android.view

import android.Manifest.permission.*
import android.content.ContentValues
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.EXTRA_OUTPUT
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.view.View
import cs.android.extensions.dialog
import cs.android.image.CSBitmap.resizeImage
import cs.android.viewbase.CSView
import cs.android.viewbase.CSViewController
import cs.java.lang.CSLang.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class CSGetPictureController<T : View>(parent: CSViewController<T>, private var imagesDirName: String,
                                       private val onImageReady: (File) -> Unit) :
        CSViewController<T>(parent) {

    var title: String? = null
    var onProgress: (Boolean) -> Unit = {}

    private val requestCode = 386
    private val imagesDir: File = File(File(model().dataDir(), "Pictures"), imagesDirName)
    private val photoURI: Uri by lazy {
        context().contentResolver.insert(EXTERNAL_CONTENT_URI, ContentValues())
    }

    init {
        imagesDir.mkdirs()
    }

    override fun show(): CSView<T> {
        requestPermissions(list(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                { onPermissionsGranted() },
                { showSnackBar("Some permissions not granted for taking photos") })
        return this
    }

    private fun onPermissionsGranted() {
        val upload = "Album"
        val take = "Camera"
        dialog().buttons(upload, "Cancel", take) { value, _ ->
            if (upload == value) onSelectPhoto()
            else if (take == value) onTakePhoto()
        }.text(title, null).show()
    }

    private fun onImageSelected(input: InputStream) {
        onProgress(YES)
        try {
            val image = File(imagesDir, generateRandomStringOfLength(4) + ".jpg")
            image.createNewFile()
            copy(input, FileOutputStream(image))
            resizeImage(image.absolutePath, 1024, 768)
            onImageReady(image)
        } catch (e: Exception) {
            error(e)
        }
        onProgress(NO)
    }

    private fun onSelectPhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        onActivityResult.add { registration, result ->
            if (result.isOK(requestCode)) onImageSelected(openInputStream(result.data.data))
            registration.cancel()
        }
        startActivityForResult(createChooser(intent, "Select Picture"), requestCode)
    }

    private fun onTakePhoto() {
        val intent = Intent(ACTION_IMAGE_CAPTURE)
        intent.putExtra(EXTRA_OUTPUT, photoURI)
        onActivityResult.add { registration, result ->
            if (result.isOK(requestCode)) onImageSelected(openInputStream(photoURI))
            registration.cancel()
        }
        startActivityForResult(intent, requestCode)
    }

}