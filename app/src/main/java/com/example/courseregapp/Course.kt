package com.example.courseregapp

data class Course
(
    val id: String,
    val name: String,
    val type: String,
    val day: String,
    val startTime: Int,
    val endTime: Int,
    val detail: String,
    val schedule: ArrayList<String>
)