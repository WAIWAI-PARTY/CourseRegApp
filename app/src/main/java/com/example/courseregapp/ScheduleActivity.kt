package com.example.courseregapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScheduleActivity : AppCompatActivity() {
    private val selectedCourses: List<Course> = Global.selectedCourses
    private lateinit var homeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tableLayout = findViewById<TableLayout>(R.id.schedule_table)
        populateScheduleTable(tableLayout)

    }
    private fun populateScheduleTable(tableLayout: TableLayout) {
        // Define the time slots
        for (time in 9..18) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            // Time slot column
            val timeTextView = TextView(this)
            timeTextView.text = "$time:00"
            timeTextView.gravity = android.view.Gravity.CENTER
            timeTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            tableRow.addView(timeTextView)

            // Days columns
            for (day in 1..5) {
                val dayTextView = TextView(this)
                dayTextView.gravity = android.view.Gravity.CENTER
                dayTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

                // Check if there is a course for this day and time
                val course = selectedCourses.find { it.day == day && it.startTime <= time.toInt() && it.endTime >= time.toInt() }
                val temp_id = (course?.id?.substring(0,2) ?: "") +'\n'+ (course?.id?.substring(2) ?: "")
                dayTextView.text = temp_id
                tableRow.addView(dayTextView)
            }

            tableLayout.addView(tableRow)
            homeButton = findViewById(R.id.home_button)
            homeButton.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}