package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Equipa {

    var id_equipa   : Int?      = null
    var nome_equipa : String?   = null
    var id_seccao   : Int?      = null


    constructor(){

    }

    constructor(id_equipa: Int?, nome_equipa: String?, id_seccao: Int?) {
        this.id_equipa = id_equipa
        this.nome_equipa = nome_equipa
        this.id_seccao = id_seccao
    }


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_equipa", id_equipa)
        jsonObject.put("nome_equipa", nome_equipa)
        jsonObject.put("id_seccao", id_seccao)

        return jsonObject
    }


    companion object {

        fun fromJson(jsonObject: JSONObject) : Equipa {
            val equipa = Equipa()
            equipa.id_equipa =   jsonObject.getInt("id_equipa")
            equipa.nome_equipa = jsonObject.getString("nome_equipa")
            equipa.id_seccao =   jsonObject.getInt("id_seccao")

            return equipa
        }


    }


}

