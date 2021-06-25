package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Participante {
    var id_utilizador     : Int?    = null
    var id_atividade      : Int?   = null
    var confirmacao       : Int?   = null

    constructor(){

    }

    constructor(
        id_utilizador: Int?,
        id_atividade: Int?,
        confirmacao: Int?
    ){
     this.id_utilizador = id_utilizador
     this.id_atividade = id_atividade
     this.confirmacao = confirmacao
    }

    constructor(confirmacao: Int?){
        this.confirmacao = confirmacao
    }



    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_utilizador"     ,id_utilizador)
        jsonObject.put("id_atividade"      ,id_atividade)
        jsonObject.put("confirmacao"       ,confirmacao)

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : Participante {
            val participante = Participante()

            participante.id_utilizador       = if(!jsonObject.isNull("id_utilizador")) jsonObject.getInt("id_utilizador")       else null
            participante.id_atividade        = if(!jsonObject.isNull("id_atividade")) jsonObject.getInt("id_atividade")            else null
            participante.confirmacao         = if(!jsonObject.isNull("confirmacao")) jsonObject.getInt("confirmacao")            else null

            return participante
        }
    }



}