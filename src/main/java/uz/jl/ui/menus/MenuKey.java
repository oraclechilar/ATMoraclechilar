package uz.jl.ui.menus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir Mon 11:35 AM. 12/6/2021
 */

@Getter
@AllArgsConstructor
public enum MenuKey{
    CREATE_BRANCH,
    UPDATE_BRANCH,
    DELETE_BRANCH,
    LIST_BRANCH,
    BLOCK_BRANCH,
    UN_BLOCK_BRANCH,
    BLOCK_LIST_BRANCH,
    CREATE_ATM,
    UPDATE_ATM,
    DELETE_ATM,
    LIST_ATM,
    BLOCK_ATM,
    UN_BLOCK_ATM,
    BLOCK_LIST_ATM,
    CREATE_ADMIN,
    DELETE_ADMIN,
    LIST_ADMIN,
    BLOCK_ADMIN,
    UN_BLOCK_ADMIN,
    BLOCK_LIST_ADMIN,
    CREATE_HR,
    DELETE_HR,
    LIST_HR,
    BLOCK_HR,
    UN_BLOCK_HR,
    BLOCK_LIST_HR,
    CREATE_EMPLOYEE,
    DELETE_EMPLOYEE,
    LIST_EMPLOYEE,
    BLOCK_EMPLOYEE,
    UN_BLOCK_EMPLOYEE,
    BLOCK_LIST_EMPLOYEE,
    CREATE_CLIENT,
    DELETE_CLIENT,
    LIST_CLIENT,
    BLOCK_CLIENT,
    UN_BLOCK_CLIENT,
    BLOCK_LIST_CLIENT,
    RESET_USERNAME,
    RESET_PASSWORD,
    RESET_LANGUAGE,
    LOGIN,
    ATM_SERVICE,
    PROFILE,
    LOGOUT,
    EXIT,
    UNDEFINED;

    public static MenuKey getByValue(String choice) {
        for (MenuKey menu : values()) {
            if (menu.name().equalsIgnoreCase(choice)) return menu;
        }
        return UNDEFINED;
    }
}
