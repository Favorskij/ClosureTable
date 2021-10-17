package com.closure.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tree_path", schema = "closure")
@DynamicUpdate
@IdClass(com.closure.model.TreePathId.class)
public class TreePath implements Serializable {



    @Id
    @ManyToOne(targetEntity = CategoryName.class)
    @JoinColumn(name = "children", nullable = false, foreignKey = @ForeignKey(name = "FK_CHILDREN"))
    private CategoryName children;


    @Id
    @ManyToOne(targetEntity = CategoryName.class)
    @JoinColumn(name = "parent", nullable = false, foreignKey = @ForeignKey(name = "FK_PARENT"))
    private CategoryName parent;


    public CategoryName getChildren() {
        return children;
    }

    public void setChildren(CategoryName children) {
        this.children = children;
    }

    public CategoryName getParent() {
        return parent;
    }

    public void setParent(CategoryName parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreePath)) return false;

        TreePath treePath = (TreePath) o;

        if (!getChildren().equals(treePath.getChildren())) return false;
        return getParent().equals(treePath.getParent());
    }

    @Override
    public int hashCode() {
        int result = getChildren().hashCode();
        result = 31 * result + getParent().hashCode();
        return result;
    }
}
