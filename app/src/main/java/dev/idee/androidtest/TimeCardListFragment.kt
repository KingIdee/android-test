package dev.idee.androidtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.idee.androidtest.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.fragment_timecard_list.*


class TimeCardListFragment : Fragment(), TimeCardListAdapter.TimeTableSelectionListener {

    private val timeCardAdapter = TimeCardListAdapter(this,DiffUtilCallback())
    lateinit var viewModel:TimeCardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timecard_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListener()
        setupViewModel()
    }

    override fun onTimeTableClicked(item: TimeCardModel) {

        findNavController().navigate(TimeCardListFragmentDirections.actionTimeCardListFragmentToHomeFragment(
            timetableItem = item, isEdit = true
        ))
    }

    private fun setupRecyclerView() {

        with(recyclerViewTimeCard){
            adapter = timeCardAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun setupClickListener() {

        addNewTimeCardFab.setOnClickListener {
            findNavController().navigate(TimeCardListFragmentDirections.actionTimeCardListFragmentToHomeFragment(
                timetableItem = TimeCardModel("",0.0,"",0.0,"","",""),
                isEdit = false
            ))
        }

    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!)[TimeCardListViewModel::class.java]
        viewModel.timeCardListLiveData.observe(this, Observer {
            timeCardAdapter.setItems(it)
            noDataTextView.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        })
    }


}
