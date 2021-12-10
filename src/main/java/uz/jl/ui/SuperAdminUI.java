package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.utils.Input;

import java.security.Provider;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */
public class SuperAdminUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void create() {
        String username = Input.getStr("username = ");
        String password = Input.getStr("password = ");


    }

    public static void delete() {

    }

    public static void list() {

    }

    public static void block() {

    }

    public static void unblock() {

    }

    public static void blockList() {

    }
}
