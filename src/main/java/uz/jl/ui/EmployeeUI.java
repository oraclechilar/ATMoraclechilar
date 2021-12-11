package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.EmployeeService;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Color.BLUE;


/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class EmployeeUI {
    static EmployeeService clientService = EmployeeService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void create() {
        String username = Input.getStr("Enter username:");
        String password = Input.getStr("Enter password:");
        String phoneNumber = Input.getStr("Enter phone number:");
        ResponseEntity<String> response = clientService.create(username, password, phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username = Input.getStr("Enter username: ");
        ResponseEntity<String> response = clientService.delete(username);
        showResponse(response);
    }

    public static void list() {
        ResponseEntity<String> response = clientService.list();
        showResponse(response);
    }

    public static void block() {
        String username = Input.getStr("Enter username: ");
        ResponseEntity<String> response = clientService.block(username);
        showResponse(response);
    }

    public static void unBlock() {
        String username = Input.getStr("Enter username: ");
        ResponseEntity<String> response = clientService.unblock(username);
        showResponse(response);
    }

    public static void blockList() {
        ResponseEntity<String> response = clientService.blockList();
        showResponse(response);
    }

    public static void createCard(AuthUser user) {
        String cardType = Input.getStr(BLUE + "Enter cardType(Uzcard/Humo/Mastercard/Visa/Cobage/UnionPay): ");
        String password = Input.getStr(BLUE + "Enter password for card: ");
        ResponseEntity<String> response = clientService.createCard(cardType, password, user);
        showResponse(response);
    }
}
