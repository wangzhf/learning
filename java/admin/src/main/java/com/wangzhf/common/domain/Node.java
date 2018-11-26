package com.wangzhf.common.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理层级节点
 */
public class Node {

    private String id;

    private String parent;

    private String name;

    private List<Node> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
