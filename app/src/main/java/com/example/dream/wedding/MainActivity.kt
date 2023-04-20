package com.example.dream.wedding

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var tvNotification : TextView
    lateinit var btnButton : Button

    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
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
        tvNotification = findViewById(R.id.tvNotification)
        btnButton = findViewById(R.id.btnNotification)
        btnButton.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                Snackbar.make(findViewById<View>(android.R.id.content).rootView, "Permission Granted!", Snackbar.LENGTH_LONG).show()
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            verifyUI()
            if(!it){
                Snackbar.make(findViewById<View>(android.R.id.content).rootView, "Please grant Notification permission from App Settings", Snackbar.LENGTH_LONG).show()
            }
        }
        verifyUI()
    }

    private fun verifyUI(){
        tvNotification.text = if(notificationManager.areNotificationsEnabled()) "TRUE" else "FALSE"
    }
}