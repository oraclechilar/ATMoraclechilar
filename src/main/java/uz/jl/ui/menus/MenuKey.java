package uz.jl.ui.menus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.models.settings.Language;

/**
 * @author Elmurodov Javohir Mon 11:35 AM. 12/6/2021
 */

@Getter
@AllArgsConstructor
public enum MenuKey{
    CBR("create_branch", "filial yaratish", "create branch", "создать ветку"),
    UBR("update_branch", "filialni yangilash", "update branch", "обновить ветку"),
    DELBR("delete_branch", "filialni o'chirish", "delete branch", "удалить ветку"),
    LBR("list_branch", "filiallar listi", "list branch", "список веток"),
    BBR("block_branch", "filialni bloklash", "block branch", "блокировка ветки"),
    UNBBR("unblock_branch", "filalni blokdan chiqarish", "unblock branch", "разблокировать ветку"),
    BLBR("blocked_list_branch", "bloklangan filiallar", "blocked branches", "заблокированные отделения"),
    CRA("create_atm", "ATM yaratish", "create ATM", "создать ATM"),
    UATM("update_atm", "ATMni yangilash", "update ATM", "обновить ATM"),
    DATM("delete_atm", "ATMni o'chirish", "delete ATM", "удалить ATM"),
    LISTA("list_atm", "ATMlar listi", "list ATMs", "список ATM"),
    BATM("block_atm", "ATMni bloklash", "block ATM", "блокировка ATM"),
    UNBATM("unblock_atm", "ATMni blokdan chiqarish", "unblock ATM", "разблокировать ATM"),
    BLISTATM("blcoked_atm", "bbloklangan ATMlar", "blocked ATMs", "заблокированные ATM"),
    CRAD("create_admin", "admin yaratish", "create admin", "создать админ"),
    DELAD("delete_admin", "adminni o'chirish", "delete admin", "удалить админ"),
    LISTAD("list_admin", "adminlar listi", "admin list", "список администраторов"),
    BLADM("block_admin", "adminni bloklash", "block admin", "блокировка админa"),
    UNBLADM("unblock_admin", "adminni unblock qilish", "unblock admin", "разблокировать админ"),
    BLISTADM("blocked_admins", "bloklangan adminlar", "blocked admins", "заблокированные админи"),
    CHR("create_hr", "menejer yaratish", "create HR", "создать HR"),
    DELHR("delete_hr", "menejerni o'chirish", "delete HR", "удалить HR"),
    LISTHR("list_hr", "menejerlar listi", "list manager", "список HR"),
    BLHR("block_hr", "menejerni bloklash", "block HR", "блокировка HR"),
    UNBLHR("unblock_hr", "menejerni unblock qilish", "unblock HR", "разблокировать HR"),
    BLISTHR("blocked_hr", "bloklangan menejerlar", "blocked HRs", "заблокированные HRs"),
    CEM("create_employee", "ishchi yaratish", "create employee", "создать удалить"),
    DEM("delete_employee", "ishchini o'chirish", "delete employee ", "удалить работник"),
    LISTEM("list_employee", "ishchilar listi", "list employee", "список работник"),
    BLEM("block_employee", "ishchini bloklash", "block employee", "блокировка работник"),
    UNBLEM("unblock_employee", "ishchini unblock qilish", "unblock employee", "разблокировать работник"),
    BLISTEM("blocked_employee", "bloklangan ishchilar", "blocked employees", "заблокированные работники"),
    CCL("create_client", "mijoz yaratish", "create client", "создать клиент"),
    DELCL("delete_client", "mijozni o'chirish", "delete client", "удалить клиент"),
    LISTCL("list_client", "mijozlar listi", "list client", "список клиентов"),
    BCL("block_client", "mijozni bloklash", "block client", "блокировка клиента"),
    UNBLC("unblock_client", "mijozni unblock qilish", "unblock client", "разблокировать клиент"),
    BLISTCL("blocked_cloents", "bloklangan mijozlar", "blocked clients", "заблокированные клиенте"),
    RESU("reset_username", "usernameni qayta o'rnatish", "reset username", "сбросить имя пользователя"),
    RESP("reset_password", "parolni qayta o'rnatish", "reset password", "сбросить пароль"),
    RESL("reset_lang", "tilni qayta o'rnatish", "reset language", "сбросить язык"),
    LGN("login", "Kirish", "login", "вход"),
    ATM("", "atm xizmati", "atm service", "банкомат"),
    PR("profile", "profil", "profile", "профиль"),
    LGT("logout", "profildan chiqish", "logout", "выход"),
    EXIT("exit", "dasturni tugatish", "exit", "выход"),
    UNDEFINED("undefined", "aniqlanmagan", "undefined", "неопределенный");
    private final String code;
    private final String uz;
    private final String en;
    private final String ru;

    private static String getByCodeAndLanguage(String code, Language language) {
        for (MenuKey message : values()) {
            if (message.getCode().equals(code))
                return message.get(language);
        }
        return UNDEFINED.uz;
    }

    public String get(Language language) {
        if (language.name().equals("UZ"))
            return this.uz;
        if (language.name().equals("RU"))
            return this.ru;
        return this.en;
    }
    public static MenuKey getByValue(String choice) {
        for (MenuKey menu : values()) {
            if (menu.name().equalsIgnoreCase(choice)) return menu;
        }
        return UNDEFINED;
    }
}
