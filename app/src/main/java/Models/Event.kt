package Models

import com.google.type.DateTime

class Event {
        var Eventurl:String=""
        var Eventtitle:String=""
    var Eventdiscription:String=""
    var  eventdate: String =""
    var eventlocation:String=""
    var eventtype:String=""
    var eventid:String=""
       constructor()
    constructor(
        Eventurl: String,
        Eventtitle: String,
        Eventdiscription: String,
        eventdate: String,
        eventlocation: String,
        eventtype: String,
        eventid: String,
    ) {
        this.Eventurl = Eventurl
        this.Eventtitle = Eventtitle
        this.Eventdiscription = Eventdiscription
        this.eventdate = eventdate
        this.eventlocation = eventlocation
        this.eventtype = eventtype
        this.eventid = eventid
    }

    constructor(
        Eventtitle: String,
        Eventdiscription: String,
        eventdate: String,
        eventlocation: String,
        eventtype: String,
        eventid: String,
    ) {
        this.Eventtitle = Eventtitle
        this.Eventdiscription = Eventdiscription
        this.eventdate = eventdate
        this.eventlocation = eventlocation
        this.eventtype = eventtype
        this.eventid = eventid
    }


}