package Models

class Booking {
    var bookingurl:String=""
    var title:String=""
    var bookingpric:String=""
    var seats:String=""
    var bookingcaption:String=""
    constructor()
    constructor(
        bookingurl: String,
        title: String,
        bookingpric: String,
        seats: String,
        bookingcaption: String,
    ) {
        this.bookingurl = bookingurl
        this.title = title
        this.bookingpric = bookingpric
        this.seats = seats
        this.bookingcaption = bookingcaption
    }


}