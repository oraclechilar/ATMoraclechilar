package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.IBaseDao;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 8:50 AM. 12/9/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDao extends BaseDao<AuthUser> implements IBaseDao<AuthUser> {
    FRWAuthUser frwAuthUser = FRWAuthUser.getInstance();
    List<AuthUser> userList = new ArrayList<>();

    private static AuthUserDao dao;

    public static AuthUserDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AuthUserDao();
        return dao;
    }

    {
        userList = frwAuthUser.getAll();
    }

    public AuthUser findByUserName(String username) throws APIException {
        for (AuthUser user : frwAuthUser.getAll()) {
            if (user.getUsername().equals(username)) return user;
        }
        // TODO: 12/9/2021 translate please
        throw new APIException("User Not Found Exception", HttpStatus.HTTP_404);
    }
    public int findByUserName(AuthUser authUser) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().
                    equalsIgnoreCase(authUser.getUsername()))
                return i;
        }
        return -1;
    }


    @Override
    public void create(AuthUser obj) {
        userList.add(obj);
    }

    @Override
    public void delete(int index) {
        userList.get(index).setDeleted(1);
        frwAuthUser.writeAll(userList);
    }

    @Override
    public List<AuthUser> list() {
        return userList;
    }

    @Override
    public void update(int index, AuthUser obj) {
        userList.set(index, obj);
        frwAuthUser.writeAll(userList);
    }

    @Override
    public void block(int index) {
        userList.get(index).setStatus(UserStatus.BLOCKED);
        frwAuthUser.writeAll(userList);
    }

    @Override
    public void unBlock(int index) {
        userList.get(index).setStatus(UserStatus.ACTIVE);
        frwAuthUser.writeAll(userList);
    }
}
