package cg.vsu.survey.view.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R
import cg.vsu.survey.model.Survey
import cg.vsu.survey.viewmodel.SurveyListViewModel

class SurveyAdapter(
    private val viewModel: SurveyListViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {

    private var surveys: List<Survey> = emptyList()

    init {
        viewModel.surveys.observe(lifecycleOwner) { newSurveys ->
            surveys = newSurveys.toList()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.survey_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val survey = surveys.getOrNull(position)
        holder.bind(survey)
    }

    override fun getItemCount(): Int {
        return surveys.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        private val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)

        fun bind(survey: Survey?) {
            titleTextView.text = survey?.title ?: "Unknown survey"
            descriptionTextView.text = survey?.description ?: "There is no description"
            statusTextView.text = if (survey?.active == true) "active" else "not active"
            authorTextView.text = survey?.admins?.joinToString(", ") ?: "Unknown"
        }
    }
}