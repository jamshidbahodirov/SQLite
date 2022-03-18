package com.example.dbcrud.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dbcrud.databinding.ItemGroupBinding
import com.example.dbcrud.models.Group

class GroupAdapter(var list: List<Group>, val rvClick: RvClick ): RecyclerView.Adapter<GroupAdapter.Vh>(), Adapter {

    inner class Vh(var itemRv: ItemGroupBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(group: Group) {
            itemRv.menuItem.visibility = View.GONE
            itemRv.groupId.text = group.id.toString()
            itemRv.groupName.text = group.name
            itemRv.root.setOnClickListener {
                rvClick.itemClick(group)
            }
            itemRv.root.setOnLongClickListener {
                rvClick.longClick(group)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun itemClick(group: Group)
        fun longClick(group: Group)

    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

}

