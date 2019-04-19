package com.jejefcgb.homelights

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.furniture.view.*
import java.util.*

class MyAdapter internal constructor(private val mDataset: ArrayList<Server>, private val mCallback: Callback, private val mActivity : Activity) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var selectedPos: MutableList<Int> = ArrayList()

    inner class MyViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {


        internal var mTitle: TextView
        internal var mIcon: ImageView
        internal var mBackground : View


        init {
            v.setOnClickListener(this)
            mTitle = v.card_title
            mIcon = v.card_icon
            mBackground = v.card_background
        }


//        override fun onClick(v: View) {
//
//            if (adapterPosition == RecyclerView.NO_POSITION) return
//
//            // Updating old as well as new positions
//            //notifyItemChanged(selectedPos);
//            if (selectedPos.contains(adapterPosition)) {
//                selectedPos.remove(Integer.valueOf(adapterPosition))
//            } else {
//                selectedPos.add(adapterPosition)
//            }
//            mCallback.update(selectedPos)
//            notifyDataSetChanged()
//        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val p1 = androidx.core.util.Pair(mIcon as View, "transitionImage")
            //val p2 = androidx.core.util.Pair(mTitle as View, "transitionTitle")
            val p3 = androidx.core.util.Pair(mBackground , "transitionBackground")

            val transitions = arrayOf(p1,p3)
//            transitions += p2
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

            val intent = Intent(mActivity, TransitionActivity::class.java)
            intent.putExtra("EXTRA_ICON", mDataset[adapterPosition].icon)
            intent.putExtra("EXTRA_TITLE", mDataset[adapterPosition].name)
            mActivity.startActivity(intent, options.toBundle())

        }
    }

    fun getSelectedPos(): List<Int> {
        return selectedPos
    }

    fun resetSelectedPos() {
        this.selectedPos = ArrayList()
        mCallback.update(selectedPos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.furniture, parent, false) as ConstraintLayout

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.isSelected = selectedPos.contains(position)
        holder.itemView.setBackgroundColor(
                if (selectedPos.contains(position))
                    ContextCompat.getColor(holder.itemView.context, R.color.tile_selected)
                else
                    ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))

        val s = mDataset[position]
        holder.mTitle.text = s.name
        holder.mIcon.setImageResource(s.icon)

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

}