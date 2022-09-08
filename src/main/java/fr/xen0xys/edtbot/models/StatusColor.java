package fr.xen0xys.edtbot.models;

import java.awt.*;

public enum StatusColor {
    Ok(Color.GREEN),
    Warning(Color.YELLOW),
    Error(Color.RED);

    private final Color color;

    StatusColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
