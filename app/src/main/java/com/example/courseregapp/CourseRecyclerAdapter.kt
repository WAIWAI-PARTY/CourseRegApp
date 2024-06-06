package com.example.courseregapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseRecyclerAdapter(private val courses: List<Course>) :
    RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder>() {

    private var filteredCourses: MutableList<Course> = courses.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item_layout, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentCourse = filteredCourses[position]

        holder.courseId.text = currentCourse.id
        holder.courseName.text = currentCourse.name
        holder.courseType.text = currentCourse.type
        holder.courseDay.text = currentCourse.toDay()
        holder.courseTime.text = "${currentCourse.startTime} - ${currentCourse.endTime}"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CourseDetailActivity::class.java)
            intent.putExtra("course_id", currentCourse.id)
            intent.putExtra("course_name", currentCourse.name)
            intent.putExtra("course_type", currentCourse.type)
            intent.putExtra("course_day", currentCourse.toDay())
            intent.putExtra("course_start_time", currentCourse.startTime)
            intent.putExtra("course_end_time", currentCourse.endTime)
            intent.putExtra("course_detail", currentCourse.detail)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = filteredCourses.size

    fun filter(query: String) {
        filteredCourses = if (query.isEmpty()) {
            courses.toMutableList()
        } else {
            courses.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.id.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseId: TextView = itemView.findViewById(R.id.text_course_id)
        val courseName: TextView = itemView.findViewById(R.id.text_course_name)
        val courseType: TextView = itemView.findViewById(R.id.text_course_type)
        val courseDay: TextView = itemView.findViewById(R.id.text_course_day)
        val courseTime: TextView = itemView.findViewById(R.id.text_course_time)
    }
}
