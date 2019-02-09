package ne.application.com.firebasetest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by Harikesh on 07/02/2019.
 */
class MyRecyclerViewAdapter// data is passed into the constructor
internal constructor(clickListner :ItemClickListener, context: Context, private val mData: List<MeditationModel>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = clickListner

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_card_meditation, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = mData[position]
        holder.textMeditaion.text = text.text_medication
        Picasso.get().load(mData.get(position).imageLink).into(holder.imageMeditaion);
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }


    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var textMeditaion: TextView
        internal var imageMeditaion: ImageView
        init {
            textMeditaion = itemView.findViewById(R.id.text_meditation)
            imageMeditaion = itemView.findViewById(R.id.imageMeditation)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, mData[adapterPosition])
        }
    }

    // convenience method for getting data at click position
    internal fun getItem(id: Int): MeditationModel {
        return mData[id]
    }

    // allows clicks events to be caught
    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: MeditationModel)
    }
}