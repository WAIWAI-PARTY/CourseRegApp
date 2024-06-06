package com.example.courseregapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView


class RegCourseActivity : AppCompatActivity() {
    private lateinit var courseAdapter: CourseRegRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var previewButton: Button
    private lateinit var confirmButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg_course)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.RegCourseItem)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val courseList = parseCourses(this)
        courseAdapter = CourseRegRecyclerAdapter(courseList)
        recyclerView.adapter = courseAdapter

        previewButton = findViewById(R.id.button_preview_schedule)
        previewButton.setOnClickListener {
            val selectedCourses = courseAdapter.getSelectedCourses()
            val dialogFragment = PreviewScheduleDialogFragment.newInstance(selectedCourses)
            dialogFragment.show(supportFragmentManager, "previewSchedule")
        }

        confirmButton = findViewById(R.id.button_confirm)
        confirmButton.setOnClickListener{
            Global.selectedCourses = courseAdapter.getSelectedCourses().toList()
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity((intent))
        }

        searchView = findViewById(R.id.regSearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    courseAdapter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    courseAdapter.filter(it)
                }
                return true
            }
        })
    }
}