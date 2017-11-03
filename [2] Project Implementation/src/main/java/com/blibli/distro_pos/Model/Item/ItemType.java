package com.blibli.distro_pos.Model.item;

public class ItemType {
    private String id;
    private String name;

    public ItemType() {
    }

    public ItemType(String idItem_Type, String nameItem_Type) {
        this.setId(idItem_Type);
        this.setName(nameItem_Type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
