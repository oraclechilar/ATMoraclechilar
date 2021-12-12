package uz.jl.ui.menus;

import uz.jl.configs.Session;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.settings.Language;
import uz.jl.utils.Print;

import java.util.LinkedHashMap;
import java.util.Map;

import static uz.jl.utils.Color.GREEN;

/**
 * @author Elmurodov Javohir, Mon 11:30 AM. 12/6/2021
 */
public class Menu {
    public static Map<String, MenuKey> menus() {
        Role role = Session.getInstance().getUser().getRole();
        UserStatus userStatus = Session.getInstance().getUser().getStatus();
        Language language = Session.getInstance().getLanguage();
        Map<String, MenuKey> menus = new LinkedHashMap<>();
        if (Role.SUPER_ADMIN.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.CBR.get(language), MenuKey.CBR);
            menus.put(MenuKey.CRAD.get(language), MenuKey.CRAD);

            menus.put(MenuKey.BLADM.get(language), MenuKey.BLADM);
            menus.put(MenuKey.BBR.get(language), MenuKey.BBR);

            menus.put(MenuKey.LISTAD.get(language), MenuKey.LISTAD);
            menus.put(MenuKey.BLISTADM.get(language), MenuKey.BLISTADM);

            menus.put(MenuKey.DELAD.get(language), MenuKey.DELAD);
            menus.put(MenuKey.DELBR.get(language), MenuKey.DELBR);

            menus.put(MenuKey.UBR.get(language), MenuKey.UBR);
        } else if (Role.ADMIN.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.CBR.get(language), MenuKey.CBR);
            menus.put(MenuKey.DELBR.get(language), MenuKey.DELBR);

            menus.put(MenuKey.UBR.get(language), MenuKey.UBR);
            menus.put(MenuKey.CHR.get(language), MenuKey.CHR);

            menus.put(MenuKey.LISTHR.get(language), MenuKey.LISTHR);
            menus.put(MenuKey.BLISTHR.get(language), MenuKey.BLISTHR);

            menus.put(MenuKey.DELHR.get(language), MenuKey.DELHR);
            menus.put(MenuKey.CRA.get(language), MenuKey.CRA);

            menus.put(MenuKey.DATM.get(language), MenuKey.DATM);
            menus.put(MenuKey.BATM.get(language), MenuKey.BATM);

            menus.put(MenuKey.UNBATM.get(language), MenuKey.UNBATM);
        } else if (role.in(Role.ADMIN, Role.HR) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.LISTEM.get(language), MenuKey.LISTEM);
            menus.put(MenuKey.BLISTEM.get(language), MenuKey.BLISTEM);

            menus.put(MenuKey.CEM.get(language), MenuKey.CEM);

            menus.put(MenuKey.DEM.get(language), MenuKey.DEM);
            menus.put(MenuKey.BLEM.get(language), MenuKey.BLEM);

            menus.put(MenuKey.UNBLEM.get(language), MenuKey.UNBLEM);
        } else if (Role.EMPLOYEE.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.BLISTATM.get(language), MenuKey.BLISTATM);
            menus.put(MenuKey.UATM.get(language), MenuKey.UATM);

            menus.put(MenuKey.LISTA.get(language), MenuKey.LISTA);
            menus.put(MenuKey.CCL.get(language), MenuKey.CCL);

            menus.put(MenuKey.LISTCL.get(language), MenuKey.LISTCL);
            menus.put(MenuKey.BLISTCL.get(language), MenuKey.BLISTCL);

            menus.put(MenuKey.DELCL.get(language), MenuKey.DELCL);
            menus.put(MenuKey.BCL.get(language), MenuKey.BCL);

            menus.put(MenuKey.UNBLC.get(language), MenuKey.UNBLC);
        } else if (Role.ANONYMOUS.equals(role) || userStatus.equals(UserStatus.NON_ACTIVE)) {
            menus.put(MenuKey.LGN.get(language), MenuKey.LGN);
            menus.put(MenuKey.ATM.get(language), MenuKey.ATM);
        }
        if (!Role.ANONYMOUS.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.LGT.get(language), MenuKey.LGT);
            menus.put(MenuKey.PR.get(language), MenuKey.PR);
        }
        menus.put(MenuKey.EXIT.get(language), MenuKey.EXIT);
        return menus;
    }

    public static void show() {
        Menu.menus().forEach((k, v) -> Print.println(GREEN, k + " -> " + v));
    }
}
