package cg.vsu.survey.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R

class OptionsAdapter(
    private val options: MutableList<String>,
    private val onDeleteOption: (Int) -> Unit
) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val optionEditText: EditText = view.findViewById(R.id.elementTextEditText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteElementButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_fragment, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionEditText.setText(options[position])

        // Обновление текста варианта
        holder.optionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                options[position] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Удаление варианта
        holder.deleteButton.setOnClickListener {
            onDeleteOption(position)
        }
    }

    override fun getItemCount(): Int = options.size

    fun addOption() {
        options.add("")
        notifyItemInserted(options.size - 1)
    }

    fun removeOption(position: Int) {
        options.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getOptions(): List<String> = options
}

