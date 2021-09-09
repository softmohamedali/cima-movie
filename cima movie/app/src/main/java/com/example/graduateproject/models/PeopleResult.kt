package com.example.graduateproject.models


import com.google.gson.annotations.SerializedName

data class PeopleResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultXX>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)