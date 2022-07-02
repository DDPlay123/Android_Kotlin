package com.tutorial.taiwanhighspeedrailsystem.Data

data class TrainItem(
    val TrainNo: String, val Direction: String?,
    val DepartureTime: String,
    val Duration: String,
    val ArrivalTime: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrainItem

        if (TrainNo != other.TrainNo) return false
        if (Direction != other.Direction) return false
        if (DepartureTime != other.DepartureTime) return false
        if (Duration != other.Duration) return false
        if (ArrivalTime != other.ArrivalTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = TrainNo.hashCode()
        result = 31 * result + (Direction?.hashCode() ?: 0)
        result = 31 * result + DepartureTime.hashCode()
        result = 31 * result + Duration.hashCode()
        result = 31 * result + ArrivalTime.hashCode()
        return result
    }
}