package com.blibli.distro_pos.Model.item;

public class ItemMerk {
    private String id;
    private String name;

    public ItemMerk() {
    }

    public ItemMerk(String idItem_Merk, String nameItem_Merk) {
        this.setId(idItem_Merk);
        this.setName(nameItem_Merk);
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
