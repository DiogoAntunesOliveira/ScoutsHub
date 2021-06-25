package com.mindoverflow.scoutshub.models

import org.json.JSONObject

class Images {
    var etag : String? = null
    var location : String? = null
    var key1 : String? = null
    var key2 : String? = null
    var bucket : String? = null

    constructor()

    constructor(
        etag: String?,
        location: String?,
        key1: String?,
        key2: String?,
        bucket: String?
    ) {
        this.etag = etag
        this.location = location
        this.key1 = key1
        this.key2 = key2
        this.bucket = bucket
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("etag"                   ,etag)
        jsonObject.put("location"               ,location)
        jsonObject.put("key1"                   ,key1)
        jsonObject.put("key2"                   ,key2)
        jsonObject.put("bucket"                 ,bucket)

        return jsonObject
    }

    companion object{
        fun fromJson(jsStr: String?, arrayPosition: Int?) : Images {
            val images = Images()

            val jsonObject : JSONObject


            jsonObject = JSONObject(jsStr!!)


            images.etag         = if(!jsonObject.isNull("etag"))         jsonObject.getString("etag")       else null
            images.location     = if(!jsonObject.isNull("location"))     jsonObject.getString("location")   else null
            images.key1         = if(!jsonObject.isNull("key1"))         jsonObject.getString("key1")       else null
            images.key2         = if(!jsonObject.isNull("key2"))         jsonObject.getString("key2")       else null
            images.bucket       = if(!jsonObject.isNull("bucket"))       jsonObject.getString("bucket")     else null


            return images
        }
    }

}