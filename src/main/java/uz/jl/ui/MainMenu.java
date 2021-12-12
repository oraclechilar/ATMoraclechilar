package uz.jl.ui;

import uz.jl.configs.AppConfig;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.card.CardDao;
import uz.jl.ui.menus.Menu;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Wed 11:30 AM. 12/8/2021
 */
public class MainMenu {
    static {
        AppConfig.init();
    }

    public static void run() {
        Menu.show();
        String choice = Input.getStr("?:");
        MenuKey key = MenuKey.getByValue(choice);

        switch (key) {
            case CRAD -> SuperAdminUI.create();
            case LGN -> AuthUI.login();
            case ATM -> AtmUI.menu();
            case PR -> AuthUI.profile();
            case LGT -> AuthUI.logout();

            case DELAD -> SuperAdminUI.delete();
            case LISTAD -> SuperAdminUI.list();
            case BLADM -> SuperAdminUI.block();
            case UNBLADM -> SuperAdminUI.unBlock();
            case BLISTADM -> SuperAdminUI.blockList();

            case CHR -> AdminUI.create();
            case DELHR -> AdminUI.delete();
            case LISTHR -> AdminUI.list();
            case BLHR -> AdminUI.block();
            case UNBLHR -> AdminUI.unBlock();
            case BLISTHR -> AdminUI.blockList();

            case CEM -> HrUI.create();
            case DEM -> HrUI.delete();
            case LISTEM -> HrUI.list();
            case BLEM -> HrUI.block();
            case UNBLEM -> HrUI.unBlock();
            case BLISTEM -> HrUI.blockList();

            case CCL -> EmployeeUI.create();
            case DELCL -> EmployeeUI.delete();
            case LISTCL -> EmployeeUI.list();
            case BCL -> EmployeeUI.block();
            case UNBLC -> EmployeeUI.unBlock();
            case BLISTCL -> EmployeeUI.blockList();

            case CBR -> BranchUI.create();
            case UBR -> BranchUI.update();
            case DELBR -> BranchUI.delete();
            case LBR -> BranchUI.list();
            case BBR -> BranchUI.block();
            case UNBBR -> BranchUI.unblock();
            case BLBR -> BranchUI.blockList();

            case CRA -> AtmUI.create();
            case UATM -> AtmUI.update();
            case DATM -> AtmUI.delete();
            case LISTA -> AtmUI.list();
            case BATM -> AtmUI.block();
            case UNBATM -> AtmUI.unblock();
            case BLISTATM -> AtmUI.blockList();

            case EXIT -> {
                AuthUserDao.getInstance().writeAll();
                ATMDao.getInstance().writeAll();
                BranchDao.getInstance().writeAll();
                CardDao.getInstance().writeAll();
                PassportDao.getInstance().writeAll();
                Print.println(Color.YELLOW, "Good bye");
                return;
            }
            default -> // TODO: 12/8/2021 do translations here
                    Print.println(Color.RED, "Wrong Choice");
        }
        run();
    }
}
