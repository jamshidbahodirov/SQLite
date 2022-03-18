package com.example.dbcrud.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dbcrud.databinding.ItemGroupBinding
import com.example.dbcrud.models.Student

class StudentAdapter(val list: List<Student>, val rvClick: RvClick): RecyclerView.Adapter<StudentAdapter.Vh>(){

    inner class Vh(var itemRv: ItemGroupBinding): RecyclerView.ViewHolder(itemRv.root){
        fun onBind(student: Student){
            itemRv.groupId.text = student.number
            itemRv.groupName.text = student.name



            itemRv.root.setOnClickListener {
                rvClick.itemClick(student)
            }

            itemRv.menuItem.setOnClickListener{
                rvClick.menuClick(itemRv.menuItem, student)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.Vh {

        return Vh (ItemGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: StudentAdapter.Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface  RvClick{
        fun itemClick(student: Student)
        fun menuClick(imageView: ImageView, student: Student)
    }


}