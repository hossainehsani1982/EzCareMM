package com.hossain_ehs.medication_on_alarm_ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.medication_domain.use_case.MedicationUseCases
import com.hossain_ehs.medication_presentation.databinding.ItemAlarmBinding

class AlarmMedicationViewPagerAdapter(private val listener : OnAlertMedicationClicked,
private val medicationUseCases: MedicationUseCases) :
    ListAdapter<Medication,AlarmMedicationViewPagerAdapter.ViewHolder>(DiffCallBack()) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMedication = getItem(position)
        holder.onBind(currentMedication)
    }

    inner class ViewHolder(private val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                imageMedication.setOnClickListener{
                    val position = adapterPosition
                    val medication = getItem(position)
                    listener.onMedicationImageClicked(medication)
                }
            }
        }

        fun onBind(medication: Medication){
            binding.apply {
                val medicationNameText = medication.medicineName +
                        " (${medication.medicationNotes})"
                editTextMedicineName.setText(medicationNameText)
                editTextMedicineQuantity.setText(medication.medicationDosage)

                Glide.with(imageMedication.context)
                    .load(Uri.parse(medication.medicationImageUri))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(imageMedication)

                textSchedule.text = medicationUseCases.dateToStringForAlarmUseCase(
                    medication.nextSchedule
                )
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

    interface OnAlertMedicationClicked {
            fun onMedicationImageClicked(medication: Medication)
    }



}