package com.tutorial.taiwanhighspeedrailsystem.Data

class ObjectDailyTimetable {
    lateinit var DailyTrainInfo: dailyTrainInfo
    class dailyTrainInfo {
        var TrainNo = ""  // 車次
        var Direction = ""  // 北上:1、南下:0
    }
    lateinit var OriginStopTime: originStopTime
    class originStopTime {
        var DepartureTime = ""  // 出發時間
    }
    lateinit var DestinationStopTime: destinationStopTime
    class  destinationStopTime {
        var ArrivalTime = ""  // 到站時間
    }
}