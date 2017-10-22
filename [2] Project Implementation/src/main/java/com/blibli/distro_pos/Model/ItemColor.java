package com.blibli.distro_pos.Model;

public class ItemColor {
    private String idItem_Color;
    private String nameItem_Color;

    public ItemColor() {
    }

    public ItemColor(String idItem_Color, String nameItem_Color) {
        setIdItem_Color(idItem_Color);
        setNameItem_Color(nameItem_Color);
    }

    public String getIdItem_Color() {
        return idItem_Color;
    }

    public void setIdItem_Color(String idItem_Color) {
        this.idItem_Color = idItem_Color;
    }

    public String getNameItem_Color() {
        return nameItem_Color;
    }

    public void setNameItem_Color(String nameItem_Color) {
        this.nameItem_Color = nameItem_Color;
    }
}
