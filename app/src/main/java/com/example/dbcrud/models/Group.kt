package com.example.dbcrud.models

import java.io.Serializable
import javax.xml.transform.sax.TemplatesHandler

class Group : Serializable {
    var id : Int? = null
    var name :String? =null

    constructor(id: Int?, name: String){
        this.id = id
        this.name = name
    }

    constructor(name:String?){
        this.name = name
    }

}