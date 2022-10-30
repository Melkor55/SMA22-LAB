package com.example.sma1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult,intent);
        asyncTask.execute();
    }

    private static class Task extends AsyncTask<String, String, String>
    {
        private final PendingResult pendingResult;
        private final Intent intent;

        private Task(PendingResult pendingResult, Intent intent)
        {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }

        @Override
        protected String doInBackground(String... strings)
        {
            //
            //return log;
            return "";
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            //
            //pendingResult.finish();
        }
    }
}


