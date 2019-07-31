package dev.idee.androidtest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dev.idee.androidtest.utils.DatePickerFragment
import dev.idee.androidtest.utils.DatePickerFragment.Companion.SELECTED_DATE
import dev.idee.androidtest.utils.TimePickerFragment
import dev.idee.androidtest.utils.TimePickerFragment.Companion.SELECTED_TIME
import dev.idee.androidtest.utils.validateEditText
import kotlinx.android.synthetic.main.fragment_create_time_card.*


class CreateTimeCardFragment : Fragment() {

    lateinit var viewModel: TimeCardListViewModel
    private var timeCardModel: TimeCardModel? = null
    private var isEdit: Boolean = false
    private var startTime: String? = ""
    private var endTime: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_time_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchArguments()
        setupViewModel()
        prefillData()
        setupListeners()
    }

    private fun fetchArguments() {
        val args = arguments?.let { CreateTimeCardFragmentArgs.fromBundle(it) }
        if (args != null) {
            timeCardModel = args.timetableItem
            isEdit = args.isEdit
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!)[TimeCardListViewModel::class.java]

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // check for the results
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                DATE_PICKER_REQUEST -> {

                    val selectedDate = data?.getStringExtra(SELECTED_DATE)
                    dateTextView.setText(selectedDate)

                }

                START_TIME_REQUEST -> {
                    startTime = data?.getStringExtra(SELECTED_TIME)
                    startTimeTextView.setText(startTime)

                }

                END_TIME_REQUEST -> {
                    endTime = data?.getStringExtra(SELECTED_TIME)
                    endTimeTextView.setText(endTime)
                }
            }

        }


    }

    private fun prefillData() {
        if (isEdit && (timeCardModel != null)) {
            projectNameInputField.setText(timeCardModel?.projectName)

            rateEditText.setText(timeCardModel?.billRate.toString())
            dateTextView.setText(timeCardModel?.date)
            startTime = timeCardModel?.startTime
            startTimeTextView.setText(startTime)
            endTime = timeCardModel?.endTime
            endTimeTextView.setText(endTime)
            saveButton.text = getString(R.string.update)
            deleteBtn.visibility = View.VISIBLE
        } else {
            deleteBtn.visibility = View.GONE
        }
    }

    private fun setupListeners() {

        dateTextView.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.setTargetFragment(this, DATE_PICKER_REQUEST)
            fragmentManager?.let { fragManager -> datePickerFragment.show(fragManager, DatePickerFragment::class.java.simpleName) }
        }

        startTimeTextView.setOnClickListener {

            val timePickerFragment = TimePickerFragment()
            timePickerFragment.setTargetFragment(this, START_TIME_REQUEST)
            fragmentManager?.let { fragManager -> timePickerFragment.show(fragManager, TimePickerFragment::class.java.simpleName) }
        }

        endTimeTextView.setOnClickListener {

            val timePickerFragment = TimePickerFragment()
            timePickerFragment.setTargetFragment(this, END_TIME_REQUEST)
            fragmentManager?.let { fragManager -> timePickerFragment.show(fragManager, TimePickerFragment::class.java.simpleName) }
        }

        saveButton.setOnClickListener {

            if (validateEditText(projectNameInputField) && validateEditText(rateEditText) && validateEditText(
                    dateTextView
                )
                && validateEditText(startTimeTextView) && validateEditText(endTimeTextView)
            ) {

                val newItemId = if (isEdit) {
                    timeCardModel?.id
                } else {
                    val newId = viewModel.timeCardListLiveData.value?.size?.plus(1) ?: 1
                    newId.toString()
                }

                val timeCardModel = TimeCardModel(
                    newItemId,
                    rateEditText.text.toString().toDouble(),
                    projectNameInputField.text.toString(),
                    getHours(),
                    dateTextView.text.toString(),
                    startTimeTextView.text.toString(),
                    endTimeTextView.text.toString()
                )

                if (isEdit) {
                    viewModel.updateTimeCardList(timeCardModel)
                } else {
                    viewModel.saveTimeCardList(timeCardModel)
                }

                findNavController().navigate(CreateTimeCardFragmentDirections.actionCreateTimeCardFragmentToTimeCardListFragment())
            }
        }

        deleteBtn.setOnClickListener {
            val id = timeCardModel?.id
            if (id != null) {
                viewModel.deleteItemFromList(id)
                findNavController().navigate(CreateTimeCardFragmentDirections.actionCreateTimeCardFragmentToTimeCardListFragment())
            }
        }

    }

    private fun getHours(): Double {

        if (startTime.isNullOrEmpty() || endTime.isNullOrEmpty()) {
            return 0.0
        }

        val sTotalInMin = toMinutes(startTime ?: "")
        val eTotalInMin = toMinutes(endTime ?: "")

        if (sTotalInMin >= eTotalInMin) {
            Log.e("TAG", "something is wrong")
        } else {
            val t = eTotalInMin.minus(sTotalInMin)
            val hour = t / 60
            val minute = t % 60

            return hour.toDouble()
        }

        return 0.0
    }


    private fun toMinutes(time: String): Long {
        if (time.isEmpty()) {
            return 0
        }
        val start = time.split(":")
        val sHour = start[0].toLong()
        val sMin = start[1].toLong()
        return (sHour.times(60)).plus(sMin)
    }

    companion object {
        const val DATE_PICKER_REQUEST = 1000
        const val START_TIME_REQUEST = 2000
        const val END_TIME_REQUEST = 3000
    }


}
