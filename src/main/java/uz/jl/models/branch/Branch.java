package uz.jl.models.branch;

import lombok.Getter;
import lombok.Setter;
import uz.jl.enums.atm.Status;
import uz.jl.models.Auditable;

@Getter
@Setter
public class Branch extends Auditable {
    private String name;
    private Status status;
}
