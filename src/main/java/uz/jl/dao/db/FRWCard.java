package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FRWCard extends FRWBase<Card> {
    private static FRWCard frwCard;

    public static FRWCard getInstance() {
        if (Objects.isNull(frwCard)) {
            frwCard = new FRWCard();
        }
        return frwCard;
    }

    public FRWCard() {
        super("src/main/resources/db/cards.json");
    }

    @Override
    public List<Card> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<AuthUser>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List dataList) {
        writeAll(Collections.singletonList(dataList));
    }
}
