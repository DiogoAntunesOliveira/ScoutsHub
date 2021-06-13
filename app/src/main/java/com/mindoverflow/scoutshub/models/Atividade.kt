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

            var perfis = jsonObject.getJSONArray("perfis")
            for (i in 0 until perfis.length()){

                // itera todos os objetos dentro do array
                var jsonObjectPerfil = perfis.getJSONObject(i)

                // construir json object atividade
                atividade.idAtividade       = if(!jsonObjectPerfil.isNull("id_atividade"))    jsonObjectPerfil.getInt("id_atividade")       else null
                atividade.nome              = if(!jsonObjectPerfil.isNull("nome"))            jsonObjectPerfil.getString("nome")            else null
                atividade.tipo              = if(!jsonObjectPerfil.isNull("tipo"))            jsonObjectPerfil.getString("tipo")            else null
                atividade.descricao         = if(!jsonObjectPerfil.isNull("descricao"))       jsonObjectPerfil.getString("descricao")       else null
                atividade.custo             = if(!jsonObjectPerfil.isNull("custo"))           jsonObjectPerfil.getInt("custo")              else null
                atividade.local             = if(!jsonObjectPerfil.isNull("local"))           jsonObjectPerfil.getString("local")           else null
                atividade.localInicio       = if(!jsonObjectPerfil.isNull("local_inicio"))    jsonObjectPerfil.getString("local_inicio")    else null
                atividade.localFim          = if(!jsonObjectPerfil.isNull("local_fim"))       jsonObjectPerfil.getString("local_fim")       else null
                atividade.coordenadas       = if(!jsonObjectPerfil.isNull("coordenadas"))     jsonObjectPerfil.getString("coordenadas")     else null
                atividade.urlLocal          = if(!jsonObjectPerfil.isNull("url_local"))       jsonObjectPerfil.getString("url_local")       else null
                atividade.dataInicio        = if(!jsonObjectPerfil.isNull("data_inicio"))     jsonObjectPerfil.getString("data_inicio")     else null
                atividade.dataFim           = if(!jsonObjectPerfil.isNull("data_fim"))        jsonObjectPerfil.getString("data_fim")        else null
            }

            //teste

            return atividade
        }
    }
}