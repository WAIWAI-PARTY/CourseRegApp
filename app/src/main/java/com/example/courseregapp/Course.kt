package com.example.courseregapp

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

data class Course(
    val id: String,
    val name: String,
    val type: String,
    val day: String,
    val startTime: Int,
    val endTime: Int,
    val detail: String,
)

fun parseCourses(context: Context): List<Course> {
    val courses = mutableListOf<Course>()
    val parser = context.resources.getXml(R.xml.courses)

    var eventType = parser.eventType
    var currentCourse: Course? = null
    var currentTag: String? = null
    var currentText = ""

    try {
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name

            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName == "course") {
                        currentCourse = Course("", "", "", "", 0, 0, "")
                    } else {
                        currentTag = tagName
                    }
                }
                XmlPullParser.TEXT -> {
                    currentText = parser.text
                }
                XmlPullParser.END_TAG -> {
                    if (currentCourse != null) {
                        when (tagName) {
                            "id" -> currentCourse = currentCourse.copy(id = currentText)
                            "name" -> currentCourse = currentCourse.copy(name = currentText)
                            "type" -> currentCourse = currentCourse.copy(type = currentText)
                            "day" -> currentCourse = currentCourse.copy(day = currentText)
                            "startTime" -> currentCourse = currentCourse.copy(startTime = currentText.toInt())
                            "endTime" -> currentCourse = currentCourse.copy(endTime = currentText.toInt())
                            "detail" -> currentCourse = currentCourse.copy(detail = currentText)
                            "course" -> {
                                courses.add(currentCourse)
                                currentCourse = null
                            }
                        }
                    }
                }
            }
            eventType = parser.next()
        }
    } catch (e: XmlPullParserException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return courses
}

