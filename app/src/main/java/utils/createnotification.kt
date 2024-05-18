package utils

import Models.notification
import android.os.Build
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun createnotification(title:String, message:String, fragment:String, foor:String, from: String, img:String){
    var notificationid=Firebase.firestore.collection("Notification").document().id
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    val formattedTime = currentTime.format(formatter)
    var notification=notification(notificationid,title,message,fragment,foor,from,img,formattedTime.toString())
    Firebase.firestore.collection("Notification").document(notificationid).set(notification)


}
