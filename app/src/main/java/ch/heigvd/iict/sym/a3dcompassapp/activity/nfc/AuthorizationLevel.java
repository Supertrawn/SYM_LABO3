package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import java.io.Serializable;

public enum AuthorizationLevel implements Serializable {
    AUTHENTICATE_MAX(10, "MAX", 20000),
    AUTHENTICATE_MEDIUM(5, "MEDIUM", 10000),
    AUTHENTICATE_LOW(1, "LOW", 0);

    private int level;
    private String name;
    private long msLevel;

    public static final long INITIAL_TIME = 30000;

    AuthorizationLevel(int level, String name, long msLevel) {
        this.level = level;
        this.name = name;
        this.msLevel = msLevel;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName() + " : " + getLevel();
    }

    public long getMsLevel() {
        return msLevel;
    }
}
