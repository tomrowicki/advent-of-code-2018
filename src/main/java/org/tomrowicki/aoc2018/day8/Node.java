package org.tomrowicki.aoc2018.day8;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private int startingIndex;

	private int noOfChildren;

	private int quantityOfMdataEntries;

	private List<Node> childNodes = new ArrayList<>();

	private List<Integer> metadataEntries = new ArrayList<>();

	public Node(int index) {
		this.startingIndex = index;
	}

	public int getStartingIndex() {
		return startingIndex;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public int getQuantityOfMdataEntries() {
		return quantityOfMdataEntries;
	}

	public void setQuantityOfMdataEntries(int quantityOfMdataEntries) {
		this.quantityOfMdataEntries = quantityOfMdataEntries;
	}

	public List<Node> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Node> childNodes) {
		this.childNodes = childNodes;
	}

	public List<Integer> getMetadataEntries() {
		return metadataEntries;
	}

	public void setMetadataEntries(List<Integer> metadataEntries) {
		this.metadataEntries = metadataEntries;
	}
}
