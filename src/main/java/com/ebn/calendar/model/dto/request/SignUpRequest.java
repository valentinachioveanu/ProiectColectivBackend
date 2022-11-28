package com.ebn.calendar.model.dto.request;

import javax.validation.constraints.*;

public class SignUpRequest {
    @NotNull(message = "'username' is mandatory")
    @Size(min = 3, max = 20, message = "'username' should have a length of 3 to 20 character")
    private String username;

    @NotNull(message = "'password' is mandatory")
    @Size(min = 6, max = 40, message = "'password' should have a length of 6 to 40 character")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
