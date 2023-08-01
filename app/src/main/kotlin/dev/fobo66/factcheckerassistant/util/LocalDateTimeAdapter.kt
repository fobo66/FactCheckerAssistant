package dev.fobo66.factcheckerassistant.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
    }
}
