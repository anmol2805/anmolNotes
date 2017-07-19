package com.anmol.anmolnotes;

/**
 * Created by anmol on 2017-07-03.
 */

public class Notes {
    private int id;
    private String title = "";
    private String description = "";
    public Notes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
