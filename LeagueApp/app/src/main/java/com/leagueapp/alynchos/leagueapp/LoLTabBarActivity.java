package com.leagueapp.alynchos.leagueapp;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.leagueapp.alynchos.leagueapp.Debug.Logger;
import com.leagueapp.alynchos.leagueapp.Player.PlayerManager;
import com.leagueapp.alynchos.leagueapp.Player.PlayerStats;
import com.leagueapp.alynchos.leagueapp.SaveData.FeedReaderDbHelper;

/**
 * Created by Alex Lynchosky on 1/11/2015.
 */
public class LoLTabBarActivity extends TabActivity {
    /* Debugging */
    private static final String TAG    = LoLTabBarActivity.class.getSimpleName();
    private static final Logger logger = new Logger(TAG);

    /* Main Tab */
    private static LoLTabBarActivity lolTabBarActivity;

    private Resources mResources;
    private TabHost mTabHost;

    /* Tab Names */
    private final String TAB_CHARACTER      = "character";
    private final String TAB_INVENTORY      = "inventory";
    private final String TAB_COMBAT         = "combat";
    private final String TAB_NOTES         = "notes";

    /* Save Data */
    private static FeedReaderDbHelper feedReaderDbHelper;
    private static boolean dataSaved = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug("onCreate called");

        // Prevent launching the main activity on top of other activities
        // This will ensure we only have one instance displayed
        final Intent intent = getIntent();
        if (!isTaskRoot()) {
            final String intentAction = intent.getAction();

            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                logger.debug("Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;
            }
        }

        lolTabBarActivity = this;
        FeedReaderDbHelper.setContext(getApplicationContext());
        feedReaderDbHelper = FeedReaderDbHelper.getInstance();
        mTabHost = getTabHost();

        mResources = getResources();
        /* Insert Tabs */
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CHARACTER)
                .setIndicator(getString(R.string.tab_player_stats))
                .setContent(new Intent(lolTabBarActivity, PlayerStats.class)));
        //TextView x = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        //x.setTextSize(11);
        //mTabHost.getTabWidget().getChildAt(0).getLayoutParams().width = 100;


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                logger.debug("Changing to " + tabId + " tab");
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        logger.debug("onResume");
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.syncWithDatabase(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.saveData(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public static LoLTabBarActivity getInstance(){
        return lolTabBarActivity;
    }
}
