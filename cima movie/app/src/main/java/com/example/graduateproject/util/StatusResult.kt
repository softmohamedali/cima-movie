package com.example.graduateproject.util

 sealed class StatusResult<T>(
     var data:T?=null,
     var massage:String?=null
 ) {
     class OnSuccess<T>(data:T):StatusResult<T>(data)
     class OnLoading<T>():StatusResult<T>()
     class OnError<T>(massage: String?):StatusResult<T>(null,massage)

}