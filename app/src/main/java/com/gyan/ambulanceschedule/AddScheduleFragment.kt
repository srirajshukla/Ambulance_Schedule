package com.gyan.ambulanceschedule

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gyan.ambulanceschedule.data.Schedule
import com.gyan.ambulanceschedule.databinding.FragmentAddScheduleBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AddScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddScheduleFragment : Fragment() {
    private val navigationArgs : ScheduleDetailFragmentArgs by navArgs()

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel : ScheduleViewModel by activityViewModels {
        ScheduleViewModelFactory(
            (activity?.application as ScheduleApplication).database
                .scheduleDao()
        )
    }

    lateinit var schedule : Schedule

    // Binding object instance corresponding to the fragment_add_schedule.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding : FragmentAddScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.scheduleId
        if (id>0){
            viewModel.retrieveSchedule(id).observe(this.viewLifecycleOwner) {selectedSchedule ->
                schedule = selectedSchedule
                bind(schedule)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewSchedule()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Hiding the keyboard
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }



    private fun isEntryValid() : Boolean {
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.phone.text.toString(),
            binding.startTime.text.toString(),
            binding.endTime.text.toString(),
            binding.day.text.toString(),
        )
    }

    private fun addNewSchedule() {
        if (isEntryValid()){
            viewModel.addNewSchedule(
                binding.name.text.toString(),
                binding.phone.text.toString(),
                binding.startTime.text.toString(),
                binding.endTime.text.toString(),
                binding.day.text.toString(),
            )

            val action = AddScheduleFragmentDirections.actionAddScheduleFragmentToScheduleListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(schedule: Schedule){
        binding.apply {
            name.setText(schedule.name, TextView.BufferType.SPANNABLE)
            phone.setText(schedule.phone, TextView.BufferType.SPANNABLE)
            startTime.setText(schedule.startTime, TextView.BufferType.SPANNABLE)
            endTime.setText(schedule.endTime, TextView.BufferType.SPANNABLE)
            day.setText(schedule.day, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateSchedule() }
        }
    }

    private fun updateSchedule() {
        if (isEntryValid()){
            viewModel.updateSchedule(
                this.navigationArgs.scheduleId,
                this.binding.name.text.toString(),
                this.binding.phone.text.toString(),
                this.binding.startTime.text.toString(),
                this.binding.endTime.text.toString(),
                this.binding.day.text.toString(),
            )

            val action = AddScheduleFragmentDirections.actionAddScheduleFragmentToScheduleListFragment()
            findNavController().navigate(action)
        }
    }
}