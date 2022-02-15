package com.initbase.nytarticles.utils

sealed class CallState<out T>(val data: T?, val message: String?) {
    class SuccessCallState<T>(data: T?, message: String?): CallState<T>(data,message)
    class ErrorCallState<T>(data: T?, message: String):CallState<T>(data,message)
    class LoadingCallState():CallState<Nothing>(null,null)
    class InitialCallState():CallState<Nothing>(null,null)
}