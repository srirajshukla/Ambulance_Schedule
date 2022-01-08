package com.gyan.ambulanceschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyan.ambulanceschedule.databinding.ScheduleListFragmentBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleListFragment : Fragment() {

    private var _binding : ScheduleListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ScheduleListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floatingActionButton.setOnClickListener {
            val action = ScheduleListFragmentDirections.actionScheduleListFragmentToAddScheduleFragment(
                "Add new Schedule"
            )
            this.findNavController().navigate(action)
        }
    }
}