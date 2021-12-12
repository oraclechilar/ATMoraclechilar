package uz.jl.services.auth;

import uz.jl.configs.AppConfig;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.card.CardDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.card.CardType;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Card;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.ui.EmployeeUI;
import uz.jl.utils.Print;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.*;
import static uz.jl.utils.Print.println;

public class EmployeeService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {
    private static EmployeeService instance;

    protected EmployeeService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static EmployeeService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(instance))
            return new EmployeeService(repository, mapper);
        return instance;
    }

    public ResponseEntity<String> create(String username, String password, String phoneNumber) {
        AuthUser temp = repository.findByUserName(username);
        if (Objects.nonNull(temp)) {
            return new ResponseEntity<>("Username already taken!");
        }
        AuthUser user = new AuthUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(UserStatus.NON_ACTIVE);
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.CLIENT);
        user.setLanguage(AppConfig.language);
        user.setCreatedAt(new Date());
        EmployeeUI.createCard(user);
        AuthUserDao.getInstance().users.add(user);
        return new ResponseEntity<>(BLUE + "Thank you for using our bank :)");
    }

    public ResponseEntity<String> delete(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT) && !user.getStatus().equals(UserStatus.DELETED) && user.getUsername().equals(username))
                user.setStatus(UserStatus.DELETED);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Deleted!");
    }

    public ResponseEntity<String> block(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT)
                    && !user.getStatus().equals(UserStatus.DELETED)
                    && user.getUsername().equals(username)
                    && !user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.BLOCKED);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Blocked!");
    }

    public ResponseEntity<String> unblock(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT)
                    && !user.getStatus().equals(UserStatus.DELETED)
                    && user.getUsername().equals(username)
                    && user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.ACTIVE);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Unblocked!");
    }

    public ResponseEntity<String> blockList() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (user.getRole().equals(Role.CLIENT)
                    && !user.getStatus().equals(UserStatus.DELETED)
                    && user.getStatus().equals(UserStatus.BLOCKED))
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }

    public ResponseEntity<String> list() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (user.getRole().equals(Role.CLIENT)
                    && !user.getStatus().equals(UserStatus.DELETED)
                    && !user.getStatus().equals(UserStatus.BLOCKED))
                println(PURPLE, user.getUsername());
            else
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }

    public ResponseEntity<String> createCard(CardType cardType, String password, AuthUser user, String pan) {
        Card card = new Card();
        card.setStatus(Status.ACTIVE);
        card.setPassword(password);
        card.setHolderId(user.getId());
        card.setType(cardType);
        card.setPan(pan);
        card.setBankId("23edqdwwstq12tvJPOwqm1w");
        CardDao.getInstance().cards.add(card);
        Print.println("Your card number :"+pan);
        return new ResponseEntity<>("Success!");
    }
}
