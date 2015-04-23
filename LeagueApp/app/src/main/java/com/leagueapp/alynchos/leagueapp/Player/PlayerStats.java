package com.leagueapp.alynchos.leagueapp.Player;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.leagueapp.alynchos.leagueapp.Debug.Logger;
import com.leagueapp.alynchos.leagueapp.R;

/**
 * Created by Alex Lynchosky on 1/11/2015.
 */
public class PlayerStats extends Activity {
    /* Debugging */
    private static final String TAG = PlayerManager.class.getSimpleName();
    private static final Logger logger = new Logger(TAG);

    private static PlayerManager mPlayerManager;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.player_stats_layout);
    }


    public static final String SUMMONER_NAME = "com.leagueapp.alynchos.leagueapp.MESSAGE";

    public void sendSummonerName(View view) {
        Intent intentSN = new Intent(this, ShowUserInputTempAndUrlOutput.class);
        EditText editText = (EditText) findViewById(R.id.userInput_summonerName);
        String summonerName = editText.getText().toString();
        intentSN.putExtra(SUMMONER_NAME, summonerName);
        startActivity(intentSN);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}