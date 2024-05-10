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
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Compress the bitmap
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Adjust quality as needed

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
