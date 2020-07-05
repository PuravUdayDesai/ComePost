package com.hack.comp.model;

import javax.validation.constraints.NotNull;

public class LoginModel
{
    @NotNull(message = "CHECK IS NULL")
    boolean check;
    @NotNull(message = "Composter cannot be NULL")
    long composter_id;

    public LoginModel()
    {
    }

    public LoginModel(boolean check, long composter_id)
    {
        this.check = check;
        this.composter_id = composter_id;
    }

    public boolean isCheck()
    {
        return check;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }

    public long getComposter_id()
    {
        return composter_id;
    }

    public void setComposter_id(long composter_id)
    {
        this.composter_id = composter_id;
    }


}
