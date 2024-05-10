package Models

class Like {
    var likeid:String=""
    var userid:String=""
    var postid:String=""
    constructor()
    constructor(likeid: String, userid: String, postid: String) {
        this.likeid = likeid
        this.userid = userid
        this.postid = postid
    }

}