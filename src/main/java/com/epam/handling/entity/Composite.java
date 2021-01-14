package com.epam.handling.entity;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
    private List<Component> components = new ArrayList<>();
    private Separator separator;
    private ComponentType type;

    public Composite(Separator separator, ComponentType type) {
        this.separator = separator;
        this.type = type;
    }

    public ComponentType getType() {
        return type;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public void add(Component component) {
        components.add(component);
    }

    public void addAll(List<Component> list) {
        components.addAll(list);
    }

    public int size() {
        return components.size();
    }

    @Override
    public StringBuilder buildText() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < components.size(); i++) {
            StringBuilder part = components.get(i).buildText();
            if(i != components.size() - 1) {
                part.append(separator.get());
            }
            result.append(part);
        }
//        for(Component component : components) {
//            StringBuilder part = component.buildText();
//            part.append(separator.get());
//            result.append(part);
//        }
        return result;
    }
}
