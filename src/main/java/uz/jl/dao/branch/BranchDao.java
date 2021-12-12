package uz.jl.dao.branch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.dao.db.FRWBranch;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.branch.Branch;

import java.util.List;
import java.util.Objects;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 22:40
 * Project : ATMoraclechilar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchDao extends BaseDao<Branch> {
    private static BranchDao dao;

    public static BranchDao getInstance() {
        if (Objects.isNull(dao))
            dao = new BranchDao();
        return dao;
    }
    private final static FRWBranch frwBranch = FRWBranch.getInstance();

    public List<Branch> branches = frwBranch.getAll();

    public void writeAll() {
        frwBranch.writeAll(branches);
    }

}

