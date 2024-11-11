package tests.junit5.ui.stepik_auth_api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StepikAuth {
    private String email;
    private String password;
}
