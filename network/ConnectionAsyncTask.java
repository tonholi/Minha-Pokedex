package br.com.unicuritiba.minhapokedex.network;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectionAsyncTask extends AsyncTask<String, Integer, String> {

    private ConnectionListener listener;

    public ConnectionAsyncTask(ConnectionListener listener) {
        this.listener = listener;
    }

    //dado retornado e o "s" do onPostExecute
    @Override
    protected String doInBackground(String... strings) {

        String urlString = strings[0];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject object = new JSONObject(s);
            listener.onRequestResult(object);
        } catch (
                Exception e) {
            listener.onRequestResult(null);
        }
    }

    public interface ConnectionListener {
        void onRequestResult(JSONObject object);
    }

}
