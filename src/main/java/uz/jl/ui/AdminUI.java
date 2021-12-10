package uz.jl.ui;

import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

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
            user.setStatus(UserStatus.NON_ACTIVE);
            user.setRole(Role.HR);
            Print.println("This user is available");
        } else {
            FRWAuthUser.getInstance().writeAll(user);
            Print.println("succesfully");
        }
    }

    public static void delete() {
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        AuthUser user = getUser(name, pass);
        if (Objects.nonNull(user)) {
            user.setDeleted(-1);
            Print.println("Employee successfully deleted");
        } else Print.println(Color.RED, "Employee not found");
    }

    public static void list() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.HR) && user.getDeleted() != -1)
                if (!user.getStatus().equals(UserStatus.BLOCKED))
                    Print.println(user.getUsername());
                else if (user.getStatus().equals(UserStatus.BLOCKED))
                    Print.println(Color.RED, user.getUsername());
        }
    }

    public static void block() {
        list();
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        AuthUser user = getUser(name, pass);
        if (Objects.nonNull(user)) {
            user.setStatus(UserStatus.BLOCKED);
            Print.println("Successfully");
        } else Print.println("User not found");
    }

    public static void unBlock() {
        list();
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        AuthUser user = getUser(name, pass);
        if (Objects.nonNull(user)) {
            user.setStatus(UserStatus.ACTIVE);
            Print.println("Successfully");
        } else Print.println("User not found");
    }

    public static void blockList() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.HR) && user.getStatus().equals(UserStatus.BLOCKED)
                    && user.getDeleted() != -1)
                Print.println(Color.RED, user.getUsername());
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
