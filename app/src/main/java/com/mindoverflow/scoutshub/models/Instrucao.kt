package com.mindoverflow.scoutshub.models

import com.google.android.gms.common.images.ImageManager
import org.json.JSONObject

class Instrucao {

    var idInstrucao : Int?      = null
    var titulo      : String?   = null
    var descricao   : String?   = null
    var imagem      : Int?      = null

    constructor()

    constructor(

        id_intrucao : Int?,
        titulo      : String?,
        descricao   : String?,
        imagem      : Int?

    ) {

        this.idInstrucao    = id_intrucao
        this.titulo         = titulo
        this.descricao      = descricao
        this.imagem         = imagem
    }

    fun toJson() : JSONObject {

        val jsonObject = JSONObject()

        jsonObject.put("id_instrucao"   , idInstrucao)
        jsonObject.put("titulo"         , titulo)
        jsonObject.put("descricao"      , descricao)
        jsonObject.put("imagem"         , imagem)

        return jsonObject
    }

    companion object {

        fun fromJson(jsonObject : JSONObject) : Instrucao {

            val instrucoes = Instrucao()

            instrucoes.idInstrucao  = if(!jsonObject.isNull("id_instrucao"))    jsonObject.getInt("id_instrucao")       else null
            instrucoes.titulo       = if(!jsonObject.isNull("titulo"))          jsonObject.getString("titulo")          else null
            instrucoes.descricao    = if(!jsonObject.isNull("descricao"))       jsonObject.getString("descricao")       else null
            instrucoes.imagem       = if(!jsonObject.isNull("imagem"))          jsonObject.getInt("imagem")             else null

            return instrucoes
        }
    }
}