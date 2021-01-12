package com.example.connectionservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.telecom.*
import android.telecom.TelecomManager.PRESENTATION_ALLOWED
import android.util.Log

class ConnService : ConnectionService() {

    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        super.onCreateIncomingConnection(connectionManagerPhoneAccount, request)
        Log.i("CallConnectionService", "onCreateIncomingConnection")
        val conn = VoiceConnection(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            conn.connectionProperties = Connection.PROPERTY_SELF_MANAGED
        }
        conn.setCallerDisplayName("test call", TelecomManager.PRESENTATION_ALLOWED)
        conn.setAddress(Uri.parse("tel:" + "+919582940055"), PRESENTATION_ALLOWED)
        conn.setRinging()
        conn.setInitializing()
        conn.setActive()
        return conn
    }

}


class VoiceConnection(ctx: Context) : Connection() {
    val TAG = "CallConnection"
    val context: Context

    init {
        context = ctx
    }


    override fun onShowIncomingCallUi() {
        super.onShowIncomingCallUi()
        Log.i(TAG, "onShowIncomingCallUi")
    }

    override fun onAnswer() {
        super.onAnswer()
        val myIntent: Intent = Intent(context, SecondActivity::class.java)
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(myIntent)
    }

}
