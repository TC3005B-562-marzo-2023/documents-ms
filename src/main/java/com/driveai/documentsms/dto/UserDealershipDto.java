package com.driveai.documentsms.dto;

import java.util.Date;
import java.util.List;

public class UserDealershipDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String user_type;
    private Integer dealershipId;
    private List<Integer> dealershipsList;

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getEmail() { return email; }

    public String getUser_type() {
        return user_type;
    }

    public Integer getDealershipId() { return dealershipId; }

    public List<Integer> getDealershipsList() { return dealershipsList; }
}