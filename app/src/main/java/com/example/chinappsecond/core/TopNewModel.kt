package com.example.chinappsecond.core

import com.example.chinappsecond.R

data class TopNewModel(
    val title: String = "",
    val time: String = "",
    val Source: String = "",
    val text: String = "",
    val updatalist: List<TopNewTemperatures> = emptyList(),
    val image: Int = R.drawable.new_10_image_2
)

data class TopNewTemperatures(
    val id: Int,
    val content: String
)
