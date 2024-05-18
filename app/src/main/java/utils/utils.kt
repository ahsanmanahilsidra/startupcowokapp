import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID

fun uploadImage(context: Context, uri: Uri, folderName: String, callback: (String?) -> Unit) {
    // Decode the image file
    val inputStream = context.contentResolver.openInputStream(uri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)

    // Define maximum image dimensions
    val maxWidth = 1024
    val maxHeight = 1024

    // Calculate the scaled width and height maintaining aspect ratio
    var scaledWidth = originalBitmap.width
    var scaledHeight = originalBitmap.height

    if (scaledWidth > maxWidth || scaledHeight > maxHeight) {
        val aspectRatio = scaledWidth.toFloat() / scaledHeight.toFloat()
        if (aspectRatio > 1) {
            scaledWidth = maxWidth
            scaledHeight = (scaledWidth / aspectRatio).toInt()
        } else {
            scaledHeight = maxHeight
            scaledWidth = (scaledHeight * aspectRatio).toInt()
        }
    }

    // Create the scaled bitmap
    val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true)

    // Compress the bitmap
    val outputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Adjust quality as needed

    // Convert the compressed bitmap to ByteArray
    val compressedByteArray = outputStream.toByteArray()

    // Upload the compressed image asynchronously
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putBytes(compressedByteArray)
        .addOnSuccessListener { uploadTask ->
            uploadTask.storage.downloadUrl
                .addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    callback(imageUrl)
                }
                .addOnFailureListener {
                    // Handle failure to get download URL
                    callback(null)
                }
        }
        .addOnFailureListener { exception ->
            // Handle failure to upload file
            callback(null)
        }
}
