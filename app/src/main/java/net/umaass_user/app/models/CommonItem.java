package net.umaass_user.app.models;

import net.umaass_user.app.interfac.ListItem;

public class CommonItem implements ListItem {
    String id;
    String name;

    public CommonItem(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getItemId() {
        return id;
    }
}
