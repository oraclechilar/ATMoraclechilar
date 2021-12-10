package uz.jl.services.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.ui.SuperAdminUI;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;

/**
 * @author Bakhromjon Fri, 11:21 AM 12/10/2021
 */
public class SuperAdminService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    private static SuperAdminService superAdminService;

    public static SuperAdminService getInstance(AuthUserDao authUserDao, AuthUserMapper authUserMapper) {
        if (Objects.isNull(superAdminService)) {
            superAdminService = new SuperAdminService(authUserDao, authUserMapper);
        }
        return superAdminService;
    }

    public AuthService authService = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public List<AuthUser> users = FRWAuthUser.getInstance().getAll();

    public ResponseEntity<String> create(AuthUser admin) {
        users.add(admin);
        return new ResponseEntity<>("Succesfully created", HttpStatus.HTTP_201);
    }

    public ResponseEntity<String> delete(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }

        getAdmins().get(choiceN - 1).setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> block(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        getAdmins().get(choiceN - 1).setStatus(UserStatus.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> unblock(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        getBlockedAdmins().get(choiceN - 1).setStatus(UserStatus.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<ArrayList<AuthUser>> blockList() {
        ArrayList<AuthUser> blockedUsers = new ArrayList<>();
        for (AuthUser admin : getAdmins()) {
            if (admin.getStatus().equals(UserStatus.BLOCKED) && admin.getDeleted() != -1) {
                blockedUsers.add(admin);
            }
        }
        if (blockedUsers.size() == 0) {
            return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> list() {
        if (getAdmins().size() == 0) {
            return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> blockedAdminsList() {
        if (getBlockedAdmins().size() == 0) {
            return new ResponseEntity<>(getBlockedAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getBlockedAdmins(), HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> unBlockedAdminsList() {
        if (getUnblockedAdmins().size() == 0) {
            return new ResponseEntity<>(getUnblockedAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getUnblockedAdmins(), HttpStatus.HTTP_200);
    }

    @Override
    public List<AuthUser> getAll() {
        return users;
    }

    @Override
    public ResponseEntity<String> update(String username) {
        return null;
    }

    public SuperAdminService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    /**
     * @param choice adminni raqami frontdan keladigan
     * @return -> Agar choice son bo'lmasa yoki adminlarni sonidan katta son bo'lsa, (-1) qaytadi.
     * Aks holda choiceni Integerga o'tkazib qaytaradi.
     */
    private int castRequest(String choice) {
        int choiceN;
        try {
            choiceN = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (choiceN > getAdmins().size()) {
            return -1;
        }
        return choiceN;
    }

    /**
     * @return Userlarni ichidan roli admin va delete bo'lmagan userlarni qaytaradi. Deleted(-1) Deleted bo'lamagan bo'lsa(0)
     */
    public ArrayList<AuthUser> getAdmins() {
        ArrayList<AuthUser> admins = new ArrayList<>();
        for (AuthUser authUser : users) {
            if (authUser.getRole().equals(Role.ADMIN) && authUser.getDeleted() != -1) {
                admins.add(authUser);
            }
        }
        return admins;
    }

    public ArrayList<AuthUser> getBlockedAdmins() {
        ArrayList<AuthUser> blockedAdmins = new ArrayList<>();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.ADMIN) && user.getStatus().equals(UserStatus.BLOCKED) && user.getDeleted() != -1) {
                blockedAdmins.add(user);
            }
        }
        return blockedAdmins;
    }

    public ArrayList<AuthUser> getUnblockedAdmins() {
        ArrayList<AuthUser> unBlockedAdmins = new ArrayList<>();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.ADMIN) && !user.getStatus().equals(UserStatus.BLOCKED) && user.getDeleted() != -1) {
                unBlockedAdmins.add(user);
            }
        }
        return unBlockedAdmins;
    }
}
