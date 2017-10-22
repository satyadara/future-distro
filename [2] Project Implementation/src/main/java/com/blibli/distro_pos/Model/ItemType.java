package com.blibli.distro_pos.Model;

public class ItemType {
    private String idItem_Type;
    private String nameItem_Type;

    public ItemType() {
    }

    public ItemType(String idItem_Type, String nameItem_Type) {
        this.setIdItem_Type(idItem_Type);
        this.setNameItem_Type(nameItem_Type);
    }


    public String getIdItem_Type() {
        return idItem_Type;
    }

    public void setIdItem_Type(String idItem_Type) {
        this.idItem_Type = idItem_Type;
    }

    public String getNameItem_Type() {
        return nameItem_Type;
    }

    public void setNameItem_Type(String nameItem_Type) {
        this.nameItem_Type = nameItem_Type;
    }
}
