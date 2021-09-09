package com.example.graduateproject.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseSource @Inject constructor(
    var firebaseAuth: FirebaseAuth
) {
    fun createUserWithEmail(name:String,email:String,password:String)=
            firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun singInWithEmail(email:String,password: String)=
            firebaseAuth.signInWithEmailAndPassword(email, password)

    fun logOut()=firebaseAuth.signOut()

    fun currentUser()=firebaseAuth.currentUser
}