package com.example.junejaspc.queen;

/**
 * Created by junejaspc on 3/7/2017.
 */

public class LevelClass {
    public String getLevelno() {
        return levelno;
    }

    public void setLevelno(String levelno) {
        this.levelno = levelno;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public LevelClass(String levelno, String count) {

        this.levelno = levelno;
        this.count = count;
    }

    private String levelno,count;

}
