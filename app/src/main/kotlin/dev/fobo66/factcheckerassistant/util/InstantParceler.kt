package dev.fobo66.factcheckerassistant.util

import android.os.Parcel
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parceler

object InstantParceler : Parceler<Instant?> {
    override fun create(parcel: Parcel): Instant? {
        val timestamp = parcel.readString()
        return if (timestamp != null) {
            Instant.parse(timestamp)
        } else {
            null
        }
    }

    override fun Instant?.write(parcel: Parcel, flags: Int) {
        parcel.writeString(this?.toString())
    }
}
