package uz.jl.services.auth;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;

import java.util.*;

public class AdminService {

    static List<AuthUser> users = FRWAuthUser.getInstance().getAll();

    public static ResponseEntity<String> create(AuthUser user) {
        users.add(user);
        FRWAuthUser.getInstance().writeAll(users);
        return new ResponseEntity<>("Succesfully created", HttpStatus.HTTP_201);
    }

    public static ResponseEntity<String> delete(String username) {
        AuthUser user = findByUsername(username);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        user.setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public static ResponseEntity<String> block(String name) {
        AuthUser user = findByUsername(name);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        user.setStatus(UserStatus.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public static ResponseEntity<String> unblock(String name) {
        AuthUser user = findByUsername(name);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        user.setStatus(UserStatus.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    private static AuthUser findByUsername(String username) {
        for (AuthUser user : getHR()) {
            if (user.getUsername().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }

    public static ResponseEntity<ArrayList<AuthUser>> list() {
        if (getHR().size() == 0) return new ResponseEntity<>(getHR(), HttpStatus.HTTP_204);
        return new ResponseEntity<>(getHR(), HttpStatus.HTTP_200);
    }

    public static ArrayList<AuthUser> getHR() {
        ArrayList<AuthUser> HRs = new ArrayList<>();
        for (AuthUser user : FRWAuthUser.getInstance().getAll()) {
            if (user.getRole().equals(Role.HR) && user.getDeleted() != -1)
                HRs.add(user);
        }
        return HRs;
    }
}
