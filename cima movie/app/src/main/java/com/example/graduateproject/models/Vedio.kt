package com.example.graduateproject.models


import com.google.gson.annotations.SerializedName

data class Vedio(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<ResultXXX>
)