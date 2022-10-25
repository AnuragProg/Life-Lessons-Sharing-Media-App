@file:Suppress("UNCHECKED_CAST")

package com.android.personallifelessons.components


sealed class Outcome<T>(val data:T?=null, val message:String?=null){
    class Success<T>(data:T): Outcome<T>(data=data)
    class Loading<T>: Outcome<T>()
    class Error<T>(message: String): Outcome<T>(message=message)

    fun <T,R>convertTo(converter: (T)->R): Outcome<R>{
        return try{
            when(this){
                is Error -> Error(this.message!!)
                is Loading -> Loading()
                is Success -> {
                    val data = this.data!! as T
                    Success(converter(data))
                }
            }
        }catch (e: Exception){
            Error("Corrupted data")
        }
    }

    fun <T,R>convertListTo(converter: (T)->R): Outcome<List<R>>{
        return try{
            when(this){
                is Error -> Error(this.message!!)
                is Loading -> Loading()
                is Success -> {
                    val data = this.data!! as List<T>
                    Success(data.map { converter(it) })
                }
            }
        }catch (e: Exception){
            Error("Corrupted data")
        }
    }

}

