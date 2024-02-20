package com.example.weatherapp.model.dataClass

class SearchResponse : ArrayList<SearchResponse.SearchResponseItem>(){
    data class SearchResponseItem(
        val AdministrativeArea: AdministrativeArea2,
        val Country: Country2,
        val DataSets: List<String>,
        val EnglishName: String,
        val GeoPosition: GeoPosition2,
        val IsAlias: Boolean,
        val Key: String,
        val LocalizedName: String,
        val PrimaryPostalCode: String,
        val Rank: Int,
        val Region: Region2,
        val SupplementalAdminAreas: List<Any>,
        val TimeZone: TimeZone2,
        val Type: String,
        val Version: Int
    ) {
        data class AdministrativeArea2(
            val CountryID: String,
            val EnglishName: String,
            val EnglishType: String,
            val ID: String,
            val Level: Int,
            val LocalizedName: String,
            val LocalizedType: String
        )
    
        data class Country2(
            val EnglishName: String,
            val ID: String,
            val LocalizedName: String
        )
    
        data class GeoPosition2(
            val Elevation: Elevation2,
            val Latitude: Double,
            val Longitude: Double
        ) {
            data class Elevation2(
                val Imperial: Imperial2,
                val Metric: Metric2
            ) {
                data class Imperial2(
                    val Unit: String,
                    val UnitType: Int,
                    val Value: Double
                )
    
                data class Metric2(
                    val Unit: String,
                    val UnitType: Int,
                    val Value: Double
                )
            }
        }
    
        data class Region2(
            val EnglishName: String,
            val ID: String,
            val LocalizedName: String
        )
    
        data class TimeZone2(
            val Code: String,
            val GmtOffset: Double,
            val IsDaylightSaving: Boolean,
            val Name: String,
            val NextOffsetChange: Any
        )
    }
}