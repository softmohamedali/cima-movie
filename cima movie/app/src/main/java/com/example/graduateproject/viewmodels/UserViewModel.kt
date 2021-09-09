package com.example.graduateproject.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduateproject.data.DataRepo
import com.example.graduateproject.data.FirebaseSource
import com.example.graduateproject.util.StatusResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private var userRepo: DataRepo,
    application: Application
):AndroidViewModel(application) {

    var isRegester=MutableLiveData<StatusResult<FirebaseUser>>()
    var isSingIn=MutableLiveData<StatusResult<FirebaseUser>>()
    var user=userRepo.firebaseSource.currentUser()

    fun singInWithEmail(email: String,password: String) {
        isSingIn.value=StatusResult.OnLoading()
        if (hasInternetConnection())
        {
            userRepo.firebaseSource.singInWithEmail(email, password).addOnSuccessListener {

                isSingIn.value=StatusResult.OnSuccess(it.user!!)


            }.addOnFailureListener {
                isSingIn.value=StatusResult.OnError(it.message)
            }
        }
        else
        {
            isSingIn.value=StatusResult.OnError("No internet connection")
        }

    }

    fun createEmailWithEmail(name:String, email:String, password:String) {
        if (hasInternetConnection())
        {
            isRegester.value=StatusResult.OnLoading()
            userRepo.firebaseSource.createUserWithEmail(name, email, password).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    isRegester.value=StatusResult.OnSuccess(it.result!!.user!!)
                }
                else{
                    isRegester.value=StatusResult.OnError(it.exception?.message)
                }
            }
        }
        else{
            isRegester.value=StatusResult.OnError("No internet connection")
        }

    }


    fun logOut()
    {
        userRepo.firebaseSource.logOut()
        user=null
    }


    private fun hasInternetConnection():Boolean {
        val connectivityManger=getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?:return false
        val networkCapability=connectivityManger.getNetworkCapabilities(netWorkActive) ?:return false
        when{
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else->return false
        }

    }

}