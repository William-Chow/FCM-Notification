package com.example.dream.wedding

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.*
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                Log.i("William", "retrieve token successful : $token")
            } else {
                Log.i("William", "token should not be null...")
            }
        }.addOnFailureListener { }.addOnCanceledListener {}.addOnCompleteListener { task: Task<String> ->
            Log.i("William", "This is the token : " + task.result
            )
        }
    }
}