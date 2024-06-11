package com.example.courseregapp

import android.content.Intent
import android.graphics.Color
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
    private val selected_courses: List<Course> = Global.selectedCourses
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
        PopulateScheduleTable(tableLayout)

    }

    private fun PopulateScheduleTable(table_layout: TableLayout) {
        val colors = listOf(
            Color.parseColor("#00008B"), // Dark Blue
            Color.parseColor("#8B0000"), // Dark Red
            Color.parseColor("#8B008B"), // Dark Magenta
            Color.parseColor("#556B2F"), // Dark Olive Green
            Color.parseColor("#8B4513"), // Saddle Brown
            Color.parseColor("#2F4F4F"), // Dark Slate Gray
            Color.parseColor("#000080"), // Navy
            Color.parseColor("#4B0082"), // Indigo
            Color.parseColor("#2E8B57"), // Sea Green
            Color.parseColor("#483D8B"), // Dark Slate Blue
            Color.parseColor("#8B0000")  // Dark Red
        )
        table_layout.post {
            val table_height = table_layout.height
            val row_height = table_height / 11

            for (time in 9..18) {
                val table_row = TableRow(this)
                table_row.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    row_height
                )

                val time_text_view = TextView(this)
                time_text_view.text = "$time:00"
                time_text_view.layoutParams =
                    TableRow.LayoutParams(0, row_height, 1f)
                time_text_view.setTextColor(Color.WHITE)
                time_text_view.setBackgroundResource(R.drawable.border)
                table_row.addView(time_text_view)

                for (day in 1..5) {
                    val day_text_view = TextView(this)
                    day_text_view.gravity = android.view.Gravity.CENTER
                    day_text_view.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f)
                    day_text_view.setTextColor(Color.WHITE)
                    day_text_view.setBackgroundResource(R.drawable.border)

                    val course =
                        selected_courses.find { it.day == day && it.startTime <= time && it.endTime >= time }
                    if (course != null) {
                        val course_id = course.id.filter { it.isDigit() }.toInt()
                        day_text_view.setBackgroundColor(colors[course_id % colors.size])
                        val temp_id = (course.id.substring(0, 2) + '\n' + course.id.substring(2))
                        day_text_view.text = temp_id
                    }
                    table_row.addView(day_text_view)
                }

                table_layout.addView(table_row)
            }

            val home_button = findViewById<Button>(R.id.home_button)
            home_button.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

}