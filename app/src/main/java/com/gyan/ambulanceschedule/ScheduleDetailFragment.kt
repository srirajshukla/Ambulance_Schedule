package com.gyan.ambulanceschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyan.ambulanceschedule.data.Schedule
import com.gyan.ambulanceschedule.data.getFormattedSchedule
import com.gyan.ambulanceschedule.databinding.FragmentScheduleDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleDetailFragment : Fragment() {

    private val navigationArgs : ScheduleDetailFragmentArgs by navArgs()

    private var _binding : FragmentScheduleDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var schedule: Schedule

    private val viewModel : ScheduleViewModel by activityViewModels {
        ScheduleViewModelFactory(
            (activity?.application as ScheduleApplication).database.scheduleDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.scheduleId
        viewModel.retrieveSchedule(id).observe(this.viewLifecycleOwner){ selectedSchedule ->
            schedule = selectedSchedule
            bind(schedule)
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        findNavController().navigateUp()
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(schedule: Schedule){
        binding.apply {
            name.text = schedule.name
            phone.text = schedule.phone
            scheduleTime.text = schedule.getFormattedSchedule()
        }
    }

}