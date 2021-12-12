package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.HRService;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.net.SocketOptions;
import java.util.ArrayList;

import static uz.jl.utils.Input.*;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class HrUI extends BaseUI {
    static HRService service = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        String phoneNumber = getStr("PhoneNumber: ");
        String branchID;
        if(Session.getInstance().getUser().getRole().equals(Role.ADMIN)){
            ResponseEntity<ArrayList<Branch>> branchResponse = BranchService.getInstall().list();
            if (branchResponse.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
                Print.println(Color.RED, "There is no any branch");
                return;
            }
            BranchUI.showBranch(branchResponse.getData());
            String branchChoice = getStr("Choose branch: ");
            ResponseEntity<String> branchIDResponse = BranchService.getInstall().getBranchID(branchChoice);
            if (branchIDResponse.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
                Print.println(Color.RED, branchIDResponse.getData());
            }
            branchID = branchIDResponse.getData();

        }else{
            branchID=Session.getInstance().getUser().getBranchId();
        }
        ResponseEntity<String> response = service.create(username, password, phoneNumber,branchID);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        ResponseEntity<String> response = service.delete(username, password);
        showResponse(response);
    }

    public static void list() {
        ResponseEntity<String> response = service.list();
        showResponse(response);
    }

    public static void blockList() {
        ResponseEntity<String> response = service.blockList();
        showResponse(response);
    }

    public static void block() {
        list();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.block(username);
        showResponse(response);

    }

    public static void unBlock() {
        blockList();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.unblock(username);
        showResponse(response);
    }

}
