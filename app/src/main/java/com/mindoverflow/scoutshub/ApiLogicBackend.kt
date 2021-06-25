package com.mindoverflow.scoutshub

import com.google.android.gms.maps.model.LatLng

// author : José Diogo Antunes Oliveira
// data : 6/20/2021
// description : API logic to make request on api and handle images of AMAZON S3 Bucket.

object ApiLogicBackend {

    fun ActivitiesLocation( latitude : Double , longitude : Double): LatLng {
        println(latitude)
        println(longitude)
        return  LatLng(latitude, longitude)
    }
}