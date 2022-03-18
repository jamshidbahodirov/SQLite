package com.example.dbcrud.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dbcrud.models.Group
import com.example.dbcrud.models.Student

class MyDbHelper(context: Context):SQLiteOpenHelper(context, Db_NAME,null, DB_VERSION),MyDbService {
    companion object{
        const val Db_NAME = "dbc6"
        const val DB_VERSION = 1

        const val TABLE_GROUP = "groups"
        const val GROUP_ID = "id"
        const val GROUP_NAME = "name"

        const val TABLE_STUDENT = "students"
        const val STUDENT_ID = "id"
        const val STUDENT_NAME = "name"
        const val STUDENT_NUMBER = "number"
        const val STUDENT_GROUP_ID = "group_id"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val groupQuery = "create table $TABLE_GROUP ($GROUP_ID integer not null primary key autoincrement unique, $GROUP_NAME text not null)"
        val studentQuery = "create table $TABLE_STUDENT($STUDENT_ID integer not null primary key autoincrement unique, $STUDENT_NAME text not null, $STUDENT_NUMBER text not null, $STUDENT_GROUP_ID integer not null, foreign key ($STUDENT_GROUP_ID) REFERENCES $TABLE_GROUP ($GROUP_ID))"

        p0?.execSQL(groupQuery)
        p0?.execSQL(studentQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        database.insert(TABLE_GROUP,null ,contentValues)
        database.close()


    }

    override fun showGroup(): List<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_GROUP"

        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val group = Group(
                    cursor.getInt(0),
                    cursor.getString(1)
                )
                list.add(group)
            } while (cursor.moveToNext())
        }
return list
    }

    override fun getGroupById(id: Int): Group {
        val database = this.readableDatabase
        val cursor =database.query(
            TABLE_GROUP,
            arrayOf(
                GROUP_ID,
                GROUP_NAME
            ),
            "$GROUP_ID = ?",
            arrayOf(id.toString()),
            null,null,null
        )
        cursor.moveToFirst()
        val group = Group(
            cursor.getInt(0),
            cursor.getString(1)
        )
        return  group

    }


    override fun editGroup(group: Group): Int {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(group: Group) {
        deleteStudentByGroupId(group)

        val database = this.writableDatabase
        database.delete(TABLE_GROUP,"$GROUP_ID =?", arrayOf(group.id.toString()))
        database.close()
    }

    override fun addStudent(student: Student) {

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_NUMBER, student.number)
        contentValues.put(STUDENT_GROUP_ID, student.group?.id)
        database.insert(TABLE_STUDENT,null ,contentValues)
        database.close()


    }

    override fun showStudent(): List<Student> {
        val database = this.readableDatabase
        val list = ArrayList<Student>()
        val query = "select * from $TABLE_STUDENT"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do{
                val student = Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getGroupById(cursor.getInt(3))
                )
                list.add(student)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editStudent(student: Student): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_ID,student.id)
        contentValues.put(STUDENT_NAME,student.name)
        contentValues.put(STUDENT_NUMBER,student.number)

        return database.update(TABLE_STUDENT, contentValues,"${ STUDENT_ID} =?", arrayOf(student.id.toString()) )

    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(TABLE_STUDENT,"$STUDENT_ID =?", arrayOf(student.id.toString()))
        database.close()
    }

    override fun deleteStudentByGroupId(group: Group) {
        val query = "select * from $TABLE_STUDENT where $STUDENT_GROUP_ID = ${group.id}"
        val database = this.writableDatabase
        val cursor = database.rawQuery(query, null)


        if (cursor.moveToFirst()){
            do{
                val student = Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getGroupById(cursor.getInt(3))

                )
                deleteStudent(student)
            }while (cursor.moveToNext())
        }

    }
}