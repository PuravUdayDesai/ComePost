package com.hack.comp.model;

import javax.validation.constraints.NotNull;

public class ProductModel
{
    @NotNull(message = "Id cannot be NULL")
    private long id;
    @NotNull(message = "Name cannot be NULL")
    private String name;
    @NotNull(message = "Quantity cannot be NULL")
    private double quantity;
    @NotNull(message = "Price cannot be NULL")
    private double price;
    @NotNull(message = "Description cannot be NULL")
    private String description;
    @NotNull(message = "Image Location cannot be NULL")
    private String img_loc;

    public ProductModel()
    {
    }

    public ProductModel(long id, String name, double quantity, double price, String description, String img_loc)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.img_loc = img_loc;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImg_loc()
    {
        return img_loc;
    }

    public void setImg_loc(String img_loc)
    {
        this.img_loc = img_loc;
    }
}
