package gay.plat.victeemtweaks.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ApiUtil {
    public static JsonObject getApiData(String urlString) {
        try {
            URL url = URI.create(urlString.toLowerCase()).toURL();
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();
            if (httpurlconnection.getResponseCode() / 100 != 2) {
                return null;
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return JsonParser.parseString(response.toString()).getAsJsonObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
