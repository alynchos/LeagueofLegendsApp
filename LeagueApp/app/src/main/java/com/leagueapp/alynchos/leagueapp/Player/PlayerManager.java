package com.leagueapp.alynchos.leagueapp.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.leagueapp.alynchos.leagueapp.Debug.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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

    /* Sends data to Riot */
    public void pingRiot(final String getURL) {

        AsyncTask<Void, Void, String> task = (new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                // Testing riot api key!!!1!!1
                HttpClient client = new DefaultHttpClient();

                HttpGet get = new HttpGet(getURL);

                HttpResponse responseGet = null;

                String response = "failed";

                try {
                    responseGet = client.execute(get);

                    HttpEntity resEntityGet = responseGet.getEntity();

                    response = EntityUtils.toString(resEntityGet);
                } catch (Exception e) {
                    logger.debug("FAILED!!!!");
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                logger.debug(response);
                /*Intent intent = new Intent();
                intent.setAction(UPDATE_UI);
                mActivity.sendBroadcast(intent);*/
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                //Toast.makeText(mActivity, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /* Private helpers for saving */

    private void syncPlayerData() {
        //TODO: SAVE PLAYER DATA HERE
    }

}
