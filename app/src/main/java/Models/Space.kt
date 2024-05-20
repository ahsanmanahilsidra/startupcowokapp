package Models

class Space {
        var Spaceurl:String=""
       var Spacetitle:String=""
     var spaceid:String=""
    var price:String=""
    var about:String=""
    var totalseats:String=""
    var avalabeleseats:String=""
        constructor()
    constructor(
        Spaceurl: String,
        Spacetitle: String,
        spaceid: String,
        price: String,
        about: String,
        totalseats: String,
        avalabeleseats: String,
    ) {
        this.Spaceurl = Spaceurl
        this.Spacetitle = Spacetitle
        this.spaceid = spaceid
        this.price = price
        this.about = about
        this.totalseats = totalseats
        this.avalabeleseats = avalabeleseats
    }


}