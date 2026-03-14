package com.teqnitzo.engine.scene;

import com.teqnitzo.engine.math.Transform;
import com.teqnitzo.engine.render.Material;
import com.teqnitzo.engine.render.Model;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    protected final Transform transform;
    private final Model model;
    private final Material material;
    private final List<Component> components = new ArrayList<>();
    private boolean started = false;

    public GameObject(Model model, Material material) {
        this.transform = new Transform();
        this.model = model;
        this.material = material;
    }

    public Transform getTransform() {
        return transform;
    }

    public Model getModel() {
        return model;
    }

    public Material getMaterial() {
        return material;
    }

    public void addComponent(Component component) {
        component.setGameObject(this);
        components.add(component);
    }

    public List<Component> getComponents() {
        return components;
    }

    public void update(float deltaTime) {
        if (!started) {
            for (Component component : components) {
                component.start();
            }
            started = true;
        }

        for (Component component : components) {
            component.update(deltaTime);
        }
    }

    public void cleanup() {
        for (Component component : components) {
            component.cleanup();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component)) {
                return (T) component;
            }
        }
        return null;
    }
}