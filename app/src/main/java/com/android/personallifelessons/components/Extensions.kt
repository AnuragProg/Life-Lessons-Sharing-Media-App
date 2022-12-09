package com.android.personallifelessons.components

import android.content.Context
import android.content.Intent
import com.android.personallifelessons.data.dto.response.Pll
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

fun Context.sharePll(pll: Pll){
    val content = "Title\n${pll.title}\n\nLearning\n${pll.learning}\n\nRelated Story\n${pll.relatedStory}"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, content)
    }
    //Allows user to select the content from share intent
    startActivity(Intent.createChooser(shareIntent, "Share"),null)
    //startActivity(context,shareIntent,null)
}

// For showing correct ui when server cannot be connected to
// because SocketTimeoutErrorException has predefined msg
// ServerConnectionError give us ability to send what exception it is and desired message to show user
class ServerConnectionError(msg: String): Exception(msg)