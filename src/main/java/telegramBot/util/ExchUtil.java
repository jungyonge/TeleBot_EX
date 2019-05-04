package telegramBot.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import telegramBot.OkHttpConnector;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExchUtil {

    public static String post(String addr, String param) {
        boolean sent = false;
        String response = "";

        while (!sent) {
            sent = true;
            HttpURLConnection connection = null;
            DataOutputStream output = null;
            BufferedReader input = null;
            String charset = "UTF-8";

            try {
                connection = (HttpURLConnection) new URL(addr).openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setRequestProperty("Charset", charset);

                // Add parameters if included with the call or authorization is required.
                if (param != "") {
                    connection.setDoOutput(true);
                    output = new DataOutputStream(connection.getOutputStream());

                    output.writeBytes(param);
                    output.flush();
                    output.close();
                }

                String temp = "";
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((temp = input.readLine()) != null) {
                    response += temp;
                }

                input.close();
            } catch (MalformedURLException e) {
                sent = false;
                e.printStackTrace();
            } catch (IOException e) {
                sent = false;
                e.printStackTrace();
            }
        }

        return response;
    }

    public static String get(String strUrl) {
        BufferedReader input = null;
        String charset = "UTF-8";
        String response = "";


        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            String temp = "";
            input = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((temp = input.readLine()) != null) {
                response += temp;
            }

            input.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String gets(String strUrl) {
        BufferedReader input = null;
        String charset = "UTF-8";
        String response = "";


        try {
            URL url = new URL(strUrl);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            String temp = "";
            input = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((temp = input.readLine()) != null) {
                response += temp;
            }

            input.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String EXCHG_EUR = "EUR";
    public static String EXCHG_USD = "USD";
    public static String EXCHG_GBP = "GBP";
    public static String EXCHG_RUB = "RUB";

    public static double findExchg(String exchg) {
        double result = 1100d;

        int status = HttpStatus.SC_OK;
        try {

            String ret = OkHttpConnector.getString("https://quotation-api-cdn.dunamu.com/v1/forex/recent?codes=FRX.KRWUSD");//("http://finance.daum.net/api/exchanges/summaries");
            List<Map<String, Object>> retlist = new Gson().fromJson(ret, new TypeToken<List<Map<String, Object>>>(){}.getType());
            Map<String, Object> retMap = retlist.get(0);
            result = (Double) retMap.get("basePrice");
            /*List<Map<String, Object>> data = (List<Map<String, Object>>)ret.get("data");

            for(Map<String, Object> e : data) {
                if(e.get("currencyCode").toString().equals(exchg)) {
                    result = (Double)e.get("basePrice");
                    break;
                }
            }*/

        } catch (Exception e) {
            //e.printStackTrace();
            status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }

        if(status != HttpStatus.SC_OK) {

            try {

                String response = ExchUtil.get("http://finance.daum.net/exchange/exchangeMain.daum");

                String exchangeDataStart = "var exchangeData = [";
                int s = response.indexOf(exchangeDataStart) + exchangeDataStart.length() - 1;
                int e = response.indexOf(";", s);

                String exchangeData = response.substring(s, e);
                //System.out.println(exchangeData);

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(exchangeData);
                JsonArray exArr = element.getAsJsonArray();

                for (int i = 0; i < exArr.size(); i++) {
                    JsonArray exData = exArr.get(i).getAsJsonArray();

                    if (exchg.equals(exData.get(1).getAsString())) {
                        result = Double.parseDouble(exData.get(3).getAsString());
                        break;
                    }
                }
                //System.out.println(exchg + " : " + result);

                status = HttpStatus.SC_OK;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

//    public static String COIN_ETH = "ETH";
//    public static String COIN_BTC = "BTC";
//    public static String COIN_XRP = "XRP";
//    public static String COIN_LTC = "LTC";
//    public static String COIN_BCH = "BCH";
//
//    public static long findPrice(String coin) {
//
//        if (COIN_BCH.equals(coin))
//            coin = "BCC";
//
//        String response = ExchUtil.gets("https://crix-api-endpoint.upbit.com/v1/crix/trades/ticks?code=CRIX.UPBIT.KRW-" + coin + "&count=1");
//
//
//        JsonParser parser = new JsonParser();
//        JsonElement element = parser.parse(response);
//        JsonArray tickArr = element.getAsJsonArray();
//        JsonObject lastTick = tickArr.get(0).getAsJsonObject();
//
//        long lastTradePrice = Math.round(Double.parseDouble(lastTick.get("tradePrice").getAsString()));
//
//        System.out.println(coin + " : " + lastTradePrice);
//
//        return lastTradePrice;
//    }

//
//    public static double type1(String fiatType, String coinType, int grade) {
//        double fiatAmount = getFiatLimit(fiatType, grade);
//        double fiatIn = getFiatIn(fiatType, fiatAmount);
//
//        double exchg = ExchUtil.findExchg(fiatType);
//        double korFiatPrice = ExchUtil.findPrice(coinType);
//
//        double krwIn = fiatIn * exchg;
//        double krwOut;
//
//        double limit = getFiatInReverse(fiatType,korFiatPrice / exchg * 0.9981);
//        System.out.println("limit : " + limit);
//
//
//        CexAPI test = new CexAPI("up105818141", "d6pya4RiewQo1fEJ1HIu4FHU5w", "api_secret");
//
//        String orderBook = test.orderBook(coinType + "/" + fiatType);
//
//        JsonParser parser = new JsonParser();
//        JsonElement element = parser.parse(orderBook);
//        JsonObject object = element.getAsJsonObject();
//
//        JsonArray asks = object.getAsJsonArray("asks");
//
//        double fiatSum = 0;
//        double coinSum = 0;
//        System.out.println();
//        for (int i = 0; i < asks.size(); i++) {
//            JsonArray ask = asks.get(i).getAsJsonArray();
//            double price = ask.get(0).getAsDouble();
//            double coin = ask.get(1).getAsDouble();
//            double money = coin*price/0.9981;
////            if (price > limit) {
////                System.out.println ("BREAK!!!!!!!!!!!");
////                System.out.println("PRICE=" + price + ", " + coinType + "=" + coin + ", " + fiatType + "=" + money);
////
////                break;
////            }
//
//             System.out.println("PRICE=" + price + ", " + coinType + "=" + coin + ", " + fiatType + "=" + money);
//
//
//            if (fiatSum + money > fiatAmount) {
//                money = fiatAmount - fiatSum;
//                coin = money/price;
//                coinSum += coin;
//
//                break;
//            }
//            else {
//                fiatSum += money;
//                coinSum += coin;
//            }
//        }
//
//        double price = fiatAmount / coinSum;
//
//        // 수수료
//        //coinSum *= 0.9981;
//
//        System.out.println();
//        System.out.println(coinType + " : " + coinSum + " (P " + price + ", K " + (krwIn/coinSum) + ")");
//        System.out.println();
//
//        krwOut = coinSum * korFiatPrice;
//
//        System.out.println("KRW IN = " + Math.round(krwIn));
//        System.out.println("KRW OUT = " + Math.round(krwOut));
//        System.out.println("KRW INCOME = " + Math.round(krwOut - krwIn));
//        System.out.println();
//        System.out.println();
//
//
//        return krwOut - krwIn;
//    }


}
