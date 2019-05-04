package telegramBot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.codehaus.plexus.util.StringUtils;
import telegramBot.util.CommonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class OkHttpConnector {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final OkHttpClient client = new OkHttpClient();


    public static Map<String,Object> getMap(String url) {
        try {
            Request request = new Request.Builder().url(url).header("User-Agent","Mozilla/5.0").build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());
        } catch (IOException e){
            log.error(e.toString());
            return new HashMap<>();
        }
    }

    public static String getString(String url) {
        try {
            Request request = new Request.Builder().url(url).header("User-Agent","Mozilla/5.0").build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return StringUtils.defaultString(response.body().string());
        } catch (IOException e){
            return "";
        }
    }


    public static Map<String,Object> postMap(String url, Object obj)  {
        try {
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("User-Agent","Mozilla/5.0")
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());

        } catch (IOException e){
            return new HashMap<>();
        }
    }
    public static Map<String,Object> postMap2(String url, Map<String,Object> param)  {
        try {
            MediaType mediaType = MediaType.parse("x-www-form-urlencoded; charset=utf-8");
            String params = CommonUtil.mapToQueryString(param);

            RequestBody body = RequestBody.create(mediaType,params);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("User-Agent","Mozilla/5.0")
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());

        } catch (IOException e){
            return new HashMap<>();
        }
    }

    public static Response deleteSeatMap(String url, Object obj) throws Exception {
      /*  try {*/
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
            log.debug(gson.toJson(obj));
            Request request = new Request.Builder()
                    .url(url)
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return response;
    }

    public static Map<String, Object> putSeatMap(String url, Object obj) throws Exception {
        /*  try {*/
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
        log.debug(gson.toJson(obj));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        log.debug(response.toString());
        if(response.code() == 200) {
            return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());

        }else{
            return new HashMap<>();
        }
    }

    public static Response paidSeatMap(String url, Object obj) throws Exception {
        /*try {*/
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return response;
            //return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());

     /*   } catch (IOException e){
           // return new HashMap<>();
        }*/
    }

    public static String deleteString(String url, Object obj)  {
        try {
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
            Request request = new Request.Builder()
                    .url(url)
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return StringUtils.defaultString(response.body().string());

        } catch (IOException e){
            return new String();
        }
    }

    public static String postString(String url, Object obj) {
        try {
            Gson gson = new Gson();
            RequestBody body = RequestBody.create(JSON, gson.toJson(obj));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("User-Agent","Mozilla/5.0")
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return StringUtils.defaultString(response.body().string());

        } catch (IOException e){
            return "";
        }
    }

    public static String postFormDataString(String url, Map<String,String>params) {
        try {

            FormBody.Builder formBody = new FormBody.Builder();
            for(String key : params.keySet()) {
                formBody = formBody.add(key, params.get(key));
            }
            RequestBody requestBody = formBody.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("User-Agent","Mozilla/5.0")
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());

            return StringUtils.defaultString(response.body().string());

        } catch (IOException e){
            return "";
        }
    }

    public static Map<String,Object> postFormDataMap(String url, Map<String,String>params)  {
        try {
            FormBody.Builder formBody = new FormBody.Builder();
            for(String key : params.keySet()) {
                formBody = formBody.add(key, params.get(key));
            }
            RequestBody requestBody = formBody.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("User-Agent","Mozilla/5.0")
                    .build();
            Response response = client.newCall(request).execute();
            log.debug(response.toString());
            return new Gson().fromJson(response.body().string(), new TypeToken<Map<String, Object>>(){}.getType());

        } catch (IOException e){
            return new HashMap<>();
        }
    }


}
