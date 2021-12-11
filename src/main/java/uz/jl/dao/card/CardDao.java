package uz.jl.dao.card;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.dao.db.FRWCard;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.card.Card;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDao extends BaseDao<Card> {
    private static CardDao dao;

    public static CardDao getInstance() {
        if (Objects.isNull(dao))
            dao = new CardDao();
        return dao;
    }
    private static FRWCard frwcard = FRWCard.getInstance();

    public List<Card> cards = frwcard.getAll();

    public void writeAll() {
        frwcard.writeAll(cards);
    }

}
