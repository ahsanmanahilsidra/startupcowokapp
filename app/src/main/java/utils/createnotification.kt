package utils

import Models.notification
import android.os.Message
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun createnotification(title:String,message:String,fragment:String,foor:String,from: String,img:String){
    var notificationid=Firebase.firestore.collection("Notification").document().id
    var notification=notification(notificationid,title,message,fragment,foor,from,img)
    Firebase.firestore.collection("Notification").document(notificationid).set(notification)


}
