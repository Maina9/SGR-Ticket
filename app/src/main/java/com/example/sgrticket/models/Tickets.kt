package com.example.sgrticket.models

class Tickets {
    var Uname: String = ""
    var source: String=""
    var destination: String=""
    var ticket_number:String=""
    var record_id:String = ""

    constructor(Uname: String, source:String,ticket_number:String, record_id:String){
        this.Uname = Uname
        this.source = source
        this.destination = destination
        this.ticket_number = ticket_number
        this.record_id = record_id
    }
}