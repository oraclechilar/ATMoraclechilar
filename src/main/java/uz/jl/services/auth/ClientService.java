package uz.jl.services.auth;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.card.CardDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.card.CardType;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Cards;
import uz.jl.response.Data;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 10.12.2021 19:37
 * Project : ATMoracle
 */
public class ClientService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {
    private static ClientService instance;

    protected ClientService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static ClientService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(instance))
            return new ClientService(repository, mapper);
        return instance;
    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        int userIndex = AuthUserDao.getInstance().findByUserName(authUser);

        if (userIndex != -1) {
            return new ResponseEntity<>("This username was taken", HttpStatus.HTTP_205);
        }

        AuthUserDao.getInstance().create(authUser);
        return new ResponseEntity<>("Created", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> delete(String username) {
        try {
            AuthUser authUser = AuthUserDao.getInstance().findByUserName(username);
            int index = AuthUserDao.getInstance().findByUserName(authUser);
            AuthUserDao.getInstance().delete(index);
            return new ResponseEntity<>("Deleted", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    @Override
    public List<AuthUser> getAll() {
        List<AuthUser> list = new ArrayList<>();
        AuthUser session = Session.getInstance().getUser();
        for (AuthUser authUser : AuthUserDao.getInstance().list()) {
            if (authUser.getRole().equals(Role.CLIENT))
                list.add(authUser);
        }
        return list;
    }

    @Override
    public ResponseEntity<String> update(String userName) {
        try {
            AuthUser byUserName = AuthUserDao.getInstance().findByUserName(userName);
            if (Objects.isNull(byUserName))
                return new ResponseEntity<>("Not found", HttpStatus.HTTP_404);
            int index = AuthUserDao.getInstance().findByUserName(byUserName);
            AuthUser authUser = updateData(byUserName);
            AuthUserDao.getInstance().update(index, authUser);
            return new ResponseEntity<>("Ok", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    private AuthUser updateData(AuthUser byUserName) {
        String index = Input.getStr("Update client\n 1. Username 2. Password 3. PhoneNumber -> 1/2/3 : ");
        if (index.equals("1")) {
            String username = Input.getStr("New UserName: ");
            byUserName.setUsername(username);
        } else if (index.equals("2")) {
            String password = Input.getStr("New Password: ");
            byUserName.setPassword(password);
        } else if (index.equals("3")) {
            String phoneNumber = Input.getStr("New phoneNumber: ");
            byUserName.setPhoneNumber(phoneNumber);
        } else {
            ResponseEntity<String> response = new ResponseEntity<>("Bad request", HttpStatus.HTTP_400);
            Print.println(Color.RED, response.getData());
            return updateData(byUserName);
        }
        return byUserName;
    }

    @Override
    public ResponseEntity<String> block(String username) {
        try {
            AuthUser authUser = AuthUserDao.getInstance().findByUserName(username);
            if (authUser.getStatus().equals(UserStatus.BLOCKED))
                return new ResponseEntity<>("Not Changed", HttpStatus.HTTP_400);
            int index = AuthUserDao.getInstance().findByUserName(authUser);
            AuthUserDao.getInstance().block(index);
            return new ResponseEntity<>("Blocked", HttpStatus.HTTP_200);

        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    @Override
    public ResponseEntity<String> unblock(String username) {
        try {
            AuthUser authUser = AuthUserDao.getInstance().findByUserName(username);
            if (authUser.getStatus().equals(UserStatus.ACTIVE))
                return new ResponseEntity<>("Not Changed", HttpStatus.HTTP_400);
            int index = AuthUserDao.getInstance().findByUserName(authUser);
            AuthUserDao.getInstance().unBlock(index);
            return new ResponseEntity<>("UnBlocked", HttpStatus.HTTP_200);

        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    public List<AuthUser> blockList() {
        List<AuthUser> list = new ArrayList<>();
        for (AuthUser authUser : getAll()) {
            if (authUser.getStatus().equals(UserStatus.BLOCKED))
                list.add(authUser);
        }
        return list;
    }

    public ResponseEntity<String> createCard(String username, String type, String password) {
        try {
            CardType typeCard = CardType.getType(type);
            if (Objects.isNull(typeCard)) return new ResponseEntity<>("Bad request", HttpStatus.HTTP_400);

            AuthUser authUser = AuthUserDao.getInstance().findByUserName(username);
            String pan = BaseUtils.createPan(typeCard);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM yyyy");
            String expiry = LocalDateTime.now().format(formatter);
            Cards card = Cards.builder().pan(pan).expiry(expiry).
                    holderId(authUser.getId()).type(typeCard).
                    status(Status.ACTIVE).build();
            CardDao.getInstance().create(card);
            return new ResponseEntity<>("Ok", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

}
