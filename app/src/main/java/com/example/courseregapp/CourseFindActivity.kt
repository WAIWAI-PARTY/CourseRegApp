package com.example.courseregapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class CourseFindActivity : AppCompatActivity() {
    val courseList = listOf(
        Course(
            id = "C001",
            name = "Introduction to Programming",
            type = "Compulsory",
            day = "Monday",
            startTime = 8,
            endTime = 10,
            detail = "Basic concepts of programming",
            schedule = arrayListOf("Variables and Data Types", "Control Structures", "Functions and Modules")
        ),
        Course(
            id = "C002",
            name = "Database Management Systems",
            type = "Elective",
            day = "Wednesday",
            startTime = 11,
            endTime = 13,
            detail = "Fundamentals of database systems",
            schedule = arrayListOf("Relational Model", "SQL Basics", "Normalization")
        ),
        Course(
            id = "C003",
            name = "Software Engineering",
            type = "Compulsory",
            day = "Friday",
            startTime = 14,
            endTime = 16,
            detail = "Principles and methodologies of software development",
            schedule = arrayListOf("Software Development Life Cycle", "Agile Methodology", "Testing and Quality Assurance")
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_course_find)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_courses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CourseRecyclerAdapter(courseList) // Replace courseList with your list of courses
        recyclerView.adapter = adapter
    }
}