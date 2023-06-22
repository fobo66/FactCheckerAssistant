package dev.fobo66.factcheckerassistant.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import java.time.format.DateTimeFormatterBuilder

class LocalDateTimeAdapter {

    private val formatter: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral('T')
            .append(ISO_LOCAL_TIME)
            .appendLiteral('Z')
            .toFormatter()
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return formatter.format(value)
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }
}
