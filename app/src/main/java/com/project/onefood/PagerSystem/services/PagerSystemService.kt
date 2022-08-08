/*
 * File Name: PagerSystemService.kt
 * File Description: Add a listener to listen the food order
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/07
 */
package com.project.onefood.PagerSystem.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import com.google.firebase.database.*
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.R

class PagerSystemService: Service() {

    // Firebase
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on bind
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Actions on create
    override fun onCreate() {
        super.onCreate()

        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }

    // Actions on start command
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        showNotification()

        val context: Context = this

        val code: String? = intent?.getStringExtra(FoodOrdersActivity.CODE_KEY)
        firebaseDatabase.getReference(getString(R.string.firebase_database_food_order_notifications)).child(code!!).addChildEventListener(
            object: ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {}

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                override fun onChildRemoved(p0: DataSnapshot) {
                    Toast.makeText(context, "Your food is ready", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, PagerSystemService::class.java)
                    stopService(intent)
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                override fun onCancelled(p0: DatabaseError) {}
            }
        )

        return START_STICKY
    }

    // Create a notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channel_id, "One Food Restaurant Pager System",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel((serviceChannel))
        }
    }

    // Show a notification
    private fun showNotification() {
        val notificationIntent = Intent(this, MainMenuActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT)
        }

        val notification = Notification
            .Builder(this, channel_id)
            .setContentText("Order Time: 09:44:00")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(notification_id, notification)
    }

    companion object {
        val channel_id: String = "one_food_channel_id"
        val notification_id: Int = 100
    }
}