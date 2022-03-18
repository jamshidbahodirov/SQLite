package com.example.dbcrud

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dbcrud.adapters.StudentAdapter
import com.example.dbcrud.databinding.ActivityMainBinding
import com.example.dbcrud.databinding.ActivityStudentBinding
import com.example.dbcrud.databinding.ItemDialogBinding
import com.example.dbcrud.db.MyDbHelper
import com.example.dbcrud.models.Group
import com.example.dbcrud.models.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.activity_student.btn_save_student
import kotlinx.android.synthetic.main.activity_student.edt_name_student
import kotlinx.android.synthetic.main.activity_student.edt_number
import kotlinx.android.synthetic.main.item_dialog.*

class StudentActivity : AppCompatActivity() {
    lateinit var binding: ActivityStudentBinding
    lateinit var group: Group
    lateinit var list: ArrayList<Student>
    lateinit var myDbHelper: MyDbHelper
    lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        group = intent.getSerializableExtra("keyGroup") as Group


        binding.apply {
            btn_save_student.setOnClickListener {
                val student = Student(
                    edt_name_student.text.toString(),
                    edt_number.text.toString(),
                    group

                )
                myDbHelper.addStudent(student)
                Toast.makeText(this@StudentActivity, "Save", Toast.LENGTH_SHORT).show()
                loadData()

            }

        }

        loadData()
    }
    private  fun loadData(){
        val mlist = ArrayList<Student>()
        list = ArrayList()
        myDbHelper =MyDbHelper(this)
        mlist.addAll(myDbHelper.showStudent())
        mlist.forEach {
            if (it.group?.id == group.id){
                list.add(it)
            }
        }
        studentAdapter =  StudentAdapter(list, object : StudentAdapter.RvClick{
            override fun itemClick(student: Student) {
                val dialog = AlertDialog.Builder(this@StudentActivity).create()
                dialog.setTitle(student.name)
                dialog.setMessage("id: ${student.id} \n" +
                "number :${student.number} \n" +
                "group; ${student.group?.name}")
                dialog.show()
            }

            override fun menuClick(imageView: ImageView, student: Student) {
                val popupMenu = PopupMenu(this@StudentActivity, imageView)
                popupMenu.inflate(R.menu.my_popup_menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.edit_menu-> {
                            val dialog = AlertDialog.Builder(this@StudentActivity).create()
                            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                            itemDialogBinding.edtNameStudent.setText(student.name)
                            itemDialogBinding.edtNumber.setText(student.number)
                            itemDialogBinding.btnSave.setOnClickListener {
                                student.name = itemDialogBinding.edtNameStudent.text.toString()
                                student.number = itemDialogBinding.edtNumber.text.toString()
                                myDbHelper.editStudent(student)
                                dialog.cancel()
                                loadData()
                            }
                            dialog.show()


                        }
                        R.id.delete_menu->{
                            myDbHelper.deleteStudent(student)
                            loadData()

                        }
                    }
                    true
                }
                popupMenu.show()
            }


        })
        binding.rvStudent.adapter =studentAdapter

    }
}