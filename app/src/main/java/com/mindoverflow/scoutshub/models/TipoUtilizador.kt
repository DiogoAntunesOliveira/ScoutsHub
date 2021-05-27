package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class TipoUtilizador {

    var id_tipo : Int? = null
    var designacao : String? = null

    constructor()

    constructor(id_tipo: Int?, designacao: String?) {
        this.id_tipo = id_tipo
        this.designacao = designacao
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_tipo", id_tipo)
        jsonObject.put("designacao", designacao)

        return jsonObject
    }

    companion object {

        fun fromJson(jsonObject: JSONObject) : TipoUtilizador {
            val tipo_utilizador = TipoUtilizador()
            tipo_utilizador.id_tipo = jsonObject.getInt("id_tipo")
            tipo_utilizador.designacao = jsonObject.getString("designacao")

            return tipo_utilizador
        }

    }

}