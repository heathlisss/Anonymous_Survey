package cg.vsu.survey.view.home

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
) : RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder>() {

    init {
        viewModel.surveys.observe(lifecycleOwner) { surveys ->
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.survey_card, parent, false)
        return SurveyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val survey = viewModel.surveys.value?.get(position)
        holder.bind(survey)
    }

    override fun getItemCount(): Int {
        return viewModel.surveys.value?.size ?: 0
    }

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextViewn)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        private val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)

        fun bind(survey: Survey?) {
            titleTextView.text = survey?.title
            descriptionTextView.text = survey?.description
            statusTextView.text =
                survey?.active.toString()
            authorTextView.text =
                survey?.admins.toString()
        }
    }
}