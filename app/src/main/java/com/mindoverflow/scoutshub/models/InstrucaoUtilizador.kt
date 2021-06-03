package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class InstrucaoUtilizador {
    var idInstrucao : Int? = null
    var idUtilizador : Int? = null

    constructor()

    constructor(idInstrucao: Int?, idUtilizador: Int?) {
        this.idInstrucao = idInstrucao
        this.idUtilizador = idUtilizador
    }


    fun toJson() : JSONObject {

        val jsonObject = JSONObject()

        jsonObject.put("id_intrucao", idInstrucao)
        jsonObject.put("id_utilizador", idUtilizador)

        return jsonObject
    }

    companion object {

        fun fromJson(jsonObject: JSONObject) : InstrucaoUtilizador {

            val intrucoesUtilizador = InstrucaoUtilizador()

            intrucoesUtilizador.idInstrucao = jsonObject.getInt("id_intrucao")
            intrucoesUtilizador.idUtilizador = jsonObject.getInt("id_utilizador")

            return intrucoesUtilizador
        }

    }
}