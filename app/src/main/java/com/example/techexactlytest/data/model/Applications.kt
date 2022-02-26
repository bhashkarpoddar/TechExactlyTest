package com.example.techexactlytest.data.model

data class Applications(
    val app_icon: String,
    val app_id: Int,
    val app_name: String,
    val app_package_name: String,
    val fk_kid_id: Int,
    val status: String,
    var isChecked: Boolean = false
)