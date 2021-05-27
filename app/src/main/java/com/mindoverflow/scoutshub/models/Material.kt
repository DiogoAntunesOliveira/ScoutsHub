package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Material {

    var idMaterial     : Int?      = null
    var tipo            : String?   = null
    var quantidade      : Int?      = null

    constructor(){

    }

    constructor(
        idMaterial  : Int?,
        tipo        : String?,
        quantidade  : Int?
    ){
        this.idMaterial = idMaterial
        this.tipo       = tipo
        this.quantidade = quantidade
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_material"    ,idMaterial)
        jsonObject.put("tipo"           ,tipo)
        jsonObject.put("quantidade"     ,quantidade)

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : Material {
            val  material = Material()
            material.idMaterial = if(!jsonObject.isNull("id_material")) jsonObject.getInt("id_material") else null
            material.tipo       = if(!jsonObject.isNull("tipo"))        jsonObject.getString("tipo")     else null
            material.quantidade = if(!jsonObject.isNull("quantidade"))  jsonObject.getInt("quantidade")  else null

            return material
        }
    }

}