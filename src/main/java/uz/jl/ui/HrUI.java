package uz.jl.ui;

import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.user.HRService;

import static uz.jl.utils.Input.*;
import static uz.jl.utils.Print.*;
import static uz.jl.utils.BaseUtil.*;
import static uz.jl.utils.Color.*;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class HrUI extends BaseUI {
    static HRService service = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        String phoneNumber = getStr("PhoneNumber: ");
        ResponseEntity<String> response = service.create(username, password, phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        ResponseEntity<String> response = service.delete(username, password);
        showResponse(response);
    }

    public static void list() {
        ResponseEntity<String> response = service.list();
        showResponse(response);
    }

    public static void block() {
        list();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.block(username);
        showResponse(response);

    }

    public static void unBlock() {
        blockList();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.unblock(username);
        showResponse(response);
    }

    public static void blockList() {
        ResponseEntity<String> response = service.blockList();
        showResponse(response);
    }
}
