package com.tutorial.contractbook

data class Items(val name: String, val phone: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Items

        if (name != other.name) return false
        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + phone.hashCode()
        return result
    }
}