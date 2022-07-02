package com.tutorial.taiwanhighspeedrailsystem.Data

class ObjectStation {
    lateinit var StationName: stationName
    class stationName {
        var Zh_tw = ""
    }
    var StationAddress = ""
    lateinit var StationPosition: stationPosition
    class stationPosition {
        var PositionLon = 0.0
        var PositionLat = 0.0
    }
}