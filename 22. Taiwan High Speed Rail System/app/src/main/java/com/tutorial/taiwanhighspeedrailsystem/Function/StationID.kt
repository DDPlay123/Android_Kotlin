package com.tutorial.taiwanhighspeedrailsystem.Function

import java.util.*

class StationID {
    private val dictionary: Dictionary<String, String> = Hashtable()
    fun dict(id: String?): String? {
        dictionary.put("南港高鐵站", "0990")
        dictionary.put("台北高鐵站", "1000")
        dictionary.put("板橋高鐵站", "1010")
        dictionary.put("桃園高鐵站", "1020")
        dictionary.put("新竹高鐵站", "1030")
        dictionary.put("苗栗高鐵站", "1035")
        dictionary.put("台中高鐵站", "1040")
        dictionary.put("彰化高鐵站", "1043")
        dictionary.put("雲林高鐵站", "1047")
        dictionary.put("嘉義高鐵站", "1050")
        dictionary.put("台南高鐵站", "1060")
        dictionary.put("左營高鐵站", "1070")
        dictionary.put("0", "南下")
        dictionary.put("1", "北上")
        return dictionary[id]
    }
}