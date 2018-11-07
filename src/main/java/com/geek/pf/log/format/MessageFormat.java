package com.geek.pf.log.format;

import java.io.Serializable;

/**
 * Message format.
 *
 * @author xujinkai
 *
 * @date 2018/09/01
 */
public class MessageFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String text;

    private String level;

    private String alarm;

    public MessageFormat(String id, String text, String level, String alarm) {

        this.id = id;
        this.text = text;
        this.level = level;
        this.alarm = alarm;
    }

    public String getAlarm() {

        return alarm;
    }

    public void setAlarm(String alarm) {

        this.alarm = alarm;
    }

    public String getLevel() {

        return level;
    }

    public void setLevel(String level) {

        this.level = level;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    @Override
    public String toString() {

        return "MessageFormat{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", level='" + level + '\'' +
                ", alarm='" + alarm + '\'' +
                '}';
    }
}
