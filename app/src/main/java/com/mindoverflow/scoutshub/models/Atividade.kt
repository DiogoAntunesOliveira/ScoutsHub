package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Atividade {
    var idAtividade     : Int?    = null
    var nome            : String? = null
    var tipo            : String? = null
    var descricao       : String? = null
    var custo           : Int?    = null
    var local           : String? = null
    var localInicio     : String? = null
    var localFim        : String? = null
    var coordenadas     : String? = null
    var urlLocal        : String? = null
    var dataInicio      : String? = null
    var dataFim         : String? = null


    constructor(
        idAtividade     : Int?,
        nome            : String?,
        tipo            : String?,
        descricao       : String?,
        custo           : Int?,
        local           : String?,
        localInicio     : String?,
        localFim        : String?,
        coordenadas     : String?,
        urlLocal        : String?,
        dataInicio      : String?,
        dataFim         : String?
    ) {
        this.idAtividade    = idAtividade
        this.nome           = nome
        this.tipo           = tipo
        this.descricao      = descricao
        this.custo          = custo
        this.local          = local
        this.localInicio    = localInicio
        this.localFim       = localFim
        this.coordenadas    = coordenadas
        this.urlLocal       = urlLocal
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
        jsonObject.put("descricao"          ,descricao)
        jsonObject.put("custo"              ,custo)
        jsonObject.put("local"              ,local)
        jsonObject.put("local_inicio"       ,localInicio)
        jsonObject.put("local_fim"          ,localFim)
        jsonObject.put("coordenadas"        ,coordenadas)
        jsonObject.put("url_local"          ,urlLocal)
        jsonObject.put("data_inicio"        ,dataInicio)
        jsonObject.put("data_fim"           ,dataFim)

        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : Atividade {
            val  atividade = Atividade()

            atividade.idAtividade       = if(!jsonObject.isNull("id_atividade"))    jsonObject.getInt("id_atividade")       else null
            atividade.nome              = if(!jsonObject.isNull("nome"))            jsonObject.getString("nome")            else null
            atividade.tipo              = if(!jsonObject.isNull("tipo"))            jsonObject.getString("tipo")            else null
            atividade.descricao         = if(!jsonObject.isNull("descricao"))       jsonObject.getString("descricao")       else null
            atividade.custo             = if(!jsonObject.isNull("custo"))           jsonObject.getInt("custo")              else null
            atividade.local             = if(!jsonObject.isNull("local"))           jsonObject.getString("local")           else null
            atividade.localInicio       = if(!jsonObject.isNull("local_inicio"))    jsonObject.getString("local_inicio")    else null
            atividade.localFim          = if(!jsonObject.isNull("local_fim"))       jsonObject.getString("local_fim")       else null
            atividade.coordenadas       = if(!jsonObject.isNull("coordenadas"))     jsonObject.getString("coordenadas")     else null
            atividade.urlLocal          = if(!jsonObject.isNull("url_local"))       jsonObject.getString("url_local")       else null
            atividade.dataInicio        = if(!jsonObject.isNull("data_inicio"))     jsonObject.getString("data_inicio")     else null
            atividade.dataFim           = if(!jsonObject.isNull("data_fim"))        jsonObject.getString("data_fim")        else null

            return atividade
        }
    }
}