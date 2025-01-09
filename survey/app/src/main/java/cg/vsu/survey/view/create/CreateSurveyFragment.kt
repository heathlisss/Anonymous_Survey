package cg.vsu.survey.view.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R
import cg.vsu.survey.model.Question
import cg.vsu.survey.model.Survey
import cg.vsu.survey.view.feed.FeedSurveyFragment
import cg.vsu.survey.viewmodel.QuestionViewModel
import cg.vsu.survey.viewmodel.SurveyViewModel

class CreateSurveyFragment : Fragment() {

    private lateinit var titleTextEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var saveButton: ImageButton
    private lateinit var closeButton: ImageButton
    private lateinit var addAdminButton: ImageButton
    private lateinit var adminsRecyclerView: RecyclerView
    private lateinit var adminsAdapter: AdminsAdapter
    private val adminsList = mutableListOf<String>()
    private lateinit var viewModel: SurveyViewModel
    private lateinit var viewModelQuestion: QuestionViewModel

    private lateinit var questionsRecyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val questionsList = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_survey, container, false)

        titleTextEditText = view.findViewById(R.id.surveyTitleEditText)
        descriptionEditText = view.findViewById(R.id.surveyDescriptionEditText)
        startDateEditText = view.findViewById(R.id.startDateEditText)
        endDateEditText = view.findViewById(R.id.endDateEditText)
        saveButton = view.findViewById(R.id.saveButton)
        closeButton = view.findViewById(R.id.closeButton)
        addAdminButton = view.findViewById(R.id.addAdminButton)
        adminsRecyclerView = view.findViewById(R.id.RV_admins)


        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        viewModelQuestion = ViewModelProvider(this).get(QuestionViewModel::class.java)

        startDateEditText.addTextChangedListener(DateTextWatcher(startDateEditText))
        endDateEditText.addTextChangedListener(DateTextWatcher(endDateEditText))

        adminsAdapter = AdminsAdapter(adminsList) { position ->
            adminsAdapter.removeAdmin(position)
        }
        adminsRecyclerView.adapter = adminsAdapter
        adminsRecyclerView.layoutManager = LinearLayoutManager(context)
        addAdminButton.setOnClickListener {
            adminsAdapter.addAdmin()
        }

        saveButton.setOnClickListener { saveSurvey() }
        closeButton.setOnClickListener { closeFragment() }


        questionsRecyclerView = view.findViewById(R.id.RV_questions)
        questionsAdapter = QuestionsAdapter(questionsList) { position ->
            questionsAdapter.removeQuestion(position)
        }
        questionsRecyclerView.adapter = questionsAdapter
        questionsRecyclerView.layoutManager = LinearLayoutManager(context)

        val addQuestionButton: ImageButton = view.findViewById(R.id.addQuestionButton)
        addQuestionButton.setOnClickListener {
            questionsAdapter.addQuestion()
        }

        return view
    }

    private fun saveSurvey() {
        val surveyTitle = titleTextEditText.text.toString()
        val surveyDescription = descriptionEditText.text.toString()
        val startDateString = startDateEditText.text.toString()
        val endDateString = endDateEditText.text.toString()

        val survey = Survey(
            title = surveyTitle,
            description = surveyDescription,
            start_date = startDateString,
            end_date = endDateString,
            admins = adminsAdapter.getAdmins().filter { it.isNotEmpty() },
        )

        viewModel.saveSurvey(survey) { createdSurvey ->
            if (createdSurvey != null) {
                createdSurvey.id?.let { saveQuestions(it) }
                closeFragment()
            } else {
            }
        }

        closeFragment()
    }

    private fun saveQuestions(surveyId: Int) {
        val questionsToSave = questionsAdapter.getQuestions().filter { it.isNotEmpty() }
        for (questionText in questionsToSave) {
            val question = Question(
                survey = surveyId,
                text = questionText
                // options можно оставить пустым или добавить логику для их сохранения
            )
            viewModelQuestion.saveQuestion(question)
        }
    }



    private fun closeFragment() {
        // Загрузка фрагмента Home (FeedSurveyFragment)
        loadFragment(FeedSurveyFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}

class DateTextWatcher(private val editText: EditText) : TextWatcher {

    private var isUpdating = false
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable?) {
        if (isUpdating) return

        isUpdating = true
        try {
            if (s != null) {
                val cleanString = s.toString().replace("-", "").replace("/", "")
                val formattedString = StringBuilder()

                for (i in cleanString.indices) {
                    formattedString.append(cleanString[i])
                    if ((i == 3 || i == 5) && i < cleanString.length - 1) {
                        formattedString.append("-")
                    }
                }

                if (formattedString.toString() != s.toString()) {
                    editText.setText(formattedString.toString())
                    editText.setSelection(formattedString.length)
                }
            }
        } finally {
            isUpdating = false
        }
    }
}

