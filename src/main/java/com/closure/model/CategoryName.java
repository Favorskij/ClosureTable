package com.closure.model;

import com.sun.istack.NotNull;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_name", schema = "closure")
@DynamicUpdate
public class CategoryName implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    private String name;

    @Column(name = "level", columnDefinition = "integer default 0")
    private int level;



    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "children")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TreePath> children = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TreePath> parent = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<TreePath> getChildren() {
        return children;
    }

    public void setChildren(List<TreePath> children) {
        this.children = children;
    }

    public List<TreePath> getParent() {
        return parent;
    }

    public void setParent(List<TreePath> parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryName)) return false;

        CategoryName that = (CategoryName) o;

        if (getLevel() != that.getLevel()) return false;
        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getChildren().equals(that.getChildren())) return false;
        return getParent().equals(that.getParent());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getLevel();
        result = 31 * result + getChildren().hashCode();
        result = 31 * result + getParent().hashCode();
        return result;
    }
}
