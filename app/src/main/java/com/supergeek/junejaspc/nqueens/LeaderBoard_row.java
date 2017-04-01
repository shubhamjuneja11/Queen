package com.supergeek.junejaspc.nqueens;

/**
 * Created by junejaspc on 3/13/2017.
 */

public class LeaderBoard_row {
    private String username,time;
    private int level;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private int icon;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LeaderBoard_row(String username, int level,String time,int icon) {

        this.username = username;
        this.time = time;
        this.level = level;
        this.icon=icon;
    }
}
