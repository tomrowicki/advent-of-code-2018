package org.tomrowicki.aoc2018.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.tomrowicki.aoc2018.FileReader;

public class Day8 {

	private static int currentNodeStartIndex = 0;

	public static void main(String[] args) throws IOException, URISyntaxException {
		String contents = FileReader.getFileContents("input8test.txt").get(0);
		String[] values = contents.split(" ");
		Node nodeStructure = parseNodes(values);
		int sumOfMdataEntries = getSumOfMdataEntries(nodeStructure);
		System.out.println("Answer to part 1: " + sumOfMdataEntries);
	}

	private static Node parseNodes(final String[] values) {
		Node root = new Node(0);
		int noOfChildren = getNumberOfChildren(currentNodeStartIndex, values);
		root.setNoOfChildren(noOfChildren);
		int quantityOfMdataEntries = getQuantityOfMetadataEntries(currentNodeStartIndex, values);
		root.setQuantityOfMdataEntries(quantityOfMdataEntries);
		List<Node> children = getChildNodes(currentNodeStartIndex, noOfChildren, values);
		root.setChildNodes(children);
		int childrenLength = getChildrenLength(children);
		root.setMetadataEntries(
				getMetadataEntries(currentNodeStartIndex, childrenLength, quantityOfMdataEntries, values));
		return root;
	}

	private static int getNumberOfChildren(int nodeStartingIndex, String[] values) {
		return Integer.parseInt(values[nodeStartingIndex]);
	}

	private static int getQuantityOfMetadataEntries(int nodeStartingIndex, String[] values) {
		return Integer.parseInt(values[nodeStartingIndex + 1]);
	}

	// FIXME ta metoda jest wywoływana zbyt wiele razy dla zestawu testowego
	private static List<Node> getChildNodes(int parentNodeStartingIndex, int noOfChildren, String[] values) {
		List<Node> childrenList = new ArrayList<>();
		for (int i=1; i<=noOfChildren; i++) {
			int startingIndex;
			if (i  == 1) {
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
			childNode.setMetadataEntries(getMetadataEntries(startingIndex, currChildrenLength, currQuantityOfMdataEntries, values));
			childrenList.add(childNode);
		}
		return childrenList;
	}

	private static int getChildrenLength(List<Node> children) {
//		int length = 0;
//		if (children.isEmpty()) {
//			return quantityOfMdataEntries;
//		}
//		for (Node childNode : children) {
//			length += getChildrenLength(childNode.getQuantityOfMdataEntries(), childNode.getChildNodes());
//		}
//		return length;
		int length = 0;
		for (Node childNode : children) {
			length += childNode.getQuantityOfMdataEntries();
		}
		return length;
	}

	private static List<Integer> getMetadataEntries(int currentNodeStartIndex, int childrenLength,
			int quantityOfMdataEntries, String[] values) {
		List<Integer> mdataEntries = new ArrayList<Integer>();
		for (int i = 0; i < quantityOfMdataEntries; i++) {
			// 2 is a given node's 'inner length'
			Integer entry = Integer.parseInt(values[currentNodeStartIndex + 2 + childrenLength + i]);
			mdataEntries.add(entry);
		}
		return mdataEntries;
	}

	private static int getSumOfMdataEntries(Node nodeStructure) {
		return nodeStructure.getMetadataEntries().stream().mapToInt(e -> e).sum()
				+ nodeStructure.getChildNodes().stream().mapToInt(ch -> getSumOfMdataEntries(ch)).sum();
	}
}
