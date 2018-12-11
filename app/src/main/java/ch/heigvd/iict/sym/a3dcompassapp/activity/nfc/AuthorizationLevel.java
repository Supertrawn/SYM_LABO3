package ch.heigvd.iict.sym.a3dcompassapp.activity.nfc;

import java.io.Serializable;

/**
 * @Enum        : AuthorizationLevel
 * @Author(s)   : Michael Brouchoud, Thomas Lechaire & Kevin Pradervand
 * @Date        : 11.12.2018
 *
 * @Goal        : Authorization Level Enum
 *
 * @Comment(s)  : -
 *
 * @See         : Serializable
 */
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

    /**
     * @brief get the level
     * @return The level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @brief get the name
     * @return The name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName() + " : " + getLevel();
    }

    /**
     * @brief get the milliseconds level
     * @return The milliseconds level
     */
    public long getMsLevel() {
        return msLevel;
    }
}
