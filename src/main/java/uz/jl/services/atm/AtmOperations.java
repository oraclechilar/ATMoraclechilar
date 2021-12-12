package uz.jl.services.atm;

import uz.jl.models.atm.ATMEntity;
import uz.jl.models.atm.Cassette;
import uz.jl.models.card.Card;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;

import static uz.jl.ui.AtmUI.uzMenuAtm;
import static uz.jl.utils.BaseUtils.getBig;
import static uz.jl.utils.BaseUtils.getBigInt;
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
        BigInteger sum=getBigInt(BLUE+"Enter amount of money :");
        if(card.getBalance().compareTo(sum)>=0){
            println("Please get money ");
            BigInteger sum1=atmWithdrawMoney(atm,sum,card);
            if(!sum1.equals(BigInteger.ZERO)){
                Print.println("You can withdraw only "+ sum.subtract(sum1));
            }
            card.setBalance(card.getBalance().subtract(sum.subtract(sum1)));
        }else {
            println(RED,"Not enough money on your card");
        }
    }

    public static void exchangeMoney() {

    }

    public static void putMoney(Card card,ATMEntity atm) {
      showBalance(card);
      BigInteger money=getBigInt("Enter currency value : ");
      card.setBalance(card.getBalance().add(money));
    }

    private static BigInteger atmWithdrawMoney(ATMEntity atm, BigInteger sum,Card card) {
          sum=workingCassete1(atm,sum);
          sum=workingCassete2(atm,sum);
          sum=workingCassete3(atm,sum);
          sum=workingCassete4(atm,sum);
          return sum;
    }

    private static BigInteger workingCassete1(ATMEntity atm, BigInteger sum){
        Cassette cassette1=atm.getCassette1();
        BigInteger curVal=cassette1.getCurrencyValue();
        BigInteger curCount=cassette1.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette1.setCurrencyCount(curCount.subtract(n));
            println(curVal +" -> "+ n );
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete2(ATMEntity atm, BigInteger sum) {
        Cassette cassette2= atm.getCassette2();
        BigInteger curVal=cassette2.getCurrencyValue();
        BigInteger curCount=cassette2.getCurrencyCount();

        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette2.setCurrencyCount(curCount.subtract(n));
            println(curVal +" -> "+ n );
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete3(ATMEntity atm, BigInteger sum) {
        Cassette cassette3= atm.getCassette3();
        BigInteger curVal=cassette3.getCurrencyValue();
        BigInteger curCount=cassette3.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette3.setCurrencyCount(curCount.subtract(n));
            println(curVal +" -> "+ n );
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete4(ATMEntity atm, BigInteger sum) {
        Cassette cassette4= atm.getCassette4();
        BigInteger curVal=cassette4.getCurrencyValue();
        BigInteger curCount=cassette4.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette4.setCurrencyCount(curCount.subtract(n));
            println(curVal +" -> "+ n );
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }
}
