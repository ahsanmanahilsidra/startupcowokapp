package Models

import androidx.fragment.app.Fragment

class notification {
    var notificationid:String=""
    var title:String=""
    var message:String=""
    var fragment: String=""
    var foor:String=""
    var from:String=""
    var imgurl:String=""
    var time:String=""
    constructor()
    constructor(
        notificationid: String,
        title: String,
        message: String,
        fragment: String,
        foor: String,
        from: String,
        imgurl: String,
        time: String,
    ) {
        this.notificationid = notificationid
        this.title = title
        this.message = message
        this.fragment = fragment
        this.foor = foor
        this.from = from
        this.imgurl = imgurl
        this.time = time
    }

}