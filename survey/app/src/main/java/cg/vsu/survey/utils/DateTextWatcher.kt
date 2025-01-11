package cg.vsu.survey.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

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
