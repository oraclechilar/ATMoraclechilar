package uz.jl.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import uz.jl.dao.auth.AuthUserDao;
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
        language = Language.getByCode(get("bank.default.language"));
        initUsers();
    }

    private static void initUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        File file = new File("src/main/resources/db/users.json");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            AuthUserDao.getInstance().users = gson.fromJson(reader, new TypeToken<List<AuthUser>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
