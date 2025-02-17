package org.example;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Stats {

    private Integer minValue;
    private Integer maxValue;
    private Integer avgValue;

}
