import Models.Attandence
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class markapsent(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        try {
            // Initialize Firestore
            val db = FirebaseFirestore.getInstance()

            // Get the current date
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Define the collection references
            val usersCollection = db.collection("user")
            val attendanceCollection = db.collection("Attandence")

            // Get all user IDs
            val allUserIds = usersCollection.get().await().documents.map { it.id }

            // Iterate through all user IDs
            for (userId in allUserIds) {
                // Check if there's no document for the current date and user position ID
                val attendanceQuery = attendanceCollection
                    .whereEqualTo("userid", userId)
                    .whereEqualTo("date", currentDate)
                // Add more conditions if necessary for position ID comparison

                val existingAttendance = attendanceQuery.get().await()

                if (existingAttendance.isEmpty) {
                    val currentTime = LocalTime.now()
                    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
                    val formattedTime = currentTime.format(formatter)
                    // If no document exists, create a new attendance document
                    val attendance = Attandence(userId, "Absent", currentDate,formattedTime.toString())
                    FirebaseFirestore.getInstance().collection("Attandence")
                        .add(attendance).addOnSuccessListener {
                            Toast.makeText(applicationContext,"yes",Toast.LENGTH_SHORT).show()
                        }
                } else {
                    println("Attendance document already exists for user ID $userId and the current date.")
                }
            }

            return Result.success()
        } catch (e: Exception) {
            println("Error adding attendance document: $e")
            return Result.failure()
        }
    }
}
