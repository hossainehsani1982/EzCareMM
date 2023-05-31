package com.hossain_ehs.medication_presentation.medication_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import  androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import com.hossain_ehs.medication_presentation.databinding.ItemExistingMedicationsBinding


class MedicationAdapter(
    private val listener: OnMedicationItemClickedListener,
    val language: String,
    val months: Array<String>,
    val medicationUseCases: MedicationUseCases
) : ListAdapter<Medication, MedicationAdapter.MedicationViewHolder>(DiffCallBack()) {

    private val viewBinderHelper: ViewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding = ItemExistingMedicationsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val currentMedication = getItem(position)
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipeRevealLayout, currentMedication.medicineName)
        viewBinderHelper.closeLayout(currentMedication.medicineName)
        holder.bind(currentMedication)
    }

    inner class MedicationViewHolder(private val binding: ItemExistingMedicationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var swipeRevealLayout: SwipeRevealLayout

        init {
            binding.apply {
                swipeRevealLayout = medicationSwipeLayout
                txtDeleteMedication.setOnClickListener {
                    val position = adapterPosition
                    val medication = getItem(position)
                    listener.onDeleteMedicationClicked(medication)
                }
                txtEditMedication.setOnClickListener {
                    val position = adapterPosition
                    val medication = getItem(position)
                    listener.onEditMedicationClicked(medication)
                }
                /**   lblShowProgress.setOnClickListener {
                val position = adapterPosition
                val medication = getItem(position)
                listener.onShowProgressClicked(medication)
                }
                imgExistingMedication.setOnClickListener {
                val position = adapterPosition
                val medication = getItem(position)
                listener.onMedicationImageClicked(medication)
                }
                 **/
            }
        }

        fun bind(medication: Medication) {
            binding.apply {
                txtExistingMedicationName.setText(medication.medicineName)
                textShortDescription.setText(medication.medicationNotes)
                editTextDoctorName.setText(medication.doctorName)
                txtNextUsage.setText(
                    medicationUseCases.dateToStringUseCase(
                        language = language,
                        timeInMillis = medication.nextSchedule,
                        months = months,
                    )
                )

                txtEditMedication.setOnClickListener {
                    listener.onEditMedicationClicked(medication)
                }
                txtDeleteMedication.setOnClickListener {
                    listener.onDeleteMedicationClicked(medication)
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Medication>() {
        override fun areItemsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem.medicationId == newItem.medicationId
        }

        override fun areContentsTheSame(oldItem: Medication, newItem: Medication): Boolean {
            return oldItem == newItem
        }
    }

    interface OnMedicationItemClickedListener {
        fun onEditMedicationClicked(medication: Medication)
        fun onDeleteMedicationClicked(medication: Medication)
    }
}