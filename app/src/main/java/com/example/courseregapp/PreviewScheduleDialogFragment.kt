import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.courseregapp.Course
import com.example.courseregapp.R

class PreviewScheduleDialogFragment : DialogFragment() {

    private lateinit var selectedCourses: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedCourses = requireArguments().getParcelableArrayList("selectedCourses") ?: emptyList()

        Log.d("PreviewScheduleDialog", "Selected courses: $selectedCourses")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_preview_schedule, container, false)
        val tableLayout = view.findViewById<TableLayout>(R.id.schedule_table)

        // Create headers
        val headerRow = TableRow(context)
        headerRow.addView(createTextView("Time/Day"))
        headerRow.addView(createTextView("Monday"))
        headerRow.addView(createTextView("Tuesday"))
        headerRow.addView(createTextView("Wednesday"))
        headerRow.addView(createTextView("Thursday"))
        headerRow.addView(createTextView("Friday"))
        tableLayout.addView(headerRow)

        // Create time slots
        for (hour in 9..18) {
            val row = TableRow(context)
            row.addView(createTextView("$hour:00"))

            for (day in 1..5) {
                val cell = createTextView("")
                val courseInSlot = selectedCourses.find { it.day == day && it.startTime <= hour && it.endTime >= hour }
                cell.text = courseInSlot?.id ?: ""
                row.addView(cell)
            }

            tableLayout.addView(row)
        }

        return view
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setPadding(8, 8, 8, 8)
        textView.setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
        textView.setTextColor(resources.getColor(android.R.color.black, null))
        return textView
    }

    companion object {
        fun newInstance(selectedCourses: List<Course>): PreviewScheduleDialogFragment {
            val fragment = PreviewScheduleDialogFragment()
            val args = Bundle()
            args.putParcelableArrayList("selectedCourses", ArrayList(selectedCourses))
            fragment.arguments = args
            return fragment
        }
    }
}
