package fts.ahmed.kelineyt.util

import android.util.Patterns

fun validateEmail(email:String):RegisterValidation{
    if (email.isEmpty()) return RegisterValidation.Failed("Email cant be empty")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return RegisterValidation.Failed("Wrong Email Format")

    return RegisterValidation.Success
}

fun validatePassword(password:String):RegisterValidation{
    if (password.isEmpty()) return RegisterValidation.Failed("password cant be empty")
    if (password.length<6) return RegisterValidation.Failed("password should contains six chars")
    return RegisterValidation.Success
}