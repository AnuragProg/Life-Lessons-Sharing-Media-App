package com.android.personallifelessons.components

import okhttp3.ResponseBody
import org.json.JSONObject


// For converting ErrorBody returned from Api to Exception ( on failure )
class ApiException: Exception{
    constructor(message: String): super(message)
    constructor(errorBody: ResponseBody?): super(errorBody.convertToMessage())
}

fun ResponseBody?.convertToMessage(): String{
    if(this == null)
        return "Api error"
    return JSONObject(this.string()).get("message").toString()
}