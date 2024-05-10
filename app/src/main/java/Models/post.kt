package Models

class post {
    var posturl: String = ""
    var caption: String = ""
    var userid: String = ""
    var time: String = ""
    var postid:String=""
    constructor()
    constructor(posturl: String, caption: String) {
        this.posturl = posturl
        this.caption = caption
    }

    constructor(posturl: String, caption: String, userid: String, time: String, postid: String) {
        this.posturl = posturl
        this.caption = caption
        this.userid = userid
        this.time = time
        this.postid = postid
    }


}