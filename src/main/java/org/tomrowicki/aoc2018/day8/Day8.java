package org.tomrowicki.aoc2018.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.tomrowicki.aoc2018.FileReader;

public class Day8 {

	private static final int ROOT_STARTING_INDEX = 0;

	public static void main(String[] args) throws IOException, URISyntaxException {
		String contents = FileReader.getFileContents("input8.txt").get(0);
		String[] values = contents.split(" ");
		Node nodeStructure = parseNodes(values);
		int sumOfMdataEntries = getSumOfMdataEntries(nodeStructure);
		System.out.println("Answer to part 1: " + sumOfMdataEntries);
		int rootNodeValue = getNodeValue(nodeStructure);
		System.out.println("Answer to part 2: " + rootNodeValue);
	}

	private static Node parseNodes(final String[] values) {
		Node root = new Node(0);
		int noOfChildren = getNumberOfChildren(ROOT_STARTING_INDEX, values);
		root.setNoOfChildren(noOfChildren);
		int quantityOfMdataEntries = getQuantityOfMetadataEntries(ROOT_STARTING_INDEX, values);
		root.setQuantityOfMdataEntries(quantityOfMdataEntries);
		List<Node> children = getChildNodes(ROOT_STARTING_INDEX, noOfChildren, values);
		root.setChildNodes(children);
		int childrenLength = getChildrenLength(children);
		root.setMetadataEntries(
				getMetadataEntries(ROOT_STARTING_INDEX, childrenLength, quantityOfMdataEntries, values));
		return root;
	}

	private static int getNumberOfChildren(int nodeStartingIndex, String[] values) {
		return Integer.parseInt(values[nodeStartingIndex]);
	}

	private static int getQuantityOfMetadataEntries(int nodeStartingIndex, String[] values) {
		return Integer.parseInt(values[nodeStartingIndex + 1]);
	}

	private static List<Node> getChildNodes(int parentNodeStartingIndex, int noOfChildren, String[] values) {
		List<Node> childrenList = new ArrayList<>();
		for (int i = 1; i <= noOfChildren; i++) {
			int startingIndex;
			if (i == 1) {
				startingIndex = parentNodeStartingIndex + 2;
			} else {
				int previousChildrenLength = getChildrenLength(childrenList);
				startingIndex = parentNodeStartingIndex + 2 + previousChildrenLength;
			}
			Node childNode = new Node(startingIndex);
			int currNoOfChildren = getNumberOfChildren(startingIndex, values);
			childNode.setNoOfChildren(currNoOfChildren);
			int currQuantityOfMdataEntries = getQuantityOfMetadataEntries(startingIndex, values);
			childNode.setQuantityOfMdataEntries(currQuantityOfMdataEntries);
			List<Node> currChildren = getChildNodes(startingIndex, currNoOfChildren, values);
			childNode.setChildNodes(currChildren);
			int currChildrenLength = getChildrenLength(currChildren);
			childNode.setMetadataEntries(
					getMetadataEntries(startingIndex, currChildrenLength, currQuantityOfMdataEntries, values));
			childrenList.add(childNode);
		}
		return childrenList;
	}

	private static int getChildrenLength(List<Node> children) {
		int length = 0;
		for (Node childNode : children) {
			length += 2 + childNode.getQuantityOfMdataEntries() + getChildrenLength(childNode.getChildNodes());
		}
		return length;
	}

	private static List<Integer> getMetadataEntries(int currentNodeStartIndex, int childrenLength,
			int quantityOfMdataEntries, String[] values) {
		List<Integer> mdataEntries = new ArrayList<Integer>();
		for (int i = 0; i < quantityOfMdataEntries; i++) {
			// 2 is a given node's innate length
			Integer entry = Integer.parseInt(values[currentNodeStartIndex + 2 + childrenLength + i]);
			mdataEntries.add(entry);
		}
		return mdataEntries;
	}

	private static int getSumOfMdataEntries(Node nodeStructure) {
		int parentSum = nodeStructure.getMetadataEntries().stream().mapToInt(e -> e).sum();
		// System.out.println("parent sum: " + parentSum);
		int childrenSum = nodeStructure.getChildNodes().stream().mapToInt(ch -> getSumOfMdataEntries(ch)).sum();
		// System.out.println("children sum: " + childrenSum);
		return parentSum + childrenSum;
	}

	private static int getNodeValue(Node nodeStructure) {
		int nodeValue;
		if (nodeStructure.getNoOfChildren() == 0) {
			nodeValue = nodeStructure.getMetadataEntries().stream().mapToInt(e->e).sum();
		} else {
			nodeValue = getValueForChildren(nodeStructure.getChildNodes(), nodeStructure.getMetadataEntries());
		}
//		System.out.println("Value of node beginning at index " + nodeStructure.getStartingIndex() + " equals: " + nodeValue);
		return nodeValue;
	}

	private static int getValueForChildren(List<Node> childNodes, List<Integer> mDataEntries) {
		int sumOfValues = 0;
		for (int childIndex : mDataEntries) {
			if (childNodes.size() >= childIndex) {
				// this index counts from 1
				Node child = childNodes.get(childIndex -1);
				sumOfValues += getNodeValue(child);
			}
		}
		return sumOfValues;
	}
}
