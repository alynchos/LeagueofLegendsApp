package com.leagueapp.alynchos.leagueapp.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.leagueapp.alynchos.leagueapp.Debug.Logger;

/**
 * Created by Alex Lynchosky on 1/11/2015.
 */
public class PlayerManager {
    /* Debugging */
    private static final String TAG = PlayerManager.class.getSimpleName();
    private static final Logger logger = new Logger(TAG);

    private static PlayerManager mPlayerManager;


    public static PlayerManager getInstance() {
        if (mPlayerManager == null) {
            mPlayerManager = new PlayerManager();
        }
        return mPlayerManager;
    }

    /**
     * ***************************************************
     * ****************** Saving Data ********************
     * ***************************************************
     * *****Call all these functions in an Async Task*****
     */

    public void syncWithDatabase(final Activity mActivity) {
        AsyncTask<Void, Void, Integer> task = (new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                syncPlayerData();
                return 1;
            }

            @Override
            protected void onPostExecute(Integer count) {
                /*Intent intent = new Intent();
                intent.setAction(UPDATE_UI);
                mActivity.sendBroadcast(intent);*/
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void saveData(final Activity mActivity) {
        AsyncTask<Void, Void, Integer> task = (new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //TODO: CALL SAVE FUNCTIONS HERE
                return 1;
            }

            @Override
            protected void onPostExecute(Integer count) {
                Toast.makeText(mActivity, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /* Private helpers for saving */

    private void syncPlayerData(){
        //TODO: SAVE PLAYER DATA HERE
    }

}
