package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class MaterialUsadoAtividade {

    var idAtividade     : Int?      = null
    var idMaterial      : Int?      = null
    var quantidade      : Int?      = null

    constructor(){

    }

    constructor(idAtividade: Int?, idMaterial: Int?, quantidade: Int?) {
        this.idAtividade = idAtividade
        this.idMaterial = idMaterial
        this.quantidade = quantidade
    }


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_atividade"    , idAtividade)
        jsonObject.put("id_material"     , idMaterial)
        jsonObject.put("quantidade"      , quantidade)

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : MaterialUsadoAtividade {

            val  materialUsadoAtividade = MaterialUsadoAtividade()

            materialUsadoAtividade.idAtividade       = if(!jsonObject.isNull("id_atividade"))   jsonObject.getInt("id_atividade")   else null
            materialUsadoAtividade.idMaterial        = if(!jsonObject.isNull("id_material_atividade"))    jsonObject.getInt("id_material_atividade")    else null
            materialUsadoAtividade.quantidade        = if(!jsonObject.isNull("quantidade_atividade"))     jsonObject.getInt("quantidade_atividade")     else null

            return materialUsadoAtividade
        }
    }
}