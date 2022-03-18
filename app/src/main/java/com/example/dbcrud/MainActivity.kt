package com.example.dbcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import com.example.dbcrud.adapters.GroupAdapter
import com.example.dbcrud.databinding.ActivityMainBinding
import com.example.dbcrud.db.MyDbHelper
import com.example.dbcrud.models.Group

class MainActivity : AppCompatActivity() {
    lateinit var myDbHelper: MyDbHelper
    lateinit var groupAdapter: Adapter
    lateinit var list:ArrayList<Group>
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        myDbHelper = MyDbHelper(this)

update()

        binding.apply {
            groupSave.setOnClickListener {
                val name = edtGroup.text.toString().trim()
                val group = Group(name)
                myDbHelper.addGroup(group)
                Toast.makeText(this@MainActivity, "save $name", Toast.LENGTH_SHORT).show()
                update()
            }
        }
    }
    fun update(){

        list = myDbHelper.showGroup() as ArrayList<Group>
        groupAdapter = GroupAdapter(list, object : GroupAdapter.RvClick{
            override fun itemClick(group: Group) {
                val intent = Intent(this@MainActivity,StudentActivity::class.java)
                intent.putExtra("keyGroup", group)
                startActivity(intent)
            }
            override fun longClick(group: Group) {
                myDbHelper.deleteGroup(group)
                update()
            }
        })
        binding.rvStudent.adapter = groupAdapter as GroupAdapter
    }

}