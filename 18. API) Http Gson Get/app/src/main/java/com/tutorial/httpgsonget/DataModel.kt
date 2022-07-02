package com.tutorial.httpgsonget

class DataModel {
    lateinit var results: Result
    class Result {
        lateinit var content: Array<Record>
        class Record {
            var lat = 0.0f
            var lng = 0.0f
            var name = ""
        }
    }
}