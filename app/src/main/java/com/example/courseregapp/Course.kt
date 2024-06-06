package com.example.courseregapp

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

data class Course(
    val id: String,
    val name: String,
    val type: String,
    val day: Int,
    val startTime: Int,
    val endTime: Int,
    val detail: String,
)
    : Parcelable {

    fun toDay(): String {
        return when (day) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Invalid day"
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeInt(day)
        parcel.writeInt(startTime)
        parcel.writeInt(endTime)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}


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
                        currentCourse = Course("", "", "", 0, 0, 0, "")
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
                            "day" -> currentCourse = currentCourse.copy(day = currentText.toInt())
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

