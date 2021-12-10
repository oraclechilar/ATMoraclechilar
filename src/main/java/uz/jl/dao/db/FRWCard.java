package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.card.Cards;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 10.12.2021 20:57
 * Project : ATMoracle
 */
    public final class FRWCard extends FRWBase<Cards>{

        private static uz.jl.dao.db.FRWCard frwCard;

        public static uz.jl.dao.db.FRWCard getInstance() {
            if (Objects.isNull(frwCard)) {
                frwCard = new uz.jl.dao.db.FRWCard();
            }
            return frwCard;
        }


        public FRWCard() {
            super(AppConfig.get("db.cards.path"));
        }

        @Override
        public List<Cards> getAll() {
            if (list.isEmpty()) {
                try (FileReader fileReader = new FileReader(path);
                     BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                    String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                    list = gson.fromJson(jsonDATA, new TypeToken<List<Cards>>() {
                    }.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        public void writeAll(List<Cards> dataList) {
            try (FileWriter fileWriter = new FileWriter(path, false);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                String jsonDATA = gson.toJson(dataList);
                bufferedWriter.write(jsonDATA);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void writeAll(Cards user) {
            writeAll(Collections.singletonList(user));
        }
    }


