package com.example.passwordgenerator.models.password.contents

interface PasswordContent {

    var content : String

    fun getRandom(): Char{
        val index = (Math.random()*content.length).toInt()
        return content[index]
    }
}