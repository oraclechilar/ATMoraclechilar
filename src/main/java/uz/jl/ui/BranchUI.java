package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.Status;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class BranchUI {

    static BranchService branchService = BranchService.getInstall();

    public static void create() {
        String name = Input.getStr("Branch name :");
        Branch branch = getBranch(name);
        if (Objects.nonNull(branch)) {
            Print.println("This branch is available");
            return;
        }
        branch = Branch.childBuilder().name(name).status(Status.ACTIVE).
                bankId(Session.getInstance().getUser().getBankId()).childBuild();
        ResponseEntity<String> response = BranchService.getInstall().create(branch);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode()))
            Print.println(response.getData());
    }

    public static void update() {

    }

    public static void delete() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = branchService.delete(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void list() {
        ResponseEntity<ArrayList<Branch>> response = branchService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no branch");
        }
        int i = 1;
        for (Branch branch : response.getData()) {
            Print.println(String.format("""
                            %s -> name: %s""",
                    i++, branch.getName()));
        }
    }

    public static void block() {
        list();
        String name = Input.getStr("branch name : ");
        ResponseEntity<String> response = branchService.block(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void unblock() {
        blockList();
        String str = Input.getStr("branch name : ");
        ResponseEntity<String> response = branchService.unblock(str);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(Color.RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void blockList() {
        ResponseEntity<ArrayList<Branch>> response = branchService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(Color.RED, "There are no HR");
        }
        int i = 1;
        for (Branch branch : response.getData()) {
            if (branch.getStatus().equals(Status.BLOCKED))
                Print.println(String.format("""
                                %s -> Username: %s""",
                        i++, branch.getName()));
        }
    }

    private static Branch getBranch(String name) {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getName().equalsIgnoreCase(name))
                return branch;
        }
        return null;
    }
}