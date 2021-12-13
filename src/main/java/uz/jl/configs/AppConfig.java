package uz.jl.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.card.CardDao;
import uz.jl.dao.db.*;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.reflect.TypeToken;

/**
 * @author Elmurodov Javohir, Mon 12:21 PM. 11/29/2021
 */
public class AppConfig {
    public static Language language;

    private static final Properties properties = new Properties();


    public static void init() {
        try {
            load();
        } catch (APIException e) {
            e.printStackTrace();
        }
        language = Language.findByCode(get("bank.default.language"));
        initUsers();
        initAtms();
        initBranch();
        initBranch();
        initCards();
        initPassport();
    }

    private static void initAtms() {
        ATMDao.getInstance().atms = FRWAtm.getInstance().getAll();
    }

    private static void initBranch() {
        BranchDao.getInstance().branches = FRWBranch.getInstance().getAll();
    }

    private static void initCards() {
        CardDao.getInstance().cards = FRWCard.getInstance().getAll();
    }

    private static void initPassport() {
        PassportDao.getInstance().passports = FRWPassport.getInstance().getAll();
    }

    private static void initUsers() {
        AuthUserDao.getInstance().users = FRWAuthUser.getInstance().getAll();
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    private static void load() throws APIException {
        try {
            properties.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            throw new APIException("File not found", HttpStatus.HTTP_404);
        }
    }
}
