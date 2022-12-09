package com.ebn.calendar.model.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TagCRUDRequest {
    @NotNull(message = "'name' is mandatory")
    @Size(max = 20, message = "'name' should have a length of maximum 20 character")
    private String name;

    @NotNull(message = "'colorCode' is mandatory")
    @Pattern(regexp="^[0-9A-F]{6}$", message="'colorCode' should be in HEX format")
    private String colorCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
