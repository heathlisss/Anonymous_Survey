package cg.vsu.survey.view.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cg.vsu.survey.R
import cg.vsu.survey.model.Survey
import cg.vsu.survey.view.feed.FeedSurveyFragment
import cg.vsu.survey.viewmodel.SurveyViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateSurveyFragment : Fragment() {


    private lateinit var titleTextEditText: EditText
    private lateinit var descriptionEditText: EditText // Поле для описания
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var saveButton: ImageButton
    private lateinit var closeButton: ImageButton // Кнопка закрытия
    private lateinit var viewModel: SurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_survey, container, false)

        // Инициализация EditText для ввода данных
        titleTextEditText = view.findViewById(R.id.surveyTitleEditText)
        descriptionEditText = view.findViewById(R.id.surveyDescriptionEditText) // Инициализация поля описания
        startDateEditText = view.findViewById(R.id.startDateEditText)
        endDateEditText = view.findViewById(R.id.endDateEditText)
        saveButton = view.findViewById(R.id.saveButton)
        closeButton = view.findViewById(R.id.closeButton) // Инициализация кнопки закрытия

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)

        // Применение TextWatcher для автоматического форматирования дат
        startDateEditText.addTextChangedListener(DateTextWatcher(startDateEditText))
        endDateEditText.addTextChangedListener(DateTextWatcher(endDateEditText))

        // Установка слушателей на кнопки
        saveButton.setOnClickListener { saveSurvey() }
        closeButton.setOnClickListener { closeFragment() } // Закрытие фрагмента по нажатию кнопки

        return view
    }

    // Метод для сохранения опроса
    private fun saveSurvey() {
        val surveyTitle = titleTextEditText.text.toString()
        val surveyDescription = descriptionEditText.text.toString()
        val startDateString = startDateEditText.text.toString()
        val endDateString = endDateEditText.text.toString()

        val startDate = startDateString
        val endDate =  endDateString

        val survey = Survey(
            title = surveyTitle,
            description = surveyDescription,
            start_date = startDate,
            end_date = endDate
        )

        viewModel.saveSurvey(survey)
        closeFragment()
    }


    private fun closeFragment() {
        // Загрузка фрагмента Home (FeedSurveyFragment)
        loadFragment(FeedSurveyFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Добавляем в стек для возможности возврата, если нужно
            .commit()
    }
}

class DateTextWatcher(private val editText: EditText) : TextWatcher {

    private var isUpdating = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

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

