package com.example.dbcrud.db

import com.example.dbcrud.models.Group
import com.example.dbcrud.models.Student

interface MyDbService {

    fun addGroup(group: Group)
    fun showGroup():List<Group>
    fun getGroupById(id:Int):Group
    fun editGroup(group: Group):Int
    fun deleteGroup(group: Group)


    fun deleteStudentByGroupId(group: Group)
    fun addStudent(student: Student)
    fun showStudent():List<Student>
    fun editStudent(student: Student):Int
    fun deleteStudent(student: Student)

}