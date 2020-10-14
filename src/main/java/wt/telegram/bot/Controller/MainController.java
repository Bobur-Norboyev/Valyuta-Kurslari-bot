package wt.telegram.bot.Controller;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import wt.telegram.bot.AllData.AllStaticData;
import wt.telegram.bot.AllData.DataFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainController extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());

        if (message.getText().equals("/start") || message.getText().equals(EmojiParser.parseToUnicode(":back:") + "Bosh menyu")) {
            AllStaticData.isArchive = false;
            AllStaticData.archive_currencies = false;
            sendMessage = (new GeneralController().handle(message.getChatId()));
        } else if ((!update.hasCallbackQuery()) && !AllStaticData.isArchive) {
            if (message.getText().equals(EmojiParser.parseToUnicode(":bar_chart:") + " Valyuta kurslari (LIVE) " + EmojiParser.parseToUnicode(":dollar:"))) {
                AllStaticData.archive_currencies = false;
                AllStaticData.isArchive = false;
                DataFactory factory = new DataFactory();
                sendMessage.setText(factory.all(1));
                sendMessage = setKey(sendMessage);
            } else if (message.getText().equals("Asosiy Valyutalar")) {
                DataFactory factory = new DataFactory();
                sendMessage.setText(factory.all(1));
                sendMessage = setKey(sendMessage);
            } else if (message.getText().equals("Barcha Valyutalar")) {
                DataFactory factory = new DataFactory();
                sendMessage.setText(factory.all(2));
                sendMessage = setKey(sendMessage);
            } else if (message.getText().equals("Valyuta kurslari arxiv")) {
                sendMessage.setText("Valyuta kurslari arxivini ko'rish uchun kerakli sanani quyidagicha formatda kiriting:\n" +
                        "yil-oy-kun - bu yerda kun 2 xonali son, oy 2 xonali son, yil 4 xonali son.\n" +
                        "Masalan : 2007-08-01 2007-yil 1-avgustdagi valyuta kurslari taqdim etiladi.\n");
                AllStaticData.isArchive = true;
                sendMessage = setBackButton(sendMessage);
            } else if (message.getText().equals("Valyutalar tarixi")) {
                sendMessage.setText("Bu bo'limga ma'lumotlar tez kunda joylanadi.\nBizni kuzatishda davom eting.");
                sendMessage = setBackButton(sendMessage);
            } else if (message.getText().equals(EmojiParser.parseToUnicode(":envelope:") + " Yordam")) {
                sendMessage.setText("Yordam bo'lim tanlandi");
                sendMessage = setBackButton(sendMessage);
            } else if (message.getText().equals(EmojiParser.parseToUnicode(":phone:") + "Bot haqida")) {
                sendMessage.setText("Bot haqida bo'lim tanlandi");
                sendMessage = setBackButton(sendMessage);
            }
        } else if (AllStaticData.isArchive && !update.hasCallbackQuery()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            AllStaticData.isArchive = true;
            try {
                date = format.parse(message.getText());
                System.out.println(message.getText());
                AllStaticData.queryText = "http://cbu.uz/oz/arkhiv-kursov-valyut/json/all/" + message.getText() + "/";
                sendMessage.setText(new DataFactory().all(2));
                sendMessage = setBackButton(sendMessage);
            } catch (Exception e) {
                sendMessage.setText("Sana xato formatda kiritildi.\nSanani yil-oy-kun (2010-06-10) formatda kiriting :");
            }
        } else {
            sendMessage.setText("InlineTugma bosildi");
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "bot_username";
    }

    @Override
    public String getBotToken() {
        return "bot_token";
    }

    public SendMessage setBackButton(SendMessage sendMessage) {
        List<KeyboardRow> list = new LinkedList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton().setText(EmojiParser.parseToUnicode(":back:") + "Bosh menyu"));
        list.add(row);
        sendMessage.setReplyMarkup(new ReplyKeyboardMarkup().setKeyboard(list).setResizeKeyboard(true));
        return sendMessage;
    }

    public SendMessage setKey(SendMessage sendMessage) {
        KeyboardButton asosiy = new KeyboardButton();
        asosiy.setText("Asosiy Valyutalar");
        KeyboardButton hammasi = new KeyboardButton();
        hammasi.setText("Barcha Valyutalar");
        KeyboardButton home = new KeyboardButton();
        home.setText(EmojiParser.parseToUnicode(":back:") + "Bosh menyu");

        KeyboardRow qator1 = new KeyboardRow();
        qator1.add(asosiy);
        qator1.add(hammasi);
        KeyboardRow qator2 = new KeyboardRow();
        qator2.add(home);
        List<KeyboardRow> list = new LinkedList<>();
        list.add(qator1);
        list.add(qator2);
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(list);
        keyboard.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(keyboard);
        return sendMessage;
    }

}
