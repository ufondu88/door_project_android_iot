package com.example.doorproject;

class Door {
    private String alias;
    private String door_status;
    private String latest_command;

    public Door() {
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDoor_status() {
        return door_status;
    }

    public void setDoor_status(String door_status) {
        this.door_status = door_status;
    }

    public String getLatest_command() {
        return latest_command;
    }

    public void setLatest_command(String latest_command) {
        this.latest_command = latest_command;
    }
}
