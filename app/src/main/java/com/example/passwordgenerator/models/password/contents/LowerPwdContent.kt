package com.example.passwordgenerator.models.password.contents

data class LowerPwdContentimpl(override var content: String = "ancdefghijklmnopqrstuvwxyz") : PasswordContent

object LowerPwdContent{
    private var instance : PasswordContent = LowerPwdContentimpl()

    fun getInstance() : PasswordContent{
        return instance
    }
}