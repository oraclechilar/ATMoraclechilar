package uz.jl.models.atm;

import lombok.*;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.models.Auditable;

/**
 * @author Elmurodov Javohir, Mon 12:10 PM. 11/29/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "type"})
public class ATMEntity extends Auditable {
    private String bankId;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;
    private Cassette cassette1;
    private Cassette cassette2;
    private Cassette cassette3;
    private Cassette cassette4;
}


