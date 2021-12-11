package uz.jl.utils;

import org.apache.commons.lang3.RandomStringUtils;
import uz.jl.enums.card.CardType;

/**
 * @author Elmurodov Javohir, Mon 12:13 PM. 12/6/2021
 */
public class BaseUtils {
    public static String genId() {
        return System.nanoTime() + RandomStringUtils.random(20, true, true);
    }

    public static String createPan(CardType type) {
        String pan ="";
        if(type.equals(CardType.UZCARD))
            pan+=CardType.UZCARD.getCode();
        else if(type.equals(CardType.HUMO))
            pan+=CardType.HUMO.getCode();
        else if(type.equals(CardType.UNION_PAY))
            pan+=CardType.UNION_PAY.getCode();
        else if(type.equals(CardType.VISA))
            pan+=CardType.VISA.getCode();
        return pan+RandomStringUtils.random(12, false, true);
    }
    public static Double toDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer toInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }
}
