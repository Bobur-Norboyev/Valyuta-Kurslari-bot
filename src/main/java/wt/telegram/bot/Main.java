package wt.telegram.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import wt.telegram.bot.Controller.MainController;

public class Main {

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(new MainController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
