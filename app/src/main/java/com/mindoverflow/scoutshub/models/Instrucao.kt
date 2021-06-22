package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Instrucao {

    var idInstrucao : Int?      = null
    var titulo      : String?   = null
    var descricao   : String?   = null

    constructor()

    constructor(

        id_intrucao: Int?,
        titulo: String?,
        descricao: String?

    ) {

        this.idInstrucao = id_intrucao
        this.titulo = titulo
        this.descricao = descricao
    }

    fun toJson() : JSONObject {

        val jsonObject = JSONObject()

        jsonObject.put("id_instrucao", idInstrucao)
        jsonObject.put("titulo", titulo)
        jsonObject.put("descricao", descricao)

        return jsonObject
    }

    companion object {

        fun fromJson(jsStr: String?, arrayPosition: Int?) : Instrucao {

            val instrucoes = Instrucao()

            val jsonObject : JSONObject

            //If there is the need to get a Json array from a json object
            if(arrayPosition != null) {
                val jsonArray = JSONObject(jsStr!!).getJSONArray("instructions")
                jsonObject = JSONObject(jsonArray[arrayPosition].toString())
            } else {
                jsonObject = JSONObject(jsStr!!)
            }

            instrucoes.idInstrucao       = if(!jsonObject.isNull("id_instrucao"))    jsonObject.getInt("id_instrucao")       else null
            instrucoes.titulo            = if(!jsonObject.isNull("titulo"))          jsonObject.getString("titulo")          else null
            instrucoes.descricao         = if(!jsonObject.isNull("descricao"))       jsonObject.getString("descricao")       else null

            return instrucoes
        }

    }
}