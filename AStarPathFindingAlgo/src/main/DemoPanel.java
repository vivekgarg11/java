package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DemoPanel extends JPanel {

	// SCREEN SETTINGS

	final int maxCol = 15;
	final int maxRow = 10;
	final int nodeSize = 80;
	final int screenWidth = maxCol * nodeSize;
	final int screenHeight = maxRow * nodeSize;

	// NODE
	Node[][] node = new Node[maxCol][maxRow];
	Node startNode, goalNode, currentNode;
	ArrayList<Node> openList = new ArrayList<>();
	ArrayList<Node> checkedList = new ArrayList<>();

	// OTHERS
	boolean goalReached = false;
	int step = 0;

	public DemoPanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.gray);
		this.setLayout(new GridLayout(maxRow, maxCol, 1, 1));
		this.addKeyListener(new KeyHandler(this));
		this.setFocusable(true);

		// PLACE NODES
		int col = 0;
		int row = 0;

		while (col < maxCol && row < maxRow) {
			node[col][row] = new Node(col, row);
			this.add(node[col][row]);
			col++;

			if (col == maxCol) {
				col = 0;
				row++;
			}
		}

		// SET START AND GOAL NODES
		setStarttNode(3, 6);
		setGoalNode(11, 3);

		// PLACE SOLID NODES
		setSolidNode(10, 2);
		setSolidNode(10, 3);
		setSolidNode(10, 4);
		setSolidNode(10, 5);
		setSolidNode(10, 6);
		setSolidNode(10, 7);
		setSolidNode(11, 7);
		setSolidNode(12, 7);
		setSolidNode(6, 2);
		setSolidNode(7, 2);
		setSolidNode(8, 2);
		setSolidNode(9, 2);
		setSolidNode(6, 1);

		// SET COST
		setCostOnNodes();

	}

	private void setStarttNode(int col, int row) {
		node[col][row].setAsStart();
		startNode = node[col][row];
		currentNode = startNode;
	}

	private void setGoalNode(int col, int row) {
		node[col][row].setAsGoal();
		goalNode = node[col][row];
	}

	private void setSolidNode(int col, int row) {
		node[col][row].setAsSolid();
	}

	private void setCostOnNodes() {
		int col = 0;
		int row = 0;

		while (col < maxCol && row < maxRow) {
			getCost(node[col][row]);
			col++;

			if (col == maxCol) {
				col = 0;
				row++;
			}
		}
	}

	private void getCost(Node node) {

		// GET G COST (THE DISTANCE FROM THE START NODE)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;

		// GET H COST (THE DISTANCE FROM THE GOAL NODE)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;

		// GET F COST (THE TOTAL COST)
		node.fCost = node.gCost + node.hCost;

		// DISPLAY THE COST ON NODE
		if (node != startNode && node != goalNode) {
			node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
		}
	}

	public void search() {
		if (goalReached == false) {
			int col = currentNode.col;
			int row = currentNode.row;
			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0)
				openNode(node[col][row - 1]);
			// OPEN THE LEFT NODE
			if (col - 1 >= 0)
				openNode(node[col - 1][row]);
			// OPEN THE DOWN NODE
			if (row + 1 < maxRow)
				openNode(node[col][row + 1]);
			// OPEN THE RIGHT NODE
			if (col + 1 < maxCol)
				openNode(node[col + 1][row]);

			// FIND THE BEST NODE
			int bestNodeIndex = 0;
			int bestNodeOfCost = 999;

			for (int i = 0; i < openList.size(); i++) {
				// CHECK IF THIS NODE'S fCost IS BETTER
				if (openList.get(i).fCost < bestNodeOfCost) {
					bestNodeIndex = i;
					bestNodeOfCost = openList.get(i).fCost;
				}
				// IF fCost EQUALS THEN CHECK gCost
				else if (openList.get(i).fCost == bestNodeOfCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			// AFTER THE LOOP , WE GET THE BEST NODE WHICH IS OUR NEXT STEP
			currentNode = openList.get(bestNodeIndex);

			if (currentNode == goalNode) {
				goalReached = true;
			}
		}
	}

	public void autoSearch() {
		while (goalReached == false && step < 300) {
			int col = currentNode.col;
			int row = currentNode.row;
			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);

			// OPEN THE UP NODE
			if (row - 1 >= 0)
				openNode(node[col][row - 1]);
			// OPEN THE LEFT NODE
			if (col - 1 >= 0)
				openNode(node[col - 1][row]);
			// OPEN THE DOWN NODE
			if (row + 1 < maxRow)
				openNode(node[col][row + 1]);
			// OPEN THE RIGHT NODE
			if (col + 1 < maxCol)
				openNode(node[col + 1][row]);

			// FIND THE BEST NODE
			int bestNodeIndex = 0;
			int bestNodeOfCost = 999;

			for (int i = 0; i < openList.size(); i++) {
				// CHECK IF THIS NODE'S fCost IS BETTER
				if (openList.get(i).fCost < bestNodeOfCost) {
					bestNodeIndex = i;
					bestNodeOfCost = openList.get(i).fCost;
				}
				// IF fCost EQUALS THEN CHECK gCost
				else if (openList.get(i).fCost == bestNodeOfCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			// AFTER THE LOOP , WE GET THE BEST NODE WHICH IS OUR NEXT STEP
			currentNode = openList.get(bestNodeIndex);

			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
	}

	private void openNode(Node node) {
		if (node.open == false && node.checked == false && node.solid == false) {
			// IF THE NODE IS NOT OPENED YET, ADD IT TO THE OPENED LIST

			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

	private void trackThePath() {
		// BCACKTRACK AND DRAW THE BEST PATH
		Node current = goalNode;
		while (current != startNode) {
			current = current.parent;
			if (current != startNode) {
				current.setAsPath();
			}

		}
	}
}
