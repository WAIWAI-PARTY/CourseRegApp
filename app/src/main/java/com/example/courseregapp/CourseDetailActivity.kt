package com.example.courseregapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CourseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val courseId = intent.getStringExtra("course_id")
        val courseName = intent.getStringExtra("course_name")
        val courseType = intent.getStringExtra("course_type")
        val courseDay = intent.getStringExtra("course_day")
        val courseStartTime = intent.getIntExtra("course_start_time", 0)
        val courseEndTime = intent.getIntExtra("course_end_time", 0)
        val courseDetail = intent.getStringExtra("course_detail")

        findViewById<TextView>(R.id.text_course_id_data).text = courseId
        findViewById<TextView>(R.id.text_course_name_data).text = courseName
        findViewById<TextView>(R.id.text_course_type_data).text = courseType
        findViewById<TextView>(R.id.text_course_day_data).text = courseDay
        findViewById<TextView>(R.id.text_course_time_data).text = "$courseStartTime - $courseEndTime"
        findViewById<TextView>(R.id.text_course_detail_data).text = courseDetail
    }
}