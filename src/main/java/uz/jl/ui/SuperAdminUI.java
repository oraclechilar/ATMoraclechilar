package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.services.auth.SuperAdminService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.nio.channels.Pipe;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */

// TODO: 12/10/2021 Block da unblock qoldi xolos
public class SuperAdminUI {

    static SuperAdminService superAdminService = SuperAdminService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String username = getStr("Username: ");
        String password = getStr("Password: ");
        String phoneNumber = getStr("Phone Number: ");
        Language language = getLanguage();
        String choice = getStr("""
                1. Submit
                2. Cancel
                 ? :  """);
        if (choice.equals("2")) {
            return;
        }
        AuthUser admin = AuthUser.childBuilder().username(username).password(password).phoneNumber(phoneNumber).
                language(language).role(Role.ADMIN).status(UserStatus.ACTIVE).createdBy(Session.getInstance().getUser().getId())
                .createdAt(new Date()).childBuild();
        // TODO: 12/10/2021 Bank id
        ResponseEntity<String> response = superAdminService.create(admin);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void delete() {
        list();
        if (superAdminService.getAdmins().size() == 0) {
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response = superAdminService.delete(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void list() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no admins");
            return;
        }
        int i = 1;
        for (AuthUser admin : response.getData()) {
            Print.println(String.format("""
                    %s ->
                    Username: %s
                    Phone Number: %s """, i++, admin.getUsername(), admin.getPhoneNumber()));
        }
    }

    public static void block() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.unBlockedAdminsList();
        showUsers(response.getData());
        if (response.getData().size() == 0) {
            Print.println(Color.RED, "There is no any unblocked admins");
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response1 = superAdminService.block(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void unblock() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.blockedAdminsList();
        showUsers(response.getData());
        if (response.getData().size() == 0) {
            Print.println(Color.RED, "There is no any blocked admins");
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response1 = superAdminService.unblock(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.blockList();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no blocked admins");
            return;
        }
        int i = 1;
        for (AuthUser admin : response.getData()) {
            Print.println(String.format("""
                    %s. %s""", i++, admin.getUsername()));
        }
    }

    /**
     * @return User tanlagan tilni qaytaradi
     */
    private static Language getLanguage() {
        String langChoice = Input.getStr(String.format("""
                Please choice language -> 
                1. %s
                2. %s
                3. %s
                ? : """, Language.UZ.getName(), Language.EN.getName(), Language.RU.getName()));
        if ("1".equals(langChoice)) {
            return Language.UZ;
        } else if ("2".equals(langChoice)) {
            return Language.EN;
        } else if ("3".equals(langChoice)) {
            return Language.RU;
        } else {
            Print.println(Color.RED, "Wrong choice");
        }
        getLanguage();
        return null;
    }

    private static void showUsers(ArrayList<AuthUser> authUsers) {
        int i = 1;
        for (AuthUser authUser : authUsers) {
            Print.println(String.format("""
                    %s ->
                    Username: %s
                    Phone Number: %s """, i++, authUser.getUsername(), authUser.getPhoneNumber()));
        }
    }
}
