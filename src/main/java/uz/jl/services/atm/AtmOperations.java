package uz.jl.services.atm;

import uz.jl.models.atm.ATMEntity;
import uz.jl.models.card.Card;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;

import static uz.jl.utils.BaseUtils.getBig;
import static uz.jl.utils.Color.*;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 18:45
 * Project : ATMoraclechilar
 */
public class AtmOperations {
    public static void messageOn(Card card) {
        String phoneNumber=getStr("Enter phone number : ");
        card.setSmsPhoneNumber(phoneNumber);
        println(BLUE,"Sms service succesfully activated");
    }

    public static void messageOf(Card card) {
       card.setSmsPhoneNumber(null);
        println(BLUE,"Sms service succesfully deactivated");
    }

    public static void showBalance(Card card) {
        println(BLUE,"Your balance : "+card.getBalance()+" sum");
    }

    public static void withdraw(Card card, ATMEntity atm) {
        BigDecimal sum=getBig(BLUE+"Enter amount of money :");
        if(card.getBalance().compareTo(sum)>=0){
            card.setBalance(card.getBalance().subtract(sum));
            atmWithdrawMoney(atm,sum);
            Print.println(BLUE+"Please take your money");
        }else {
            println(RED,"Not enough money on your card");
        }
    }

    public static void exchangeMoney() {

    }

    public static void putMoney(Card card) {
      showBalance(card);
      BigDecimal money=getBig("Enter amount of money(sum) : ");
      card.setBalance(card.getBalance().add(money));
    }

    private static void atmWithdrawMoney(ATMEntity atm, BigDecimal sum) {
//          BigDecimal curVal1=atm.getCassette1().getCurrencyValue();
//          BigInteger n=(sum.divide(curVal1)).toBigInteger();
//          if(n.compareTo(BigInteger.valueOf(1))>=0){
//              atm.setCassette1();
//          }
    }
}
