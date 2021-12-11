package uz.jl.services.auth;

import uz.jl.enums.card.CardType;

public class ExtraServices {

    public static CardType getCardType(String cardType) {
        CardType type = CardType.HUMO;
        for (CardType value : CardType.values()) {
            if (value.toString().equalsIgnoreCase(cardType)) {
                type = value;
            } else {
                return null;
            }
        }
        return type;
    }
}
