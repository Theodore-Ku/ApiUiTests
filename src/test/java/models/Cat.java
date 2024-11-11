package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Cat {
    private String name;
    private String model;
    private Integer age;
    private Boolean isWhite;

}
