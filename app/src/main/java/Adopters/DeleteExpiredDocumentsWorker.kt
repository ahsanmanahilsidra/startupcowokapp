import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class DeleteExpiredDocumentsWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // Initialize Firestore
            val db = FirebaseFirestore.getInstance()

            // Get the current date
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Define the collection reference
            val bookingCollection = db.collection("Booking")

            // Query documents where expireDate equals current date
            val expiredDocuments = bookingCollection
                .whereEqualTo("bookingexpire", currentDate)
                .get()
                .await()

            // Delete expired documents
            for (document in expiredDocuments) {
                bookingCollection.document(document.id).delete().await()
                println("Document ${document.id} successfully deleted.")
            }

            Result.success()
        } catch (e: Exception) {
            println("Error deleting expired documents: $e")
            Result.failure()
        }
    }
}
