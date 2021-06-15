package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Perfil {

    var idPerfil                : Int?                 = null
    var nome                    : String?              = null
    //var imagem                  : String?              = null
    var dtNasc                  : String?              = null
    var genero                  : String?              = null
    var contacto                : Int?                 = null
    var morada                  : String?              = null
    var codigoPostal            : String?              = null
    var nin                     : Int?                 = null
    var totalAtivParticip       : Int?                 = null
    var idEquipa                : Int?                 = null



    constructor(){

    }

    constructor(
        idPerfil                        : Int?,
        nome                            : String?,
        //imagem                          : String?,
        dtNasc                          : String?,
        genero                          : String?,
        contacto                        : Int?,
        morada                          : String?,
        codigoPostal                    : String?,
        nin                             : Int?,
        totalAtividadesParticipadas     : Int?,
        idEquipa                        : Int?
    ) {
        this.idPerfil                   = idPerfil
        this.nome                       = nome
        //this.imagem                     = imagem
        this.dtNasc                    = dtNasc
        this.genero                     = genero
        this.contacto                   = contacto
        this.morada                     = morada
        this.codigoPostal               = codigoPostal
        this.nin                        = nin
        this.totalAtivParticip          = totalAtividadesParticipadas
        this.idEquipa                   = idEquipa
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_perfil"                          , idPerfil          )
        jsonObject.put("nome"                               , nome              )
        //jsonObject.put("imagem"                             , imagem              )
        jsonObject.put("data_nascimento"                    , dtNasc           )
        jsonObject.put("genero"                             , genero            )
        jsonObject.put("contacto"                           , contacto          )
        jsonObject.put("morada"                             , morada            )
        jsonObject.put("codigo_postal"                      , codigoPostal      )
        jsonObject.put("numero_identificacao_fiscal"        , nin               )
        jsonObject.put("total_atividades_participadas"      , totalAtivParticip )
        jsonObject.put("id_equipa"                          , idEquipa          )

        return jsonObject
    }

    companion object {
        fun fromJson(jsStr: String?, id: Int?, message: String?) : Perfil {
            val perfil = Perfil()

            val jsObject : JSONObject

            //If there is the need to get a Json array from a json object
            if(message == "get_json_array_by_id"){
                val jsonArray = JSONObject(jsStr!!).getJSONArray("perfis")
                jsObject = JSONObject(jsonArray[id!!].toString())
            } else {
                jsObject = JSONObject(jsStr!!)
            }

            perfil.idPerfil             = if (!jsObject.isNull("id_perfil"                        )) jsObject.getInt        ("id_perfil"                        )   else null
            perfil.nome                 = if (!jsObject.isNull("nome"                             )) jsObject.getString     ("nome"                             )   else null
            //perfil.imagem               = if (!jsonObject.isNull("imagem"                           )) jsonObject.getString     ("imagem"                           )   else null
            perfil.dtNasc              = if (!jsObject.isNull("dt_nasc"                           )) jsObject.getString     ("dt_nasc"                  )           else null
            perfil.genero               = if (!jsObject.isNull("genero"                           )) jsObject.getString     ("genero"                           )   else null
            perfil.contacto             = if (!jsObject.isNull("contacto"                         )) jsObject.getInt        ("contacto"                         )   else null
            perfil.morada               = if (!jsObject.isNull("morada"                           )) jsObject.getString     ("morada"                           )   else null
            perfil.codigoPostal         = if (!jsObject.isNull("codigo_postal"                    )) jsObject.getString     ("codigo_postal"                    )   else null
            perfil.nin                  = if (!jsObject.isNull("nin"                              )) jsObject.getInt        ("nin"      )                           else null
            perfil.totalAtivParticip    = if (!jsObject.isNull("total_atividades_part"            )) jsObject.getInt        ("total_atividades_part"    )            else null
            perfil.idEquipa             = if (!jsObject.isNull("id_equipa"                        )) jsObject.getInt        ("id_equipa"                        )   else null

            return perfil
        }
    }
}

