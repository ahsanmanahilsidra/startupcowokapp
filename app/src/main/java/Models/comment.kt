package Models

class comment
    {
        var comment :String=""
        var userid :String=""
        var postid :String=""
        var commenturl:String=""
        var commentid:String=""
        var time:String=""
        constructor()
        constructor(
            comment: String,
            userid: String,
            postid: String,
            commenturl: String,
            commentid: String,
            time: String,
        ) {
            this.comment = comment
            this.userid = userid
            this.postid = postid
            this.commenturl = commenturl
            this.commentid = commentid
            this.time = time
        }


    }