package uz.jl.dao.card;

import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWCard;
import uz.jl.models.card.Cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 10.12.2021 20:57
 * Project : ATMoracle
 */
public class CardDao extends BaseDao<Cards> {
    private static CardDao instance;
    private List<Cards> cards = new ArrayList<>();
    private FRWCard frwCard = FRWCard.getInstance();

    public static CardDao getInstance() {
        if (Objects.isNull(instance))
            instance = new CardDao();
        return instance;
    }

    {
        cards = frwCard.getAll();
    }

    public void create(Cards card) {
        cards.add(card);
        frwCard.writeAll(cards);
    }

}
