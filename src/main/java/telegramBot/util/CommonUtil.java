package telegramBot.util;

import com.google.gson.Gson;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public static String mapToQueryString(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static Map<String, Object> objectToMap(Object obj) {
        try {
            Map<String, Object> result = new HashMap<>();
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null)
                    result.put(pd.getName(), reader.invoke(obj));
            }
            return result;
        }catch (Exception e) {
            return null;
        }

    }

    public static Map<String, String> fromReqeustMap(Map<String, String[]> request) {
        Map<String, String> response = new HashMap();
        Iterator var2 = request.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            String value = ((String[])request.get(key))[0];
            response.put(key, value);
        }

        return response;
    }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
