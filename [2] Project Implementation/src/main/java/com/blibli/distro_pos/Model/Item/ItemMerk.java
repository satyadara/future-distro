package com.blibli.distro_pos.Model.item;

public class ItemMerk {
    private String idItem_Merk;
    private String nameItem_Merk;

    public ItemMerk() {
    }

    public ItemMerk(String idItem_Merk, String nameItem_Merk) {
        this.setId(idItem_Merk);
        this.setName(nameItem_Merk);
    }


    public String getId() {
        return idItem_Merk;
    }

    public void setId(String idItem_Merk) {
        this.idItem_Merk = idItem_Merk;
    }

    public String getName() {
        return nameItem_Merk;
    }

    public void setName(String nameItem_Merk) {
        this.nameItem_Merk = nameItem_Merk;
    }
}
