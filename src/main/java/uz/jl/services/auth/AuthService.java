package uz.jl.services.auth;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.Objects;

import static uz.jl.ui.menus.MenuKey.*;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AuthService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {
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

    Session session = Session.getInstance();

    public ResponseEntity<String> login(String username, String password) {
        if (!Session.getInstance().getUser().getRole().equals(Role.ANONYMOUS)) {
            return new ResponseEntity<>("Wrong option", HttpStatus.HTTP_403);
        }
        AuthUser user = repository.findByUserName(username);
        if (Objects.isNull(user) || !user.getPassword().equals(password))
            return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            return new ResponseEntity<>("This user is blocked", HttpStatus.HTTP_401);
        }
        if (Objects.nonNull(user.getBranchId())) {
            Branch branch = BranchDao.getById(user.getBranchId());
            if (branch.getStatus().equals(Status.BLOCKED)) {
                return new ResponseEntity<>("This user is blocked");
            }
        }
        user.setStatus(UserStatus.ACTIVE);
        session.setUser(user);
        return new ResponseEntity<>("success", HttpStatus.HTTP_200);
    }

    public void logout() {
        AuthUser user = session.getUser();
        user.setStatus(UserStatus.NON_ACTIVE);
        session.setUser(user);
    }

    public ResponseEntity<String> profile() {
        try {
            AuthUser authUser = session.getUser();
            println("Username : " + authUser.getUsername());
            println("Password : " + authUser.getPassword());
            println(RESU.get(session.getLanguage()) + " -> " + RESU);
            println(RESP.get(session.getLanguage()) + " -> " + RESP);
            println(RESL.get(session.getLanguage()) + " -> " + RESL);
            String choice = getStr("?:");
            MenuKey key = MenuKey.getByValue(choice);
            switch (key) {
                case RESU -> resetUsername(authUser);
                case RESP -> resetPassword(authUser);
                case RESL -> resetLanguage(authUser);
            }
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    private void resetLanguage(AuthUser authUser) {
        Language language = Language.EN;
        String langChoice = Input.getStr(String.format("""
                Please choose language -> 
                - %s
                - %s
                - %s
                ? : """, Language.UZ.getName(), Language.EN.getName(), Language.RU.getName()));
        if (langChoice.equalsIgnoreCase("uz")) {
            language = Language.UZ;
        } else if (langChoice.equalsIgnoreCase("ru")) {
            language = Language.RU;
        } else if (langChoice.equalsIgnoreCase("en")) {
            language = Language.EN;
        } else {
            Print.println(Color.RED, "Wrong choice");
            resetLanguage(authUser);
        }
        session.setLanguage(language);
    }


    private void resetUsername(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setUsername(getStr("Enter new username : "));
            return;
        }
        throw new APIException("Bad Credentials", HttpStatus.HTTP_400);
    }

    private void resetPassword(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setPassword(getStr("Enter new password : "));
            return;
        }
        throw new APIException("Bad Credentials", HttpStatus.HTTP_400);
    }
}
