package com.example.courseregapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CourseRegRecyclerAdapter(private val courses: List<Course>) : RecyclerView.Adapter<CourseRegRecyclerAdapter.CourseViewHolder>() {

    private val selectedCourses = mutableSetOf<Course>()
    private var filteredCourses: MutableList<Course> = courses.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_reg_item_layout, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = filteredCourses[position]
        holder.bind(course)

        val isConflicting = selectedCourses.any { it.day == course.day && it.startTime <= course.endTime && it.endTime >= course.startTime }

        // Set the initial background color based on selection state
        holder.cardView.setCardBackgroundColor(
            if (selectedCourses.contains(course)) Color.parseColor("#006400") // Dark Green
            else holder.defaultBackgroundColor
        )

        holder.checkBox.setOnCheckedChangeListener(null) // Unset listener before setting new one
        holder.checkBox.isChecked = selectedCourses.contains(course)

        holder.itemView.isEnabled = !isConflicting
        holder.itemView.alpha = if (isConflicting) 0.5f else 1.0f

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedCourses.add(course)
                holder.cardView.setCardBackgroundColor(Color.parseColor("#006400")) // Dark Green
            } else {
                selectedCourses.remove(course)
                holder.cardView.setCardBackgroundColor(holder.defaultBackgroundColor)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = filteredCourses.size

    fun getSelectedCourses(): List<Course> = selectedCourses.toList()

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

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseId: TextView = itemView.findViewById(R.id.text_course_id)
        val courseName: TextView = itemView.findViewById(R.id.text_course_name)
        val courseType: TextView = itemView.findViewById(R.id.text_course_type)
        val courseDay: TextView = itemView.findViewById(R.id.text_course_day)
        val courseTime: TextView = itemView.findViewById(R.id.text_course_time)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_select_course)
        val cardView: CardView = itemView.findViewById(R.id.card_view) // Add this line

        val defaultBackgroundColor: Int = cardView.cardBackgroundColor.defaultColor

        // Save the default background color to revert when unchecked
        fun bind(course: Course) {
            courseId.text = course.id
            courseName.text = course.name
            courseType.text = course.type
            courseDay.text = course.toDay()
            courseTime.text = "${course.startTime} - ${course.endTime}"
        }
    }
}
