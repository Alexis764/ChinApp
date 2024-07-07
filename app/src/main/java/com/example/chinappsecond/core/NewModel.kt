package com.example.chinappsecond.core

import com.example.chinappsecond.R
import java.time.LocalDate

data class NewModel(
    val title: String = "",
    val hot: Boolean = false,
    val text: String = "",
    val Source: String = "",
    val time: String = "",
    val image: Int = R.drawable.new_10_image_1
) {
    fun toDate(): NewModelDate {
        val timeSplit: List<String> = time.split("-")
        val timeDate = try {
            LocalDate.of(
                /* year = */ timeSplit[0].toInt(),
                /* month = */timeSplit[1].toInt(),
                /* dayOfMonth = */timeSplit[2].toInt()
            )
        } catch (exception: Exception) {
            LocalDate.now()
        }

        return NewModelDate(title, hot, text, Source, timeDate, image)
    }
}

data class NewModelDate(
    val title: String,
    val hot: Boolean,
    val text: String,
    val Source: String,
    val time: LocalDate,
    val image: Int = R.drawable.new_10_image_1
)
