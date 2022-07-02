package com.tutorial.taiwanhighspeedrailsystem.Data

class ObjectTrainNumber {
    var StopTimes = arrayOf<stopTimes>()
    class stopTimes {
        // 第 N 站
        var StopSequence = ""
        // 站名
        lateinit var StationName: stationName
        class stationName {
            var Zh_tw = ""
        }
        // 發車時間
        var DepartureTime = ""
    }
}