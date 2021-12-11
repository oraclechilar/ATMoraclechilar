package uz.jl.ui;

import uz.jl.configs.AppConfig;
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
import java.util.List;

import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */
public class SuperAdminUI {

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
                language(language).role(Role.ADMIN).status(UserStatus.ACTIVE).createdBy("-1").childBuild();
        // TODO: 12/10/2021 Bank id
        ResponseEntity<String> response = SuperAdminService.create(admin);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void delete() {
        list();
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response = SuperAdminService.delete(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void list() {
        ResponseEntity<ArrayList<AuthUser>> response = SuperAdminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no admins");
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
        list();
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response = SuperAdminService.block(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void unblock() {
        list();
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response = SuperAdminService.unblock(choice);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(Color.RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = SuperAdminService.blockList();
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
     *  @return User tanlagan tilni qaytaradi
     */
    private static Language getLanguage() {
        String langChoice = Input.getStr(String.format("""
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
}
