package telegramBot;

import org.quartz.SchedulerException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.module.ReplyMessageController;
import telegramBot.quartz.CronTrigger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update arg0) {
        // TODO
        // TODO Auto-generated method stub
        int getId = arg0.getMessage().getFrom().getId(); // userid
        String getFirstName = arg0.getMessage().getFrom().getFirstName(); //보낸사람 이름
        String getLastName = arg0.getMessage().getFrom().getLastName(); //보낸사람 성
        long getChatId = arg0.getMessage().getChatId();  // 채팅방의 ID
        String getText = arg0.getMessage().getText();  // 받은 TEXT
        long getmsgid = arg0.getMessage().getMessageId();

        TimeZone time;
        Date date = new Date();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = TimeZone.getTimeZone("Asia/Seoul");
        df1.setTimeZone(time);
        String time1 = df1.format(date);

        System.out.println("시간 = " + time1 );
        System.out.println("userid = "+ getId); //get ID 는 user id
        System.out.println("user firstname = "+ getFirstName); //보낸사람 이름
        System.out.println("user lastname = "+ getLastName); //보낸사람 성
        System.out.println("user chatid = "+ getChatId);  // 채팅방의 ID
        System.out.println("user send this message = "+ getText);  // 받은 TEXT
        System.out.println("user msg id = " + getmsgid);

        ReplyMessageController replyMessageController = new ReplyMessageController();



            if (getText.equals("명령어1") || getText.equals("/명령어1")) {
                replyMessageController.command1(getBotToken(), getChatId);
            }
            else if (getText.equals("명령어2") || getText.equals("/명령어2")) {
                replyMessageController.command2( getBotToken(), getChatId);
            }
    }


    @Override
    public String getBotUsername() {
        // TODO
        return "";
    }

    @Override
    public String getBotToken() {
        // TODO

        return  "";
    }


    public static void main(String[] args) {

        CronTrigger cronTrigger = new CronTrigger();
        System.out.println("트리거실행");
        try {
            cronTrigger.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        TelegramBot telegramBot =  new TelegramBot();

        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
