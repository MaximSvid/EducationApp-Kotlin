package com.example.educationappmaximsvidrak.model

class Message (
    val role: String,
    val content: String
) {
    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}
//class Message (
//    var message: String,
//    var sentBy: String
//) {
//    companion object {
//        var SENT_BY_ME = "me"
//        var SENT_BY_BOT = "bot"
//    }
//}