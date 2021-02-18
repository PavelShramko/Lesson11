package com.example.architecturebase.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewPost (
        @SerializedName("CAD")
        @Expose
        val cAD: Long,

        @SerializedName("HKD")
        @Expose
        val hKD: Long,

        @SerializedName("ISK")
        @Expose
        val iSK: Long,

        @SerializedName("PHP")
        @Expose
        val pHP: Long,
        )