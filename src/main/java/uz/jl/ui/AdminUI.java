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

    static AdminService adminService = new AdminService().getInstall();

    public static void create() {
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        String phonNumber = Input.getStr("Phone number :");
        AuthUser user = AuthUser.childBuilder()
                .username(name)
                .password(pass)
                .phoneNumber(phonNumber)
                .role(Role.HR)
                .status(UserStatus.ACTIVE)
                .childBuild();
        ResponseEntity<String> response = adminService.create(user);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    public static void delete() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = adminService.delete(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void list() {
        ResponseEntity<ArrayList<AuthUser>> response = adminService.list();
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
        ResponseEntity<String> response = adminService.block(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void unBlock() {
        list();
        String name = Input.getStr("name -> :");
        ResponseEntity<String> response = adminService.unblock(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = adminService.blockList();
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

}


