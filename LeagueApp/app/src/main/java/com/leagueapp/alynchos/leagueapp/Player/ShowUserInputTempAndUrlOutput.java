package com.leagueapp.alynchos.leagueapp.Player;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.leagueapp.alynchos.leagueapp.R;

public class ShowUserInputTempAndUrlOutput extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentN = getIntent();
        String userInputN = intentN.getStringExtra(PlayerStats.SUMMONER_NAME);

        TextView showingUserInput = new TextView(this);
        showingUserInput.setTextSize(40);
        showingUserInput.setText(userInputN);
        //getUserInfo(userInputSN);
        setContentView(showingUserInput);
    }

    public void getUserInfo( String userSN){
        String urlBasedOnUserInput = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+userSN+"?api_key=ff62241d-f02d-443b-8309-c4b10a4bc446";
        PlayerManager playerManagerTesting = PlayerManager.getInstance();
        playerManagerTesting.pingRiot(urlBasedOnUserInput);

    }



}
