package com.example.aplicacaoprojeto.models

import org.json.JSONObject

class Instrucao {

    var idInstrucao : Int?      = null
    var titulo      : String?   = null
    var descricao   : String?   = null
    var imagem      : String?   = null

    constructor()

    constructor(

        id_intrucao: Int?,
        titulo: String?,
        descricao: String?,
        imagem: String?,

    ) {

        this.idInstrucao = id_intrucao
        this.titulo = titulo
        this.descricao = descricao
        this.imagem = imagem
    }

    fun toJson() : JSONObject {

        val jsonObject = JSONObject()

        jsonObject.put("id_intrucao", idInstrucao)
        jsonObject.put("titulo", titulo)
        jsonObject.put("descricao", descricao)
        jsonObject.put("imagem", imagem)

        return jsonObject
    }

    companion object {

        fun fromJson(jsonObject: JSONObject) : Instrucao {

            val intrucoes = Instrucao()

            intrucoes.idInstrucao = jsonObject.getInt("id_intrucao")
            intrucoes.titulo = jsonObject.getString("titulo")
            intrucoes.descricao = jsonObject.getString("descricao")
            intrucoes.imagem = jsonObject.getString("imagem")

            return intrucoes
        }

    }
}