package uz.jl.services.branchService;

import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.Status;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BranchService {

    private static BranchService install;

    public static BranchService getInstall() {
        if (Objects.isNull(install))
            install = new BranchService();
        return install;
    }

    public List<Branch> branches = FRWBranch.getInstance().getAll();

    public ResponseEntity<String> create(Branch branch) {
        branches.add(branch);
        FRWBranch.getInstance().writeAll(branches);
        return new ResponseEntity<>("Successfully created brench", HttpStatus.HTTP_201);
    }

    public ResponseEntity<String> delete(String name) {
        Branch branch = findByName(name);
        if (Objects.isNull(branch)) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        branch.setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> block(String str) {
        Branch branch = findByName(str);
        if (Objects.isNull(branch))
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        branch.setStatus(Status.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> unblock(String str) {
        Branch branch = findByName(str);
        if (Objects.isNull(branch))
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        branch.setStatus(Status.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);

    }

    public ResponseEntity<ArrayList<Branch>> list() {
        if (getBranch().size() == 0) return new ResponseEntity<>(getBranch(), HttpStatus.HTTP_204);
        return new ResponseEntity<>(getBranch(), HttpStatus.HTTP_200);
    }

    public ArrayList<Branch> getBranch() {
        return (ArrayList<Branch>) FRWBranch.getInstance().getAll();
    }

    private Branch findByName(String name) {
        for (Branch branch : branches) {
            if (branch.getName().equalsIgnoreCase(name))
                return branch;
        }
        return null;
    }
}
