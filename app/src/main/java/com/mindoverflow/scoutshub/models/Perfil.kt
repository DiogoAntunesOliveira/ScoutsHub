package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Perfil {

    var idPerfil                : Int?                 = null
    var nome                    : String?              = null
    var imagem                  : String?              = null
    var dtNasc                  : String?              = null
    var genero                  : String?              = null
    var contacto                : Int?                 = null
    var morada                  : String?              = null
    var codigoPostal            : String?              = null
    var nin                     : Int?                 = null
    var totalAtivParticip       : Int?                 = null
    var idEquipa                : Int?                 = null
    var idUtilizador            : Int?                 = null

    constructor(){

    }

    constructor(
        idPerfil                        : Int?,
        nome                            : String?,
        imagem                          : String?,
        dtNasc                          : String?,
        genero                          : String?,
        contacto                        : Int?,
        morada                          : String?,
        codigoPostal                    : String?,
        nin                             : Int?,
        totalAtividadesParticipadas     : Int?,
        idEquipa                        : Int?,
        idUtilizador                    : Int?
    ) {
        this.idPerfil                   = idPerfil
        this.nome                       = nome
        this.dtNasc                     = dtNasc
        this.imagem                     = imagem
        this.dtNasc                     = dtNasc
        this.genero                     = genero
        this.contacto                   = contacto
        this.morada                     = morada
        this.codigoPostal               = codigoPostal
        this.nin                        = nin
        this.totalAtivParticip          = totalAtividadesParticipadas
        this.idEquipa                   = idEquipa
        this.idUtilizador               = idUtilizador
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id_perfil"                          , idPerfil          )
        jsonObject.put("nome"                               , nome              )
        jsonObject.put("imagem"                             , imagem            )
        jsonObject.put("dt_nasc"                            , dtNasc            )
        jsonObject.put("genero"                             , genero            )
        jsonObject.put("contacto"                           , contacto          )
        jsonObject.put("morada"                             , morada            )
        jsonObject.put("codigo_postal"                      , codigoPostal      )
        jsonObject.put("nin"                                , nin               )
        jsonObject.put("total_atividades_part"              , totalAtivParticip )
        jsonObject.put("id_equipa"                          , idEquipa          )
        jsonObject.put("id_utilizador"                      , idUtilizador      )


        return jsonObject
    }

    companion object {
        fun fromJson(jsStr: String?, arrayPosition: Int?) : Perfil {

            val perfil = Perfil()

            val jsonObject : JSONObject

            //If there is the need to get a Json array from a json object
            if(arrayPosition != null) {
                val jsonArray = JSONObject(jsStr!!).getJSONArray("perfis")
                jsonObject = JSONObject(jsonArray[arrayPosition!!].toString())
            } else {
                jsonObject = JSONObject(jsStr!!)
            }

            perfil.idPerfil             = if (!jsonObject.isNull("id_perfil"                        )) jsonObject.getInt        ("id_perfil"                        )   else null
            perfil.nome                 = if (!jsonObject.isNull("nome"                             )) jsonObject.getString     ("nome"                             )   else null
            perfil.imagem               = if (!jsonObject.isNull("imagem"                           )) jsonObject.getString     ("imagem"                           )   else null
            perfil.dtNasc               = if (!jsonObject.isNull("dt_nasc"                          )) jsonObject.getString     ("dt_nasc"                          )   else null
            perfil.genero               = if (!jsonObject.isNull("genero"                           )) jsonObject.getString     ("genero"                           )   else null
            perfil.contacto             = if (!jsonObject.isNull("contacto"                         )) jsonObject.getInt        ("contacto"                         )   else null
            perfil.morada               = if (!jsonObject.isNull("morada"                           )) jsonObject.getString     ("morada"                           )   else null
            perfil.codigoPostal         = if (!jsonObject.isNull("codigo_postal"                    )) jsonObject.getString     ("codigo_postal"                    )   else null
            perfil.nin                  = if (!jsonObject.isNull("nin"                              )) jsonObject.getInt        ("nin"                              )   else null
            perfil.totalAtivParticip    = if (!jsonObject.isNull("total_atividades_part"            )) jsonObject.getInt        ("total_atividades_part"            )   else null
            perfil.idEquipa             = if (!jsonObject.isNull("id_equipa"                        )) jsonObject.getInt        ("id_equipa"                        )   else null
            perfil.idUtilizador         = if (!jsonObject.isNull("id_utilizador"                    )) jsonObject.getInt        ("id_utilizador"                    )   else null

            return perfil
        }
    }
}

