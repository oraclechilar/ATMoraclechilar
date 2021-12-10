package uz.jl.services.auth;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
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
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;

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
        try {
            AuthUser user = repository.findByUserName(username);
            if (Objects.isNull(user) || !user.getPassword().equals(password))
                return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
            user.setStatus(UserStatus.ACTIVE);
            Session.getInstance().setUser(user);
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    public void logout() {
        AuthUser user = Session.getInstance().getUser();
        user.setStatus(UserStatus.NON_ACTIVE);
        Session.getInstance().setUser(user);
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
