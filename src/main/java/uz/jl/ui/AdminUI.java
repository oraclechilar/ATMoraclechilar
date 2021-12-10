package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AdminService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class AdminUI {

    public static void create() {
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        AuthUser user = getUser(name, pass);
        if (Objects.nonNull(user)) {
            Print.println("This user is available");
            return;
        }
        user = AuthUser.childBuilder().username(name).password(pass).role(Role.HR)
                .status(UserStatus.ACTIVE).createdBy(Session.getInstance().getUser().getUsername()).childBuild();
        ResponseEntity<String> response = AdminService.create(user);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void delete() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = AdminService.delete(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void list() {
        ResponseEntity<ArrayList<AuthUser>> response = AdminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no HR");
        }
        int i = 1;
        for (AuthUser hr : response.getData()) {
            Print.println(String.format("""
                            %s -> Username: %s""",
                    i++, hr.getUsername()));
        }
    }

    public static void block() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = AdminService.block(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void unBlock() {
        blockList();
        String name = Input.getStr("username :");
        ResponseEntity<String> response = AdminService.unblock(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = AdminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no HR");
        }
        int i = 1;
        for (AuthUser hr : response.getData()) {
            if (hr.getStatus().equals(UserStatus.BLOCKED))
                Print.println(String.format("""
                                %s -> Username: %s""",
                        i++, hr.getUsername()));
        }
    }

    private static AuthUser getUser(String name, String pass) {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getUsername().equalsIgnoreCase(name) && user.getPassword().equalsIgnoreCase(pass))
                return user;
        }
        return null;
    }
}
