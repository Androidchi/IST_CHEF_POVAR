package uz.isti.istpaymaster.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.screen.main.MainActivity
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.utils.Prefs
import org.greenrobot.eventbus.EventBus


class AppFirebaseMessagingService : FirebaseMessagingService(){

    private var count = 0

    override fun onNewToken(token: String) {
        Log.d("tag-debug : " , token)
        Prefs.setFCM(token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            Log.d("tag-debug : body " , remoteMessage?.notification?.body.toString())
            Log.d("tag-debug : title " , remoteMessage?.notification?.title.toString())
            Log.d("tag-debug : " , remoteMessage?.from.toString())
            Log.d("tag-debug : " , remoteMessage?.data.toString() ?:"")
            try{

                sendNotification(
                    remoteMessage.data.get("title").toString() ?: "",
                    remoteMessage.data.get("body").toString() ?: "",
                    remoteMessage.data.get("type").toString() ?: "",
                    remoteMessage.data.get("data").toString() ?: ""
                )
            }catch (e: java.lang.Exception){

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun sendNotification(
        title: String?,
        body: String?,
        type: String?,
        data: String?
    ) {

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var intent = Intent(this, MainActivity::class.java)
        if (type == "new_order_food"){
            EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE_PAY, 0))
        }else if (type == "update"){
            intent = Intent(this, MainActivity::class.java)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "CHEF"
        val builder =
            NotificationCompat.Builder(this, channelId)
                .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.cheef)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.cheef))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#FFFFFF"))
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setContentIntent(pendingIntent)

        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "ChEF channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(count, builder.build())
//        EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE_FCM, 0))
        playSound()
        count++
    }

    fun playSound(){
        val resID = resources.getIdentifier("alert_tone", "raw", packageName)

        val mediaPlayer = MediaPlayer.create(this, resID)
        mediaPlayer.start()
    }

}