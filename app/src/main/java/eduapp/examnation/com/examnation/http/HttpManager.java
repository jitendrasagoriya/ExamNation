package eduapp.examnation.com.examnation.http;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpManager {

    public static String getData(String urlString){
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet httpGet = new HttpGet(urlString);
        HttpResponse response;
        try{
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            httpClient.close();
        }
        return null;
    }
}
