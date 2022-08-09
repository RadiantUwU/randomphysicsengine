package com.radiant.randomphysicsengine.datatypes;

import com.radiant.randomphysicsengine.datatypes.exceptions.RecursionError;

import java.util.ArrayList;
import java.util.Set;

public interface InheritableObject {
    InheritableObject getParent();
    void setParent(InheritableObject parent);
    Set<InheritableObject> getChildren();
    void addChild(InheritableObject child);
    void removeChild(InheritableObject child);
    default ArrayList<InheritableObject> getDescendants() {
        int recursions = 0;
        ArrayList<InheritableObject> currChildren = new ArrayList<>(getChildren());
        ArrayList<InheritableObject> descendants = new ArrayList<>(currChildren);
        ArrayList<ArrayList<InheritableObject>> children = new ArrayList<>();
        children.add(new ArrayList<>(currChildren));
        while (!children.isEmpty()) {
            recursions++;
            if (recursions > 100000)
                throw new RecursionError("Too many children/recursion detected.");
            currChildren = children.remove(0);
            if (currChildren.isEmpty()) {recursions--; continue;}
            for (InheritableObject child : currChildren) {
                descendants.add(child);
                children.add(new ArrayList<>(child.getChildren()));
            }
        }
        return descendants;
    }
}
