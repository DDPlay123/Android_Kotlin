package com.tutorial.taiwanhighspeedrailsystem.Data

data class TrainNoItem(val StopSequence: String, val Zh_tw: String,
                       val DepartureTime: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrainNoItem

        if (StopSequence != other.StopSequence) return false
        if (Zh_tw != other.Zh_tw) return false
        if (DepartureTime != other.DepartureTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = StopSequence.hashCode()
        result = 31 * result + Zh_tw.hashCode()
        result = 31 * result + DepartureTime.hashCode()
        return result
    }
}