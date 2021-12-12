package uz.jl.ui;

import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.enums.atm.CassetteStatus;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.atm.Cassette;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmOperations;
import uz.jl.services.atm.AtmService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class AtmUI extends BaseUI{
    static AtmService service = AtmService.getInstance(ATMDao.getInstance(), ATMMapper.getInstance());

    public static void menu() {
        String name = getStr("Atm name =");
        ATMEntity atm = service.get(name);
        if (Objects.isNull(atm)) {
            Print.println(Color.RED, "ATM Not found");
            return;
        }
        if (atm.getType().equals(ATMType.UZCARD) || atm.getType().equals(ATMType.HUMO)) {
            uzMenu();
        }
        if (atm.getType().equals(ATMType.VISA)) {
            enMenu();
        }
    }

    public static void create() {
        String name = getStr("name = ");
        ATMEntity atm = service.get(name);
        if (Objects.nonNull(atm)) {
            Print.println(Color.RED, "Atm name is available");
            return;
        }
        ATMType.listType();

        String type = getStr("type = ");
        ATMType atmType = ATMType.get(type);
        if (atmType.equals(ATMType.UNDEFINED)) {
            create();
            return;
        }

        String latitude = getStr("latitude = ");
        Double aDouble = BaseUtils.toDouble(latitude);
        if (Objects.isNull(aDouble)) {
            create();
            return;
        }

        String longitude = getStr("longitude = ");
        Double bDouble = BaseUtils.toDouble(longitude);
        if (Objects.isNull(bDouble)) {
            create();
            return;
        }

        Cassette cassette1;
        Cassette cassette2;
        Cassette cassette3;
        Cassette cassette4;

        if (atmType.equals(ATMType.UZCARD) || atmType.equals(ATMType.HUMO)) {
            cassette1 = new Cassette("100000", CassetteStatus.ACTIVE, 100, 0);
            cassette2 = new Cassette("50000", CassetteStatus.ACTIVE, 100, 0);
            cassette3 = new Cassette("10000", CassetteStatus.ACTIVE, 100, 0);
            cassette4 = new Cassette("5000", CassetteStatus.ACTIVE, 1000, 0);
        } else {
            cassette1 = new Cassette("100", CassetteStatus.ACTIVE, 100, 0);
            cassette2 = new Cassette("100000", CassetteStatus.ACTIVE, 100, 0);
            cassette3 = new Cassette("50000", CassetteStatus.ACTIVE, 100, 0);
            cassette4 = new Cassette("10000", CassetteStatus.ACTIVE, 100, 0);
        }

        ATMEntity newAtm = new ATMEntity("qw1er2ty3ui4op5", atmType, name, ATMStatus.ACTIVE, aDouble, bDouble, cassette1, cassette2, cassette3, cassette4);
        newAtm.setId(BaseUtils.genId());
        ResponseEntity<String> atmEntityResponseEntity = service.create(newAtm);
        showResponse(atmEntityResponseEntity);
    }

    public static void update() {
        String name = getStr("name = ");
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1) if (atm.getName().equalsIgnoreCase(name)) {
                ResponseEntity<String> update = service.update(atm);
                FRWAtm.getInstance().writeAll(all);
                showResponse(update);
            }
        }
    }

    public static void delete() {
        list();
        String name = getStr("name = ");
        ResponseEntity<String> delete = service.delete(name);
        showResponse(delete);
    }

    public static void list() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1 && !atm.getStatus().equals(ATMStatus.BLOCKED))
                Print.println(Color.CYAN, atm);
        }
    }

    public static void block() {
        list();
        String name = getStr("name = ");
        ResponseEntity<String> block = service.block(name);
        showResponse(block);
    }

    public static void unblock() {
        blockList();
        String name = getStr("name = ");
        ResponseEntity<String> unBlock = service.unBlock(name);
        showResponse(unBlock);
    }

    public static void blockList() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1 && atm.getStatus().equals(ATMStatus.BLOCKED)) Print.println(Color.RED, atm);
        }
    }

    public static Integer updateMenu() {
        Print.println(Color.YELLOW, "1. Name");
        Print.println(Color.YELLOW, "2. Latitude");
        Print.println(Color.YELLOW, "3. Longitude");
        Print.println(Color.YELLOW, "4. cassette1");
        Print.println(Color.YELLOW, "5. cassette2");
        Print.println(Color.YELLOW, "6. cassette3");
        Print.println(Color.YELLOW, "7. cassette4");
        String choice = getStr("choice menu = ");
        Integer integer = BaseUtils.toInteger(choice);
        if (Objects.isNull(integer)) {
            return updateMenu();
        }
        if (integer < 1 || integer > 7) {
            return updateMenu();
        }
        return integer;
    }

    public static void showAll() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1 && atm.getStatus().equals(ATMStatus.BLOCKED)) Print.println(Color.RED, atm);
            else Print.println(Color.CYAN, atm);
        }
    }

    private static void uzMenu() {
        Print.println(Color.PURPLE, "1. Turn the sms-service on");
        Print.println(Color.PURPLE, "2. Turn the sms-service of");
        Print.println(Color.PURPLE, "3. Show balance");
        Print.println(Color.PURPLE, "4. Cash");
        Print.println(Color.PURPLE, "5. Home");
        String choice = getStr("choice menu =");
        switch (choice) {
            case "1" -> AtmOperations.messageOn();
            case "2" -> AtmOperations.messageOf();
            case "3" -> AtmOperations.showBalance();
            case "4" -> AtmOperations.cash();
            case "5" -> {
                Print.println(Color.GREEN, "Home");
                return;
            }
            default -> {
                Print.println(Color.RED, "Wrong menu");
                return;
            }
        }
        uzMenu();
    }

    private static void enMenu() {
        Print.println(Color.PURPLE, "1. Turn the sms-service on");
        Print.println(Color.PURPLE, "2. Turn the sms-service of");
        Print.println(Color.PURPLE, "3. Show balance");
        Print.println(Color.PURPLE, "4. Cash");
        Print.println(Color.PURPLE, "5. Exchange money");
        Print.println(Color.PURPLE, "6. Home");
        String choice = getStr("choice menu =");
        switch (choice) {
            case "1" -> AtmOperations.messageOn();
            case "2" -> AtmOperations.messageOf();
            case "3" -> AtmOperations.showBalance();
            case "4" -> AtmOperations.cash();
            case "5" -> AtmOperations.exchangeMoney();
            case "6" -> {
                Print.println(Color.GREEN, "Home");
                return;
            }
            default -> {
                Print.println(Color.RED, "Wrong menu");
                return;
            }
        }
        enMenu();
    }
}