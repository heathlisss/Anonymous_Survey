package cg.vsu.survey.view.create

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R
import cg.vsu.survey.model.Survey
import cg.vsu.survey.viewmodel.SurveyListViewModel

class QuestionsAdapter(
    private val questions: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionEditText: EditText = view.findViewById(R.id.questionTextEditText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteQuestionButton)
        val optionsRecyclerView: RecyclerView = view.findViewById(R.id.RV_options)

        // Здесь можно инициализировать адаптер для вариантов ответа
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_fragment, parent, false) // Предположим, что ваш XML для вопроса называется question_item
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.questionEditText.setText(questions[position])

        // Обновление списка при изменении текста
        holder.questionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                questions[position] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Удаление вопроса
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = questions.size

    fun addQuestion() {
        questions.add("")
        notifyItemInserted(questions.size - 1)
    }

    fun removeQuestion(position: Int) {
        questions.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getQuestions(): List<String> = questions
}
