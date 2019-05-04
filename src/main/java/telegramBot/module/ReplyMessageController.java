package telegramBot.module;


import com.google.gson.Gson;
import telegramBot.mybatis.MyBatisConnectionFactory;
import telegramBot.mybatis.SetalarmDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class ReplyMessageController {
    Gson gson = new Gson();
    public String response = null;
    public String msg = null;
    SendMessageController sendMessageController = new SendMessageController();

    private String TestURL ="http://localhost:8081";



    SetalarmDAO setalarmDAO = new SetalarmDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    BufferedReader input = null;


    public BufferedReader httpurl(String targetUrl) throws Exception{
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        input = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return input;
    }


    public void sendMessage(String msg, String botToken, long getChatId) {
        String url = null;
        System.out.println("----------------------------------------------------------------------------");

        try {
            url = "https://api.telegram.org/bot" + botToken + "/sendmessage?text=" + msg + "&chat_id=" + getChatId + "&reply_markup={    \"keyboard\": \n" +
                    "    [\n" +
                    "        [\"help\",\"명령어1\"], \n" +
                    "    ]}";
            response = sendMessageController.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("전송된 url = " + url);
        System.out.println("전송된 message = " + msg);
        System.out.println("url의 response = " + response);
    }

    //help 명령어
    public void help(String botToken, long getChatid){
        msg = "지원되는 명령어는  %0A" +
                "매수연동현황(전일), 매수연동현황(당일)%0A" +
                "매수연동현황(주간), KST가격 %0A" +
                "거래소잔고확인  %0A" +
                "입니다.";
        sendMessage(msg,botToken,getChatid);
    }

    public void command1(String botToken,long getChatid){

        msg =   "command1";
        sendMessage(msg,botToken,getChatid);
    }

    public void command2(String botToken, long getChatid){

            msg = "command2" ;
            sendMessage(msg,botToken,getChatid);
    }





}
