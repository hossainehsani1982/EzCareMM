package com.hossain_ehs.medication_on_alarm_ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayoutMediator
import com.hossain_ehs.core.util.MedicationAlarmUiEvents
import com.hossain_ehs.medication_domain.model.medication.Medication
import com.hossain_ehs.core_ui.R
import com.hossain_ehs.medication_presentation.databinding.FragmentMedicationAlarmBinding
import com.ncorti.slidetoact.SlideToActView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MedicationAlarmFragment :
    Fragment(com.hossain_ehs.medication_presentation.R.layout.fragment_medication_alarm),
    AlarmMedicationViewPagerAdapter.OnAlertMedicationClicked {
    private lateinit var binding: FragmentMedicationAlarmBinding
    private val viewModel: MedicationAlarmViewModel by viewModels()
    private var player: MediaPlayer = MediaPlayer()
    private lateinit var alarmMedicationViewPagerAdapter: AlarmMedicationViewPagerAdapter
    private var sentToDelete = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMedicationAlarmBinding.bind(view)
        alarmMedicationViewPagerAdapter = AlarmMedicationViewPagerAdapter(
            this,
            viewModel.medicationUseCases
        )
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.medicationAlarmEvents.asLiveData().observe(viewLifecycleOwner) {
            it?.let { evens ->
                when (evens) {
                    is MedicationAlarmUiEvents.OnDataReady -> {
                        binding.apply {
                            alarmMedicationViewPagerAdapter
                                .submitList(viewModel.state.medicationsState)

                            viewPager.currentItem = 0
                            viewPager.apply {
                                adapter = alarmMedicationViewPagerAdapter
                                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                                beginFakeDrag()
                                fakeDragBy(-10f)
                                endFakeDrag()
                            }
                            TabLayoutMediator(alarmTabLayout, viewPager) { _, _ ->
                            }.attach()

                            if (viewModel.state.medicationListSizeState == 1)
                                sliderDone.visibility = View.VISIBLE

                            viewPager.registerOnPageChangeCallback(object :
                                ViewPager2.OnPageChangeCallback() {

                                override fun onPageSelected(position: Int) {
                                    if ((position + 1) == viewModel.state.medicationListSizeState) {
                                        sliderDone.visibility = View.VISIBLE
                                    } else {
                                        sliderDone.visibility = View.GONE
                                    }
                                    viewModel.onEvent(
                                        MedicationAlarmEvents.GetCorrespondingUserData(
                                            viewModel.state.medicationsState[position]
                                        )
                                    )
                                    super.onPageSelected(position)
                                }
                            })
                            sliderDone.text = requireContext()
                                .resources.getString(com.hossain_ehs.core.R.string.finish)
                            sliderDone.onSlideCompleteListener =
                                object : SlideToActView.OnSlideCompleteListener {
                                    override fun onSlideComplete(view: SlideToActView) {
                                        if (sentToDelete)
                                            viewModel.onEvent(MedicationAlarmEvents.OnDismissClicked)
                                        sentToDelete = false
                                    }
                                }

                            playAlarm()
                            buttonMute.setOnClickListener {
                                try {
                                    if (player.isPlaying) {
                                        player.stop()
                                        player.release()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                        }
                    }
                    is MedicationAlarmUiEvents.OnUserDataReady -> {
                        binding.apply {
                            if (evens.uri.isNotEmpty()) {
                                Glide.with(requireContext())
                                    .load(Uri.parse(evens.uri))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .transform(CenterInside(), RoundedCorners(24))
                                    .into(imageUser)
                            }
                            textUserName.setText(evens.useName)
                        }

                    }
                    MedicationAlarmUiEvents.OnSlideComplete -> {
                        stopAll()
                    }
                }
            }
        }
    }

    private fun playAlarm() {
        player = MediaPlayer.create(requireContext(), R.raw.castle)
        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        player.start()
        player.isLooping = true
    }

    private fun stopAll() {
        player.isLooping = false
        player.stop()
        player.release()
        Thread.sleep(100)
        requireActivity().moveTaskToBack(true)
        requireActivity().finish()
    }

    override fun onMedicationImageClicked(medication: Medication) {

    }
}