package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Utilizador {

    var id_utilizador : Int? = null
    var email_utilizador : String? = null
    var palavra_pass : String? = null
    var id_tipo : Int? = null

    constructor()


    constructor(
        id_utilizador: Int?,
        email_utilizador: String?,
        palavra_pass: String?,
        id_tipo: Int?
    ) {
        this.id_utilizador = id_utilizador
        this.email_utilizador = email_utilizador
        this.palavra_pass = palavra_pass
        this.id_tipo = id_tipo
    }


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_utilizador", id_utilizador)
        jsonObject.put("email_utilizador", email_utilizador)
        jsonObject.put("palavra_pass", palavra_pass)
        jsonObject.put("id_tipo", id_tipo)

        return jsonObject
    }

    companion object {

        fun fromJson(jsStr: String?, arrayPosition: Int?) : Utilizador {

            val utilizador = Utilizador()

            val jsonObject : JSONObject

            //If there is the need to get a Json array from a json object
            if(arrayPosition != null) {
                val jsonArray = JSONObject(jsStr!!).getJSONArray("users")
                jsonObject = JSONObject(jsonArray[arrayPosition!!].toString())
            } else {
                jsonObject = JSONObject(jsStr!!)
            }

            utilizador.id_utilizador = jsonObject.getInt("id_utilizador")
            utilizador.email_utilizador = jsonObject.getString("email_utilizador")
            utilizador.palavra_pass = jsonObject.getString("palavra_pass")
            utilizador.id_tipo = jsonObject.getInt("id_tipo")

            return utilizador
        }

    }




}