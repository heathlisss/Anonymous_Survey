package cg.vsu.survey.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R

class QuestionsAdapter(
    private val questions: MutableList<Pair<String, MutableList<String>>>, // Пара вопрос - список вариантов
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionEditText: EditText = view.findViewById(R.id.questionTextEditText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteQuestionButton)
        val optionsRecyclerView: RecyclerView = view.findViewById(R.id.RV_options)
        val addOptionButton: ImageButton = view.findViewById(R.id.addOptionButton)

        val optionsList = mutableListOf<String>()
        lateinit var optionsAdapter: OptionsAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_fragment, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val (questionText, options) = questions[position]

        // Устанавливаем текст вопроса
        holder.questionEditText.setText(questionText)
        holder.questionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                questions[position] = Pair(s.toString(), options)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Удаление вопроса
        holder.deleteButton.setOnClickListener { onDeleteClick(position) }

        // Настройка адаптера для вариантов ответа
        holder.optionsList.clear()
        holder.optionsList.addAll(options)
        holder.optionsAdapter = OptionsAdapter(holder.optionsList) { optionPosition ->
            holder.optionsAdapter.removeOption(optionPosition)
        }
        holder.optionsRecyclerView.adapter = holder.optionsAdapter
        holder.optionsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)

        // Добавление нового варианта
        holder.addOptionButton.setOnClickListener {
            holder.optionsAdapter.addOption()
        }
    }

    override fun getItemCount(): Int = questions.size

    fun addQuestion() {
        questions.add(Pair("", mutableListOf()))
        notifyItemInserted(questions.size - 1)
    }

    fun removeQuestion(position: Int) {
        questions.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getQuestions(): List<Pair<String, MutableList<String>>> = questions
}
