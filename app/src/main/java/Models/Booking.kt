package Models

class Booking {
    var bookingid:String=""
    var userid:String=""
    var spaceid:String=""
    var Spacename:String=""
    var bookingdate:String=""
    var bookingexpire:String=""
    var price:String=""
    constructor()
    constructor(
        bookingid: String,
        userid: String,
        spaceid: String,
        Spacename: String,
        bookingdate: String,
        bookingexpire: String,
        price: String,
    ) {
        this.bookingid = bookingid
        this.userid = userid
        this.spaceid = spaceid
        this.Spacename = Spacename
        this.bookingdate = bookingdate
        this.bookingexpire = bookingexpire
        this.price = price
    }

}