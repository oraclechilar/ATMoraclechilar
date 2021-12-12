package uz.jl.enums.i18;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Elmurodov Javohir, Mon 12:19 PM. 11/29/2021
 */
@Getter
@AllArgsConstructor
public enum Locale {
    UZ("uz"), RU("ru"), EN("en");
    String code;
}
