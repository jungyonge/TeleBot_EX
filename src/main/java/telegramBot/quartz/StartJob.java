package telegramBot.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import telegramBot.TelegramBot;
import telegramBot.module.ReplyMessageController;

public class StartJob implements Job {

    ReplyMessageController replyMessageController = new ReplyMessageController();
    TelegramBot telegramBot = new TelegramBot();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        /*
         * Job Interface를 implements하여
         * execute 메소드에 로직을 넣는다.
         * */

        System.out.println("알람실행");


    }
}