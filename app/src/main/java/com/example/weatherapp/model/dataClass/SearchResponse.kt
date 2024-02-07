package com.example.weatherapp.model.dataClass

data class SearchResponse(
    val AdministrativeArea: AdministrativeArea1,
    val Country: Country1,
    val DataSets: List<String>,
    val EnglishName: String,
    val GeoPosition: GeoPosition1,
    val IsAlias: Boolean,
    val Key: String,
    val LocalizedName: String,
    val PrimaryPostalCode: String,
    val Rank: Int,
    val Region: Region1,
    val SupplementalAdminAreas: List<Any>,
    val TimeZone: TimeZone1,
    val Type: String,
    val Version: Int
) {
    data class AdministrativeArea1(
        val CountryID: String,
        val EnglishName: String,
        val EnglishType: String,
        val ID: String,
        val Level: Int,
        val LocalizedName: String,
        val LocalizedType: String
    )

    data class Country1(
        val EnglishName: String,
        val ID: String,
        val LocalizedName: String
    )

    data class GeoPosition1(
        val Elevation: Elevation1,
        val Latitude: Double,
        val Longitude: Double
    ) {
        data class Elevation1(
            val Imperial: Imperial1,
            val Metric: Metric1
        ) {
            data class Imperial1(
                val Unit: String,
                val UnitType: Int,
                val Value: Double
            )

            data class Metric1(
                val Unit: String,
                val UnitType: Int,
                val Value: Double
            )
        }
    }

    data class Region1(
        val EnglishName: String,
        val ID: String,
        val LocalizedName: String
    )

    data class TimeZone1(
        val Code: String,
        val GmtOffset: Double,
        val IsDaylightSaving: Boolean,
        val Name: String,
        val NextOffsetChange: Any
    )
}
