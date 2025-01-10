package cg.vsu.survey.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R

class QuestionsAdapter(
    private val questions: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private val optionsForQuestions = mutableListOf<MutableList<String>>()

    inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionEditText: EditText = view.findViewById(R.id.questionTextEditText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteQuestionButton)
        val optionsRecyclerView: RecyclerView = view.findViewById(R.id.RV_options)
        val addOptionButton: ImageButton = view.findViewById(R.id.addOptionButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_card, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.questionEditText.setText(questions[holder.adapterPosition])
        holder.questionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val currentPosition = holder.adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    questions[currentPosition] = s.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.deleteButton.setOnClickListener {
            val currentPosition = holder.adapterPosition
            Log.d("позиция", "вопроса: $currentPosition")
            if (currentPosition != RecyclerView.NO_POSITION) {
                onDeleteClick(currentPosition)
                optionsForQuestions.removeAt(currentPosition)
            }
        }

        // Убедимся, что у текущего вопроса есть список опций
        while (optionsForQuestions.size <= holder.adapterPosition) {
            optionsForQuestions.add(mutableListOf())
        }

        val currentOptions = optionsForQuestions[holder.adapterPosition]
        val optionsAdapter = OptionsAdapter(currentOptions) { optionPosition ->
            currentOptions.removeAt(optionPosition)
            notifyItemChanged(holder.adapterPosition)
        }
        holder.optionsRecyclerView.adapter = optionsAdapter
        holder.optionsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)

        holder.addOptionButton.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                currentOptions.add("")
                optionsAdapter.notifyItemInserted(currentOptions.size - 1)
            }
        }
    }


    override fun getItemCount(): Int = questions.size

    fun addQuestion() {
        questions.add("")
        optionsForQuestions.add(mutableListOf())
        notifyItemInserted(questions.size - 1)
    }

    fun removeQuestion(position: Int) {
        Log.d("позиция", "Номер вопроса: $position")
        //optionsForQuestions.removeAt(position)
        questions.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getQuestions(): MutableList<String> = questions

    fun getOptionsForQuestion(questionPosition: Int): List<String> {
        Log.d("Перешли в опшен", "позиция вопроса: $questionPosition")
        return optionsForQuestions.getOrNull(questionPosition) ?: emptyList()
    }
    fun getAllOptions(): List<List<String>> = optionsForQuestions
}


