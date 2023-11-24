package cz.muni.fi.pv168.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.pv168.project.export.json.deserializers.CategoryJsonDeserializer;
import cz.muni.fi.pv168.project.export.json.seralizers.CategoryJsonSerializer;

import java.awt.*;

@JsonDeserialize(using = CategoryJsonDeserializer.class)
@JsonSerialize(using = CategoryJsonSerializer.class)
public class Category extends Entity{
    private String name;
    private Color color;
    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     *     substitution for getClass, see usage
     */
    @JsonIgnore
    public Category getItself() {
        return this;
    }
}
