package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Participante {
    var id_utilizador     : Int?    = null
    var id_atividade      : Int? = null
    var confirmacao       : Boolean? = null


constructor(
     id_utilizador: Int?,
     id_atividade: Int?,
     confirmacao: Boolean?
){
     this.id_utilizador = id_utilizador
     this.id_atividade = id_atividade
     this.confirmacao = confirmacao
 }

    constructor(){

    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_utilizador"     ,id_utilizador)
        jsonObject.put("id_atividade"      ,id_atividade)
        jsonObject.put("confirmacao"       ,confirmacao)

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : Atividade {
            val atividade = Atividade()

            atividade.idAtividade       = if(!jsonObject.isNull("id_utilizador"))    jsonObject.getInt("id_utilizador")       else null
            atividade.nome              = if(!jsonObject.isNull("id_atividade"))            jsonObject.getString("id_atividade")            else null
            atividade.tipo              = if(!jsonObject.isNull("confirmacao"))            jsonObject.getString("confirmacao")            else null

            return atividade
        }
    }



}