package com.fsp.festiplansb.model

class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String) {
    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', password='$password')"
    }

}