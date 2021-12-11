package uz.jl.services.auth;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.UserStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;

import static uz.jl.utils.Color.*;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.*;

import java.util.Objects;

public class HRService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {
    private static HRService service;

    public static HRService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new HRService(repository, mapper);
        }
        return service;
    }

    public HRService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String username, String password, String phoneNumber) {
        AuthUser user = repository.findByUserName(username);
        if (Objects.nonNull(user)) {
            return new ResponseEntity<>("Username already taken!");
        }
        AuthUser user1 = new AuthUser();
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setStatus(UserStatus.NON_ACTIVE);
        user1.setPhoneNumber(phoneNumber);
        AuthUserDao.getInstance().users.add(user1);
        AuthUserDao.getInstance().writeAll();
        return new ResponseEntity<>(BLUE + "Created!");
    }

    public ResponseEntity<String> list() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (!user.getStatus().equals(UserStatus.BLOCKED))
                println(PURPLE, user.getUsername());
            else
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }

    public ResponseEntity<String> delete(String username, String password) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (!user.getStatus().equals(UserStatus.DELETED) && user.getUsername().equals(username)
                    && user.getPassword().equals(password))
                user.setStatus(UserStatus.DELETED);
            else
                return new ResponseEntity<>(RED + "User not found!");
        }
        return new ResponseEntity<>(GREEN + "Deleted!");
    }

    public ResponseEntity<String> block(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (!user.getStatus().equals(UserStatus.DELETED) && user.getUsername().equals(username) &&
                    !user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.BLOCKED);
            else
                return new ResponseEntity<>(RED + "User not found!");
        }
        return new ResponseEntity<>(GREEN + "Blocked!");
    }

    public ResponseEntity<String> unblock(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (!user.getStatus().equals(UserStatus.DELETED) && user.getUsername().equals(username) &&
                    user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.BLOCKED);
            else
                return new ResponseEntity<>(RED + "User not found!");
        }
        return new ResponseEntity<>(GREEN + "Unblocked!");
    }

    public ResponseEntity<String> blockList() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (user.getStatus().equals(UserStatus.BLOCKED))
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }
}
