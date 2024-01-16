package com.example.passwordgenerator.models.password.contents

data class NumericPwdContentimpl(override var content: String = "1234567890") : PasswordContent

object NumericPwdContent{
    private var instance : PasswordContent = NumericPwdContentimpl()

    fun getInstance() : PasswordContent{
        return instance
    }
}