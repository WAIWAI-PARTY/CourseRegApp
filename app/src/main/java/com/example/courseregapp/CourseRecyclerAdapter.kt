package com.example.courseregapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseRecyclerAdapter(private val courses: List<Course>) :
    RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item_layout, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentCourse = courses[position]

        holder.courseId.text = currentCourse.id
        holder.courseName.text = currentCourse.name
        holder.courseType.text = currentCourse.type
        holder.courseDay.text = currentCourse.day
        holder.courseTime.text = "${currentCourse.startTime} - ${currentCourse.endTime}"
    }

    override fun getItemCount() = courses.size

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseId: TextView = itemView.findViewById(R.id.text_course_id)
        val courseName: TextView = itemView.findViewById(R.id.text_course_name)
        val courseType: TextView = itemView.findViewById(R.id.text_course_type)
        val courseDay: TextView = itemView.findViewById(R.id.text_course_day)
        val courseTime: TextView = itemView.findViewById(R.id.text_course_time)
    }
}
