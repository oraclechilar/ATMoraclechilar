package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.ClientService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.ui.BaseUI.showResponse;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class HrUI {
    static ClientService clientService = ClientService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void create() {
        String username = Input.getStr("Enter username:");
        String password = Input.getStr("Enter password:");
        String phoneNumber = Input.getStr("Enter phone number:");
        AuthUser authUser = AuthUser.childBuilder().username(username).
                password(password).phoneNumber(phoneNumber).
                role(Role.CLIENT).createdBy(Session.getInstance().
                        getUser().getId()).childBuild();
        ResponseEntity<String> response = clientService.create(authUser);
        showResponse(response);
    }

    public static void delete() {
        String username = Input.getStr("Enter username: ");
        ResponseEntity<String> response = clientService.delete(username);
        showResponse(response);
    }

    public static void list() {
        List<AuthUser> list = clientService.getAll();
        for (AuthUser authUser : list) {
            Print.println(authUser.getUsername());
        }
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
        List<AuthUser> list = clientService.blockList();
        for (AuthUser authUser : list) {
            Print.println(authUser.getUsername());
        }
    }
    public static void createCard() {
        String user = Input.getStr("Enter holder name: ");
        String cardType = Input.getStr("Enter cardType: ");
        String password = Input.getStr("Enter password for card: ");
        ResponseEntity<String> response = clientService.createCard(user, cardType, password);
        showResponse(response);
    }
}
