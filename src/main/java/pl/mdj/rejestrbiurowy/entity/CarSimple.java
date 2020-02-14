package pl.mdj.rejestrbiurowy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSimple {
    private String name;
    private String registration;
}
