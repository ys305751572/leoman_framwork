package com.leoman.role.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Table(name = "t_module")
@Entity
public class Modules extends BaseEntity {

    @Column(name = "name")
    private String name = "";

    @Column(name = "url")
    private String url = "";

    @Column(name = "description")
    private String description = "";

    @Column(name = "parent_id")
    private Long parentId;

    @Transient
    private List<Modules> modulesList = new ArrayList<Modules>();
    /*@OneToMany(mappedBy = "parent")
    private List<Modules> childs;

    @ManyToOne
    @JoinTable(name = "t_module_relation", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Modules parent;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public List<Modules> getChilds() {
        return childs;
    }

    public void setChilds(List<Modules> childs) {
        this.childs = childs;
    }

    public Modules getParent() {
        return parent;
    }

    public void setParent(Modules parent) {
        this.parent = parent;
    }*/

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Modules> getModulesList() {
        return modulesList;
    }

    public void setModulesList(List<Modules> modulesList) {
        this.modulesList = modulesList;
    }
}
