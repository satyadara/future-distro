package com.blibli.distro_pos.Model.item;

public class ItemMerk {
    private String idItem_Merk;
    private String nameItem_Merk;

    public ItemMerk() {
    }

    public ItemMerk(String idItem_Merk, String nameItem_Merk) {
        this.setIdItem_Merk(idItem_Merk);
        this.setNameItem_Merk(nameItem_Merk);
    }


    public String getIdItem_Merk() {
        return idItem_Merk;
    }

    public void setIdItem_Merk(String idItem_Merk) {
        this.idItem_Merk = idItem_Merk;
    }

    public String getNameItem_Merk() {
        return nameItem_Merk;
    }

    public void setNameItem_Merk(String nameItem_Merk) {
        this.nameItem_Merk = nameItem_Merk;
    }
}
