package dev.idee.androidtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TimeCardListAdapter(
    val timeTableSelectionListener: TimeTableSelectionListener,
    diffCallback: DiffUtil.ItemCallback<TimeCardModel>) : ListAdapter<TimeCardModel, TimeCardListAdapter.TimeTableViewHolder>(diffCallback) {

    private var timeCardList: List<TimeCardModel> = ArrayList()

    fun setItems(timeCardList: List<TimeCardModel>) {
        this.timeCardList = timeCardList
        submitList(timeCardList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timetable_row, parent, false)
        return TimeTableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeTableViewHolder, position: Int) {
        holder.bind(timeCardList[position])
    }

    inner class TimeTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewProject: TextView? = itemView.findViewById(R.id.textViewProject)
        private val startTimeTV : TextView? = itemView.findViewById(R.id.startTimeTV)
        private val endTimeTV : TextView? = itemView.findViewById(R.id.endTimeTV)

        fun bind(item: TimeCardModel?) = with(itemView) {

            this.setOnClickListener {
                if (item != null) {
                    timeTableSelectionListener.onTimeTableClicked(item)
                }
            }
            textViewProject?.text = item?.projectName
            startTimeTV?.text = item?.startTime
            endTimeTV?.text = item?.endTime
        }

    }

    interface TimeTableSelectionListener {
        fun onTimeTableClicked(item: TimeCardModel)
    }

}


