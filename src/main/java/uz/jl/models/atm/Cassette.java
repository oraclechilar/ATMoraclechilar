package uz.jl.models.atm;

import lombok.*;
import uz.jl.enums.atm.CassetteStatus;
import uz.jl.utils.BaseUtils;

/**
 * @author Elmurodov Javohir, Mon 11:54 AM. 11/29/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"currencyValue", "currencyCount"})
public class Cassette {
    private final String id = BaseUtils.genId();
    private String currencyValue;
    private CassetteStatus status;
    private Integer currencyCount;
    private int deleted;
}

