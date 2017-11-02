package com.blibli.distro_pos.Model.item;

public class ItemColor {
    private String idItem_Color;
    private String nameItem_Color;

    public ItemColor() {
    }

    public ItemColor(String idItem_Color, String nameItem_Color) {
        setId(idItem_Color);
        setName(nameItem_Color);
    }

    public String getId() {
        return idItem_Color;
    }

    public void setId(String idItem_Color) {
        this.idItem_Color = idItem_Color;
    }

    public String getName() {
        return nameItem_Color;
    }

    public void setName(String nameItem_Color) {
        this.nameItem_Color = nameItem_Color;
    }
}
