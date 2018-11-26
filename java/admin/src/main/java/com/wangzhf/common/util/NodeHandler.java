package com.wangzhf.common.util;

import com.wangzhf.common.domain.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理层级结构
 */
public class NodeHandler {

    /**
     * 组织树状结构
     * @param list
     * @return
     */
    public List<Node> handleNode(List<Node> list) {
        if (list == null && list.size() == 0) return null;
        List<Node> nodes = new ArrayList<>();
        for (Node node : list) {
            Node newNode = childNode(node, list, nodes);
            if (!"0".equals(newNode.getParent())) {
                continue;
            }
            nodes.add(newNode);
        }
        return nodes;
    }

    private Node childNode(Node node, List<Node> list, List<Node> all) {
        for (Node src : list) {
            // 判断是否已经存在于all中
            if (exist(node, all)) {
                continue;
            }
            if (node.getId().equals(src.getParent())) {
                node.getChildren().add(src);
                childNode(src, list, all);
            }
        }
        return node;
    }

    private boolean exist(Node node, List<Node> list) {
        for (Node all : list) {
            if (node.getId().equals(all.getId())) {
                return true;
            }
            if (all.getChildren() != null && all.getChildren().size() > 0) {
                if (exist(node, all.getChildren())) {
                    return true;
                }
            }
        }
        return false;
    }
}
