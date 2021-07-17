package com.fcmsender.fcm_sender

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fcmsender.FCMSender
import com.fcmsender.fcm_sender.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity(), FCMSender.ResponseListener {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.activity = this
    }

    override fun onSuccess(response: String) {
        Log.d(TAG, "onSuccess. $response")
        Toast.makeText(this, "onSuccess", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(errorCode: Int,message: String) {
        Log.d(TAG,"onFailure. $errorCode $message")
        Toast.makeText(this, "onFailure", Toast.LENGTH_SHORT).show()
    }

    fun sendPush() {
        val data = JSONObject()
        data.put("title", binding.title.text.toString())
        data.put("message", binding.mesagge.text.toString())
        data.put(binding.key.text.toString(), binding.value.text.toString())

        Log.d(TAG, "data : ${data}")

        // FCM Server Key
        val serverKey = "******"
        // User Device FCM Token
        val userToken = "******"
        
        val push = FCMSender.Builder()
            .serverKey(serverKey)
            .toTokenOrTopic(userToken) //either topic or user registration token
//          .toMultipleTokens(listOfToken)
            .responseListener(this)
//          .setTimeToLive(30) // 0 to 2,419,200 seconds (4 weeks)
//          .setDryRun(false)  //test a request without actually sending a message.
            .setData(data)
            .build()
        push.sendPush(this)
    }
}
