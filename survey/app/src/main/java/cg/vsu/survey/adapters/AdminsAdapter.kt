package cg.vsu.survey.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R

class AdminsAdapter(
    private val admins: MutableList<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<AdminsAdapter.AdminViewHolder>() {

    inner class AdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val adminEditText: EditText = view.findViewById(R.id.elementTextEditText)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteElementButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_create_element, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.adminEditText.setText(admins[position])

        // Обновление списка при изменении текста
        holder.adminEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                admins[holder.adapterPosition] = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Удаление администратора
        holder.deleteButton.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onDeleteClick(currentPosition)
            }
        }
    }


    override fun getItemCount(): Int = admins.size

    fun addAdmin() {
        admins.add("")
        notifyItemInserted(admins.size - 1)
    }

    fun removeAdmin(position: Int) {
        Log.d("админ", "Номер админа: $position")
        admins.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getAdmins(): List<String> = admins
}

