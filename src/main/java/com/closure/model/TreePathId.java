package com.closure.model;

import java.io.Serializable;

public class TreePathId implements Serializable {


    private long children;
    private long parent;


    public long getChildren() {
        return children;
    }

    public void setChildren(long children) {
        this.children = children;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreePathId)) return false;

        TreePathId that = (TreePathId) o;

        if (getChildren() != that.getChildren()) return false;
        return getParent() == that.getParent();
    }

    @Override
    public int hashCode() {
        int result = (int) (getChildren() ^ (getChildren() >>> 32));
        result = 31 * result + (int) (getParent() ^ (getParent() >>> 32));
        return result;
    }
}
