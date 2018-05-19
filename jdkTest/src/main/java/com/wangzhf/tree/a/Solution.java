package com.wangzhf.tree.a;

/**
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
 */
public class Solution {

	public static TreeNode reBuildTree(int[] pre, int preStart, int preEnd,
									   int[] mid, int midStart, int midEnd) {
		if(preStart > preEnd || midStart > midEnd){
			return null;
		}
		TreeNode root = new TreeNode(pre[preStart]);
		for (int i = midStart; i <= midEnd; i++) {
			if (pre[preStart] == mid[i]) { // 中序中与根节点匹配
				// preStart: 对于前序来说，当前preStart的下一个有可能是左节点
				// preEnd: i-midStart表示左子节点的数量，还需要加上根节点此时的位置preStart
				// midStart: 对于中序，根节点i之前的都有可能
				// midEnd: 根节点i之前
				root.left = reBuildTree(pre, preStart + 1, preStart + (i - midStart),
						mid, midStart, i - 1);
				// preStart: 中序：需要跳过所有子节点数（i - minStart），再加上当前根节点的位置，再加1开始。
				// preEnd: 中序，右边的都有可能
				// midStart: 当前根节点位置加1
				// midEnd: 右边都有可能
				root.right = reBuildTree(pre, (i - midStart) + preStart + 1, preEnd,
						mid, i + 1, midEnd);
				break;
			}
		}
		return root;
	}

	public static void main(String[] args) {
		int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
		int[] mid = {4, 7, 2, 1, 5, 3, 8, 6};
		TreeNode root = reBuildTree(pre, 0, pre.length - 1,
				mid, 0, mid.length - 1);
		System.out.println(root);
	}
}