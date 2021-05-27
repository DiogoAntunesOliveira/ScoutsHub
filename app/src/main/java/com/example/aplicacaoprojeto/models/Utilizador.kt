package com.example.aplicacaoprojeto.models

import org.json.JSONObject

class Utilizador {

    var id_utilizador : Int? = null
    var email_utilizador : String? = null
    var palavra_pass : String? = null
    var id_tipo : Int? = null
    var id_perfil : Int? = null
    var id_instruçao : Int? = null

    constructor()


    constructor(
        id_utilizador: Int?,
        email_utilizador: String?,
        palavra_pass: String?,
        id_tipo: Int?,
        id_perfil: Int?,
        id_instruçao: Int?
    ) {
        this.id_utilizador = id_utilizador
        this.email_utilizador = email_utilizador
        this.palavra_pass = palavra_pass
        this.id_tipo = id_tipo
        this.id_perfil = id_perfil
        this.id_instruçao = id_instruçao
    }


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_utilizador", id_utilizador)
        jsonObject.put("email_utilizador", email_utilizador)
        jsonObject.put("palavra_pass", palavra_pass)
        jsonObject.put("id_tipo", id_tipo)
        jsonObject.put("id_perfil", id_perfil)
        jsonObject.put("id_instruçao", id_instruçao)

        return jsonObject
    }

    companion object {

        fun fromJson(jsonObject: JSONObject) : Utilizador {
            val utilizador = Utilizador()
            utilizador.id_utilizador = jsonObject.getInt("id_utilizador")
            utilizador.email_utilizador = jsonObject.getString("email_utilizador")
            utilizador.palavra_pass = jsonObject.getString("palavra_pass")
            utilizador.id_tipo = jsonObject.getInt("id_tipo")
            utilizador.id_perfil = jsonObject.getInt("id_perfil")
            utilizador.id_instruçao = jsonObject.getInt("id_instruçao")

            return utilizador
        }

    }




}