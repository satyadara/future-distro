package com.blibli.distro_pos.Model.item;

public class ItemType {
    private String idItem_Type;
    private String nameItem_Type;

    public ItemType() {
    }

    public ItemType(String idItem_Type, String nameItem_Type) {
        this.setId(idItem_Type);
        this.setName(nameItem_Type);
    }


    public String getId() {
        return idItem_Type;
    }

    public void setId(String idItem_Type) {
        this.idItem_Type = idItem_Type;
    }

    public String getName() {
        return nameItem_Type;
    }

    public void setName(String nameItem_Type) {
        this.nameItem_Type = nameItem_Type;
    }
}
