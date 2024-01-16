package com.example.passwordgenerator.models.password.contents

data class UpperPwdContentimpl(override var content: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ") : PasswordContent

object UpperPwdContent{
    private var instance : PasswordContent = UpperPwdContentimpl()

    fun getInstance() : PasswordContent{
        return instance
    }
}