package com.deitel.twittersearches;

import android.os.AsyncTask;

/** Performs an API Request after obtaining an oauth token
 * Example: apiRequest("https://api.twitter.com/1.1/trends/place.json?id=1", "GET", "thisismytokenyo")
 * Docs: https://dev.twitter.com/oauth/application-only
 * Todo: Add param support
 */
public  class DefinedAsyncTask extends AsyncTask<String, Void, String> {

    public interface ApiResponse {
        void onResponse(String result);
        String inBackground(String... params);
    }

    public ApiResponse delegate = null;

    public DefinedAsyncTask(ApiResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        return delegate.inBackground();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.onResponse(result);
    }
}