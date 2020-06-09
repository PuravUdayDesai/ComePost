package com.hack.comp.model.government;

import javax.validation.constraints.NotNull;

public class GovernmentDisplay
{
    @NotNull(message = "Funds cannot be NULL")
    private Double funds;
    @NotNull(message = "Name cannot be NULL")
    private String name;
    @NotNull(message = "Contact cannot be NULL")
    private String contact;
    @NotNull(message = "Email cannot be NULL")
    private String email;
    @NotNull(message = "Reg_no cannot be NULL")
    private String reg_no;

    public GovernmentDisplay()
    {

    }

    public GovernmentDisplay(@NotNull(message = "Funds cannot be NULL") Double funds, @NotNull(message = "Name cannot be NULL") String name, @NotNull(message = "Contact cannot be NULL") String contact, @NotNull(message = "Email cannot be NULL") String email, @NotNull(message = "Reg_no cannot be NULL") String reg_no)
    {
        super();
        this.funds = funds;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.reg_no = reg_no;
    }

    public Double getFunds()
    {
        return funds;
    }

    public void setFunds(Double funds)
    {
        this.funds = funds;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getReg_no()
    {
        return reg_no;
    }

    public void setReg_no(String reg_no)
    {
        this.reg_no = reg_no;
    }


}
