package wt.telegram.bot.AllData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.vdurmont.emoji.EmojiParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

//serverdan kerakli ma'lumotlarni olish uchun ishlatiladigan asosiy sinf
public class DataFactory {
    //server qaytarga javoblarni (json array) qabul qiilib oluvchi list
    public List<Valyuta> list;

    public String all(int k) {

        String s = "", query;
        if (AllStaticData.isArchive) {
            query = AllStaticData.queryText;
        } else {
            query = "https://cbu.uz/oz/arkhiv-kursov-valyut/json/";
        }
        try {
            URL cbu = new URL(query);
            URLConnection yc = cbu.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine, data = "";

            while ((inputLine = in.readLine()) != null)
                data = data + inputLine;

            in.close();
            JsonArray array = new Gson().fromJson(data, JsonArray.class);
            Type listType = new TypeToken<List<Valyuta>>() {
            }.getType();
            list = new Gson().fromJson(data, listType);

            if (k == 1) {
                s = "Asosiy Valyutalar :\n";
                for (int i = 0; i < 4; i++) {
                    s += list.get(i).getNominal() + " " + list.get(i).Ccy + " = " + list.get(i).getRate() + " so'm " + "Holati : ";
                    if (list.get(i).Diff >= 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":arrow_up_small:") + "\n";
                    } else if (list.get(i).Diff < 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":small_red_triangle_down:") + "\n";
                    } else if (list.get(i).Diff == 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":recycle:") + "(O'zgarmagan)\n";
                    }
                }
            } else if (k == 2) {
                s = "Barcha Valyutalar :\n";
                for (int i = 0; i < list.size(); i++) {
                    s += list.get(i).getNominal() + " " + list.get(i).Ccy + " = " + list.get(i).getRate() + " so'm " + "Holati : ";
                    if (list.get(i).Diff >= 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":arrow_up_small:") + "\n";
                    } else if (list.get(i).Diff < 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":small_red_triangle_down:") + "\n";
                    } else if (list.get(i).Diff == 0) {
                        s += list.get(i).Diff + " " + EmojiParser.parseToUnicode(":recycle:") + "(O'zgarmagan)\n";
                    }
                }
            }
        } catch (Exception e) {
            s = "Profilaktika ishlari sababli yoki server bilan aloqa yomonligi sababli ma'lumotlarni  yuklashning imkoni bo'lmadi.\nIltimos qaytadan urinib ko'ring.";
        }

        return s;
    }
}
