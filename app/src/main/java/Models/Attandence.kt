package Models

import com.google.type.DateTime
import org.checkerframework.checker.units.qual.Time
import java.util.Date

class Attandence {
    var userid:String=""
    var Attandence:String=""
    var date:String=""
    var time:String=""
  constructor()
    constructor(userid: String, Attandence: String, date: String, time: String) {
        this.userid = userid
        this.Attandence = Attandence
        this.date = date
        this.time = time
    }


}