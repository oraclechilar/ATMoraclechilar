package uz.jl.services.auth;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.ui.menus.MenuKey;

import java.util.List;
import java.util.Objects;

import static uz.jl.ui.menus.MenuKey.RESET_PASSWORD;
import static uz.jl.ui.menus.MenuKey.RESET_USERNAME;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AuthService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {
    private static AuthService service;

    public static AuthService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AuthService(repository, mapper);
        }
        return service;
    }

    protected AuthService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> login(String username, String password) {
        AuthUser user = repository.findByUserName(username);
        if (Objects.isNull(user) || !user.getPassword().equals(password))
            return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
        user.setStatus(UserStatus.ACTIVE);
        Session.getInstance().setUser(user);
        return new ResponseEntity<>("success", HttpStatus.HTTP_200);
    }

    public void logout() {
        AuthUser user = Session.getInstance().getUser();
        user.setStatus(UserStatus.NON_ACTIVE);
        Session.getInstance().setUser(user);
    }

    public ResponseEntity<String> profile() {
        try {
            AuthUser authUser = Session.getInstance().getUser();
            println("Username : " + authUser.getUsername());
            println("Password : ******* ");
            println("Reset username" + " -> " + RESET_USERNAME);
            println("Reset password" + " -> " + RESET_PASSWORD);
            String choice = getStr("?:");
            MenuKey key = MenuKey.getByValue(choice);
            switch (key) {
                case RESET_USERNAME -> resetUsername(authUser);
                case RESET_PASSWORD -> resetPassword(authUser);
            }
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    private void resetUsername(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setUsername(getStr("Enter new username : "));
            return;
        }
        throw new APIException("Bad crediantials", HttpStatus.HTTP_400);
    }

    private void resetPassword(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setUsername(getStr("Enter new password : "));
            return;
        }
        throw new APIException("Bad crediantials", HttpStatus.HTTP_400);
    }

    public ResponseEntity<String> register(String username, String password, String serial, String number, String gender, String firstName, String lastname, String fathername) {

        return new ResponseEntity<>();
    }


    @Override
    public void create(AuthUser authUser) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public AuthUser get(String id) {
        return null;
    }

    @Override
    public List<AuthUser> getAll() {
        return null;
    }

    @Override
    public void update(String id, AuthUser authUser) {

    }
}
