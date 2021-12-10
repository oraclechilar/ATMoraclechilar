package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.utils.Input;

import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class EmployeeUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void create() {String username = Input.getStr("username : ");
        String password = Input.getStr("password : ");
        String serial=Input.getStr("password serial :");
        String number=Input.getStr("password number : ");
        String gender=Input.getStr("gender :");
        String firstName=Input.getStr("firstname : ");
        String lastName=Input.getStr("lastname : ");
        String fatherName=Input.getStr("fathername : ");
        ResponseEntity<String> response=service.register(username,password,serial,
                number,gender,firstName,lastName,fatherName);
    }

    public static void delete() {

    }

    public static void list() {

    }

    public static void block() {

    }

    public static void unBlock() {

    }

    public static void blockList() {

    }
}
