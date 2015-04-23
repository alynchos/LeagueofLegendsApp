package com.leagueapp.alynchos.leagueapp.Player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import javax.xml.datatype.Duration;

/**
 * Created by ChiralAlchemist on 4/22/2015.
 */
public class CustomReciever extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){
        Toast.makeText(context,"Custom Broadcast Recieved",Toast.LENGTH_SHORT);

    }

}
