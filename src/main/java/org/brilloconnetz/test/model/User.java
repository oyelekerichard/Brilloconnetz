package org.brilloconnetz.test.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private String password;
    private String email;
    private String dob;
}
