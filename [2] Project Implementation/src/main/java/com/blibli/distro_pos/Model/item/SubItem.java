package com.blibli.distro_pos.Model.item;

public class SubItem {
    private String id;
    private String name;

    public SubItem() {
    }

    public SubItem(String idItem_Color, String nameItem_Color) {
        setId(idItem_Color);
        setName(nameItem_Color);
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
