package com.tutorial.taiwanhighspeedrailsystem.Data

class ObjectRestaurant {
    lateinit var results: Results
    class Results {
        lateinit var content: Array<Content>
        class Content {
            //var type = 0
            var lat = 0.0
            var lng = 0.0
            var name = ""
            var rating = 0.0
            var vicinity = ""
            var photo = ""
            //var phone = ""
            //var open = arrayListOf<String>()
            //var priceLevel = 0
            var reviewsNumber = 0
            //var placeID = ""
            //var index = 0
        }
    }
}