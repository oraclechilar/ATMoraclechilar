package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.UserStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 8:50 AM. 12/9/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDao extends BaseDao<AuthUser> {
    //region SINGLETON
    private static AuthUserDao dao;

    public static AuthUserDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AuthUserDao();
        return dao;
    }

    //endregion SINGLETON
    public ArrayList<AuthUser> users = new ArrayList<>();

    public void writeAll() {
        FRWAuthUser frwAuthUser = FRWAuthUser.getInstance();
        frwAuthUser.writeAll(users);
    }

    public AuthUser findByUserName(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (!user.getStatus().equals(UserStatus.DELETED) && user.getUsername().equals(username)) return user;
        }
        // TODO: 12/9/2021 translate please
        return null;
    }
}
