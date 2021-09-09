package com.example.graduateproject.data

import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataRepo @Inject constructor(
    var remoteSource:RemoteSource,
    var firebaseSource: FirebaseSource,
    var localSource:Localsource
){
}