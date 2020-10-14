package wt.telegram.bot.Controller;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.List;

public class GeneralController {

    public SendMessage handle(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Bo'limni tanlang :");

        KeyboardButton valyuta = new KeyboardButton();
        valyuta.setText(EmojiParser.parseToUnicode(":bar_chart:") + " Valyuta kurslari (LIVE) " + EmojiParser.parseToUnicode(":dollar:"));
        KeyboardButton valyutaTarix = new KeyboardButton();
        valyutaTarix.setText("Valyutalar tarixi");
        KeyboardButton valyutaArxiv = new KeyboardButton();
        valyutaArxiv.setText("Valyuta kurslari arxiv");
        KeyboardButton help = new KeyboardButton();
        help.setText(EmojiParser.parseToUnicode(":envelope:") + " Yordam");
        KeyboardButton about = new KeyboardButton();
        about.setText(EmojiParser.parseToUnicode(":phone:") + "Bot haqida");

        KeyboardRow row1 = new KeyboardRow();
        row1.add(valyuta);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(valyutaArxiv);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(valyutaTarix);
        KeyboardRow row4 = new KeyboardRow();
        row4.add(help);
        row4.add(about);

        List<KeyboardRow> board = new LinkedList<>();
        board.add(row1);
        board.add(row2);
        board.add(row3);
        board.add(row4);

        ReplyKeyboardMarkup key = new ReplyKeyboardMarkup();
        key.setKeyboard(board);
        key.setResizeKeyboard(true);
        sendMessage.setReplyMarkup(key);

        return sendMessage;
    }


}
