package uz.jl.dao.atm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.db.FRWAtm;
import uz.jl.models.atm.ATMEntity;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 11:09 AM. 12/6/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ATMDao extends BaseDao<ATMEntity> {
    FRWAtm frwAtm = FRWAtm.getInstance();

    private static ATMDao dao;

    public static ATMDao getInstance() {
        if (Objects.isNull(dao))
            dao = new ATMDao();
        return dao;
    }
}


