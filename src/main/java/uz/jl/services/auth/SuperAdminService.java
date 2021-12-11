package uz.jl.services.auth;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.ui.SuperAdminUI;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static uz.jl.utils.Input.getStr;

/**
 * @author Bakhromjon Fri, 11:21 AM 12/10/2021
 */
public class SuperAdminService {
    public static AuthService authService = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    static List<AuthUser> users = authService.getAll();

    public static ResponseEntity<String> create(AuthUser admin) {
        users.add(admin);
        return new ResponseEntity<>("Succesfully created", HttpStatus.HTTP_201);
    }

    public static ResponseEntity<String> delete(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        users.remove(choiceN);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public static ResponseEntity<String> block(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        users.get(choiceN).setStatus(UserStatus.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public static ResponseEntity<String> unblock(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        users.get(choiceN).setStatus(UserStatus.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public static ResponseEntity<ArrayList<AuthUser>> blockList() {
        ArrayList<AuthUser> blockedUsers = new ArrayList<>();
        for (AuthUser admin : getAdmins()) {
            if (admin.getStatus().equals(UserStatus.BLOCKED)) {
                blockedUsers.add(admin);
            }
        }
        if (blockedUsers.size() == 0) {
            return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_200);
    }

    public static ResponseEntity<ArrayList<AuthUser>> list() {
        if (getAdmins().size() == 0) {
            return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_200);
    }

    /**
     * @param choice  adminni raqami frontdan keladigan
     * @return -> Agar choice son bo'lmasa yoki adminlarni sonidan katta son bo'lsa, (-1) qaytadi.
     * Aks holda choiceni Integerga o'tkazib qaytaradi.
     */
    private static int castRequest(String choice) {
        int choiceN;
        try {
            choiceN = Integer.parseInt(choice);
        } catch (InputMismatchException e) {
            return -1;
        }
        if (choiceN > getAdmins().size()) {
            return -1;
        }
        return choiceN;
    }

    public static ArrayList<AuthUser> getAdmins() {
        ArrayList<AuthUser> admins = new ArrayList<>();
        for (AuthUser authUser : users) {
            if (authUser.getRole().equals(Role.ADMIN)) {
                admins.add(authUser);
            }
        }
        return admins;
    }
}
