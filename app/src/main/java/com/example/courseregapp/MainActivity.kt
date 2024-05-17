package com.example.courseregapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getBtn()

    }
    private fun getBtn(){
        val regCourseBtn = findViewById<CardView>(R.id.reg_course_btn)
        regCourseBtn.setOnClickListener {
            changePage(RegCourseActivity::class.java)
        }
        val myProfileBtn = findViewById<CardView>(R.id.my_profile_btn)
        myProfileBtn.setOnClickListener {
            changePage(MyProfileActivity::class.java)
        }
        val scheduleBtn = findViewById<CardView>(R.id.schedule_btn)
        scheduleBtn.setOnClickListener {
            changePage(ScheduleActivity::class.java)
        }
        val courseFindBtn = findViewById<CardView>(R.id.course_find_btn)
        courseFindBtn.setOnClickListener {
            changePage(CourseFindActivity::class.java)
        }
    }
    private fun changePage(activity: Class<*>){
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}