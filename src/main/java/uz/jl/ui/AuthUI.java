package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.extras.Gender;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.utils.Input;

/**
 * @author Elmurodov Javohir, Wed 12:08 PM. 12/8/2021
 */
public class AuthUI extends BaseUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void login() {
        String username = Input.getStr("username = ");
        String password = Input.getStr("password = ");
        ResponseEntity<String> response = service.login(username, password);
        showResponse(response);
    }

    public static void logout() {
        service.logout();
        AuthUserDao.getInstance().writeAll();
    }

    public static void register() {
        String username = Input.getStr("username : ");
        String password = Input.getStr("password : ");
        String serial = Input.getStr("password serial :");
        String number = Input.getStr("password number : ");
        String gender = Input.getStr("gender :");
        String firstName = Input.getStr("firstname : ");
        String lastName = Input.getStr("lastname : ");
        String fatherName = Input.getStr("father name : ");
        ResponseEntity<String> response = service.register(username, password, serial,
                number, gender, firstName, lastName, fatherName);
    }

    public static void profile() {
        ResponseEntity<String> response = service.profile();
        showResponse(response);
    }
}
