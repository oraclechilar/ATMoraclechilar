package uz.jl.services.atm;

import uz.jl.models.card.Card;

import java.math.BigDecimal;

import static uz.jl.utils.BaseUtils.getBig;
import static uz.jl.utils.Color.BLUE;
import static uz.jl.utils.Color.PURPLE;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 18:45
 * Project : ATMoraclechilar
 */
public class AtmOperations {
    public static void messageOn() {

    }

    public static void messageOf() {
    }

    public static void showBalance(Card card) {
        println(BLUE,"Your balance : "+card.getBalance()+" sum");
    }

    public static void withdraw() {
    }
    public static void exchangeMoney() {

    }

    public static void putMoney(Card card) {
      showBalance(card);
      BigDecimal money=getBig("Enter amount of money(sum) : ");
      card.setBalance(card.getBalance().add(money));
    }
}
