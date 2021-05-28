package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Perfil {

    var idPerfil                : Int?                 = null
    var nome                    : String?              = null
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
        fun fromJson(jsonObject: JSONObject) : Perfil {
            val perfil = Perfil()
            perfil.idPerfil             = if (!jsonObject.isNull("id_perfil"                        )) jsonObject.getInt        ("id_perfil"                        )   else null
            perfil.nome                 = if (!jsonObject.isNull("nome"                             )) jsonObject.getString     ("nome"                             )   else null
            perfil.dtNasc              = if (!jsonObject.isNull("data_nascimento"                  )) jsonObject.getString     ("data_nascimento"                  )   else null
            perfil.genero               = if (!jsonObject.isNull("genero"                           )) jsonObject.getString     ("genero"                           )   else null
            perfil.contacto             = if (!jsonObject.isNull("contacto"                         )) jsonObject.getInt        ("contacto"                         )   else null
            perfil.morada               = if (!jsonObject.isNull("morada"                           )) jsonObject.getString     ("morada"                           )   else null
            perfil.codigoPostal         = if (!jsonObject.isNull("codigo_postal"                    )) jsonObject.getString     ("codigo_postal"                    )   else null
            perfil.nin                  = if (!jsonObject.isNull("numero_identificacao_fiscal"      )) jsonObject.getInt        ("numero_identificacao_fiscal"      )   else null
            perfil.totalAtivParticip    = if (!jsonObject.isNull("total_atividades_participadas"    )) jsonObject.getInt        ("total_atividades_participadas"    )   else null
            perfil.idEquipa             = if (!jsonObject.isNull("id_equipa"                        )) jsonObject.getInt        ("id_equipa"                        )   else null

            return perfil
        }
    }
}

