package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Atividade {
    var idAtividade     : Int?    = null
    var nome            : String? = null
    var tipo            : String? = null
    var imagem          : String? = null
    var descricao       : String? = null
    var custo           : String? = null
    var local           : String? = null
    var localInicio     : String? = null
    var localFim        : String? = null
    var latitude        : String? = null
    var longitude       : String? = null
    var dataInicio      : String? = null
    var dataFim         : String? = null


    constructor(
        idAtividade     : Int?,
        nome            : String?,
        tipo            : String?,
        imagem          : String?,
        descricao       : String?,
        custo           : String?,
        local           : String?,
        localInicio     : String?,
        localFim        : String?,
        latitude        : String?,
        longitude       : String?,
        dataInicio      : String?,
        dataFim         : String?
    ) {
        this.idAtividade    = idAtividade
        this.nome           = nome
        this.tipo           = tipo
        this.imagem         = imagem
        this.descricao      = descricao
        this.custo          = custo
        this.local          = local
        this.localInicio    = localInicio
        this.localFim       = localFim
        this.latitude       = latitude
        this.longitude      = longitude
        this.dataInicio     = dataInicio
        this.dataFim        = dataFim
    }

    constructor(){

    }


    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_atividade"       ,idAtividade)
        jsonObject.put("nome"               ,nome)
        jsonObject.put("tipo"               ,tipo)
        jsonObject.put("imagem"             ,imagem)
        jsonObject.put("descricao"          ,descricao)
        jsonObject.put("custo"              ,custo)
        jsonObject.put("local"              ,local)
        jsonObject.put("local_inicio"       ,localInicio)
        jsonObject.put("local_fim"          ,localFim)
        jsonObject.put("latitude"           ,latitude)
        jsonObject.put("longitude"          ,longitude)
        jsonObject.put("data_inicio"        ,dataInicio)
        jsonObject.put("data_fim"           ,dataFim)

        return jsonObject
    }

    companion object{
        fun fromJson(jsStr: String?, arrayPosition: Int?) : Atividade {
            val atividade = Atividade()

            val jsonObject : JSONObject

            //If there is the need to get a Json array from a json object
            if(arrayPosition != null) {
                val jsonArray = JSONObject(jsStr!!).getJSONArray("activities")
                jsonObject = JSONObject(jsonArray[arrayPosition!!].toString())
            } else {
                jsonObject = JSONObject(jsStr!!)
            }

            atividade.idAtividade       = if(!jsonObject.isNull("id_atividade"))    jsonObject.getInt("id_atividade")       else null
            atividade.nome              = if(!jsonObject.isNull("nome"))            jsonObject.getString("nome")            else null
            atividade.tipo              = if(!jsonObject.isNull("tipo"))            jsonObject.getString("tipo")            else null
            atividade.imagem            = if(!jsonObject.isNull("imagem"))          jsonObject.getString("imagem")          else null
            atividade.descricao         = if(!jsonObject.isNull("descricao"))       jsonObject.getString("descricao")       else null
            atividade.custo             = if(!jsonObject.isNull("custo"))           jsonObject.getString("custo")           else null
            atividade.local             = if(!jsonObject.isNull("local"))           jsonObject.getString("local")           else null
            atividade.localInicio       = if(!jsonObject.isNull("local_inicio"))    jsonObject.getString("local_inicio")    else null
            atividade.localFim          = if(!jsonObject.isNull("local_fim"))       jsonObject.getString("local_fim")       else null
            atividade.latitude          = if(!jsonObject.isNull("latitude"))        jsonObject.getString("latitude")        else null
            atividade.longitude         = if(!jsonObject.isNull("longitude"))       jsonObject.getString("longitude")       else null
            atividade.dataInicio        = if(!jsonObject.isNull("data_inicio"))     jsonObject.getString("data_inicio")     else null
            atividade.dataFim           = if(!jsonObject.isNull("data_fim"))        jsonObject.getString("data_fim")        else null

            return atividade
        }
    }
}