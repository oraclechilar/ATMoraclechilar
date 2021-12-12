package uz.jl.enums.i18;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.models.settings.Language;

@Getter
@AllArgsConstructor
public enum Words {
    CREATE_BRANCH("create_branch", "filial yaratish", "create branch", "создать ветку"),
    UPDATE_BRANCH("update_branch", "filialni yangilash", "update branch", "обновить ветку"),
    DELETE_BRANCH("delete_branch", "filialni o'chirish", "delete branch", "удалить ветку"),
    LIST_BRANCH("list_branch", "filiallar listi", "list branch", "список веток"),
    BLOCK_BRANCH("block_branch", "filialni bloklash", "", "блокировка ветки"),
    UN_BLOCK_BRANCH("unblock_branch", "filalni blokdan chiqarish", "unblock branch", "разблокировать ветку"),
    BLOCK_LIST_BRANCH("blocked_list_branch", "bloklangan filiallar", "blocked branches", "заблокированные отделения"),
    CREATE_ATM("create_atm", "ATM yaratish", "create ATM", "создать ATM"),
    UPDATE_ATM("update_atm", "ATMni yangilash", "update ATM", "обновить ATM"),
    DELETE_ATM("delete_atm", "ATMni o'chirish", "delete ATM", "удалить ATM"),
    LIST_ATM("list_atm", "ATMlar listi", "list ATMs", "список ATM"),
    BLOCK_ATM("block_atm", "ATMni bloklash", "block ATM", "блокировка ATM"),
    UN_BLOCK_ATM("unblock_atm", "ATMni blokdan chiqarish", "unblock ATM", "разблокировать ATM"),
    BLOCK_LIST_ATM("blcoked_atm", "bbloklangan ATMlar", "blocked ATMs", "заблокированные ATM"),
    CREATE_ADMIN("create_admin", "admin yaratish", "create admin", "создать админ"),
    DELETE_ADMIN("delete_admin", "adminni o'chirish", "delete admin", "удалить админ"),
    LIST_ADMIN("list_admin", "adminlar listi", "admin list", "список администраторов"),
    BLOCK_ADMIN("block_admin", "adminni bloklash", "block admin", "блокировка админa"),
    UN_BLOCK_ADMIN("unblock_admin", "adminni unblock qilish", "unblock admin", "разблокировать админ"),
    BLOCK_LIST_ADMIN("blocked_admins", "bloklangan adminlar", "blocked admins", "заблокированные админи"),
    CREATE_HR("create_hr", "menejer yaratish", "create HR", "создать HR"),
    DELETE_HR("delete_hr", "menejerni o'chirish", "delete HR", "удалить HR"),
    LIST_HR("list_hr", "menejerlar listi", "list manager", "список HR"),
    BLOCK_HR("block_hr", "menejerni bloklash", "block HR", "блокировка HR"),
    UN_BLOCK_HR("unblock_hr", "menejerni unblock qilish", "unblock HR", "разблокировать HR"),
    BLOCK_LIST_HR("blocked_hr", "bloklangan menejerlar", "blocked HRs", "заблокированные HRs"),
    CREATE_EMPLOYEE("create_employee", "ishchi yaratish", "create employee", "создать удалить"),
    DELETE_EMPLOYEE("delete_employee", "ishchini o'chirish", "delete employee ", "удалить работник"),
    LIST_EMPLOYEE("list_employee", "ishchilar listi", "list employee", "список работник"),
    BLOCK_EMPLOYEE("block_employee", "ishchini bloklash", "block employee", "блокировка работник"),
    UN_BLOCK_EMPLOYEE("unblock_employee", "ishchini unblock qilish", "unblock employee", "разблокировать работник"),
    BLOCK_LIST_EMPLOYEE("blocked_employee", "bloklangan ishchilar", "blocked employees", "заблокированные работники"),
    CREATE_CLIENT("create_client", "mijoz yaratish", "create client", "создать клиент"),
    DELETE_CLIENT("delete_client", "mijozni o'chirish", "delete client", "удалить клиент"),
    LIST_CLIENT("list_client", "mijozlar listi", "list client", "список клиентов"),
    BLOCK_CLIENT("block_client", "mijozni bloklash", "block client", "блокировка клиента"),
    UN_BLOCK_CLIENT("unblock_client", "mijozni unblock qilish", "unblock client", "разблокировать клиент"),
    BLOCK_LIST_CLIENT("blocked_cloents", "bloklangan mijozlar", "blocked clients", "заблокированные клиенте"),
    RESET_USERNAME("reset_username", "usernameni qayta o'rnatish", "reset username", "сбросить имя пользователя"),
    RESET_PASSWORD("reset_password", "parolni qayta o'rnatish", "reset password", "сбросить пароль"),
    RESET_LANGUAGE("reset_lang", "tilni qayta o'rnatish", "reset language", "сбросить язык"),
    LOGIN("login", "Kirish", "Login", "Вход"),
    ATM_SERVICE("atm", "Atm ", "Atm", "Atm"),
    PROFILE("profile", "hisob", "profile", "профиль"),
    LOGOUT("logout", "Hisobdan chiqish", "Logout", "Выйти"),
    EXIT("exit", "Chiqish", "Exit", "Выйти"),
    UNDEFINED("", "", "", "");
    private String code;
    private String uz;
    private String en;
    private String ru;

    public String get(Language language) {
        if (language.equals(Language.RU))
            return this.ru;
        else if (language.equals(Language.UZ))
            return this.uz;
        else return this.en;
    }
}
