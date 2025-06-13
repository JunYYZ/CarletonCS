package comp2402w24l4;

// From within the Lab directory (or wherever you put the comp2402w24l4 directory):

// javac comp2402w24l4/PhyloTree.java
// java comp2402w24l4/PhyloTree

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import ods.ArrayStack;

public class PhyloTree {

	/**
	 * Array used to store allowable DNA bases. Do not remove this.
	 */
	private static final char[] DNABases = { 'A', 'C', 'G', 'T' };

	/**
	 * Represents a single node in our tree. Do not remove this. You may add to this
	 * class if you wish.
	 */
	class Node {
		String s;
		Node left, right, parent;
		boolean[] set;
		boolean mark;

		public Node(String s, Node parent) {
			this.s = s;
			this.parent = parent;
		}

		public Node() {
			this(null, null);
		}
	}

	/**
	 * The length of each gene/String in each node. Do not remove this.
	 */
	private int k;

	/**
	 *
	 */
	private int n;

	/**
	 * The root node. Do not remove this.
	 */
	protected Node r;

	/**
	 * A map between the Nodes and the Strings they represent. Do not remove this.
	 */
	protected HashMap<String, Node> stringsToNodes;

	/**
	 * Constructor
	 */
	public PhyloTree(int k) {
		if (k < 1)
			throw new IllegalArgumentException("k must be at least 1");
		this.k = k;
		this.n = 0;
		this.stringsToNodes = new HashMap<String, Node>();
	}

	/**
	 * Constructor
	 */
	public PhyloTree() {
		this(1);
	}

	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * @return
	 */
	public int size() {
		return n;
	}

	/**
	 * Resets the tree to be empty. Does nothing to the k value.
	 */
	public void clear() {
		n = 0;
		r = null;
	}

	/**
	 * Returns a basic string representation of this tree. Lists the nodes in
	 * in-order.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder("[");
		Iterator<String> it = iterator();
		while (it.hasNext()) {
			String next = it.next();
			s.append(next == null ? "null" : next + (it.hasNext() ? ", " : ""));
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * Returns a pretty-printed string representation of this tree. It's not
	 * perfect, but it works for the small local tests. It spans multiple lines,
	 * with the root node on the top line, the root's children on the next line and
	 * so on. This is not particularly efficient, but since you're likely to only
	 * use it on small-ish trees, that's fine.
	 * 
	 * @return
	 */
	public String prettyPrint() {
		if (r == null)
			return "";
		int h = height();
		ArrayStack<StringBuilder> levels = new ArrayStack<StringBuilder>();
		for (int i = 0; i <= h; i++) {
			levels.add(new StringBuilder());
		}

		int gap = (int) Math.pow(2, h - 1) * (k + 1) - 1; // was -1
		Node u = r, prev = null, next;
		int level = 0;
		while (u != null) {
			if (prev == u.parent) {
				StringBuilder thisLevel = levels.get(level);
				if (thisLevel.length() == 0)
					thisLevel.append(gapString(gap));
				else
					thisLevel.append(gapString(Math.max(2 * gap + 2 - k, 1)));
				levels.get(level).append(u.s);
				if (u.left != null) {
					next = u.left;
					level++;
					gap = (gap == k ? 0 : (gap + 1) / 2 - 1);
				} else if (u.right != null) {
					// Left node was missing but right is not so we need to add in stuff
					int g = (gap + 1) / 2 - 1;
					int missing = 1;
					for (int j = level + 1; j <= h; j++) {
						thisLevel = levels.get(j);
						if (thisLevel.length() == 0)
							thisLevel.append(gapString(g));
						thisLevel.append(gapString((2 * g + 1) * (missing - 1) + k * missing));
						g = (g == k ? 0 : (g + 1) / 2 - 1);
						missing *= 2;
					}
					next = u.right;
					level++;
					gap = (gap == k ? 0 : (gap + 1) / 2 - 1);
				} else { // Both children are missing
					int g = (gap + 1) / 2 - 1;
					int missing = 2;
					for (int j = level + 1; j <= h; j++) {
						thisLevel = levels.get(j);
						if (thisLevel.length() == 0)
							thisLevel.append(gapString(g));
						else
							thisLevel.append(gapString(2 * g + 1));
						thisLevel.append(gapString((2 * g + 1) * (missing - 1) + k * missing));
						g = (g == k ? 0 : (g + 1) / 2 - 1);
						missing *= 2;
					}
					next = u.parent;
					level--;
					gap = (gap == 0 ? k : (gap + 1) * 2 - 1);
				}
			} else if (prev == u.left) {
				if (u.right != null) {
					next = u.right;
					level++;
					gap = (gap == k ? 0 : (gap + 1) / 2 - 1);
				} else {
					// R was missing but left was not so we need to add in stuff
					int g = (gap + 1) / 2 - 1;
					int missing = 1;
					for (int j = level + 1; j <= h; j++) {
						StringBuilder thisLevel = levels.get(j);
						if (thisLevel.length() == 0)
							thisLevel.append(gapString(g));
						thisLevel.append(gapString((2 * g + 1) * missing + k * missing));
						g = (g == k ? 0 : (g + 1) / 2 - 1);
						missing *= 2;
					}
					next = u.parent;
					level--;
					gap = (gap == 0 ? k : (gap + 1) * 2 - 1);
				}
			} else {
				next = u.parent;
				level--;
				gap = (gap == 0 ? k : (gap + 1) * 2 - 1);
			}
			prev = u;
			u = next;
		}

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < levels.size(); i++) {
			s.append(levels.get(i).toString() + "\n");
		}
		return s.toString();
	}

	private String gapString(int k) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < k; i++) {
			s.append(" ");
		}
		return s.toString();
	}

	public int height() {
		return height(r);
	}

	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == null)
			return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	/**
	 * Returns an iterator over the Strings in the tree in in-order.
	 * 
	 * @param u
	 * @return
	 */
	public Iterator<String> iterator(Node u) {
		class BTI implements Iterator<String> {
			protected Node w, prev;

			public BTI(Node iw) {
				w = iw;
			}

			public boolean hasNext() {
				return w != null;
			}

			public String next() {
				String x = w.s;
				prev = w;
				w = nextNode(w);
				return x;
			}
		}
		return new BTI(u);
	}

	public Iterator<String> iterator() {
		return iterator(firstNode());
	}

	/**
	 * Find the first node in an in-order traversal
	 * 
	 * @return the first node reported in an in-order traversal
	 */
	private Node firstNode() {
		Node w = r;
		if (w == null)
			return null;
		while (w.left != null)
			w = w.left;
		return w;
	}

	/**
	 * Find the node that follows w in an in-order traversal
	 * 
	 * @param w
	 * @return the node that follows w in an in-order traversal
	 */
	private Node nextNode(Node w) {
		if (w.right != null) {
			w = w.right;
			while (w.left != null)
				w = w.left;
		} else {
			while (w.parent != null && w.parent.left != w)
				w = w.parent;
			w = w.parent;
		}
		return w;
	}

	/**
	 * Returns a string representation of the set of bases in the node u. Used by
	 * the autograder; do not modify.
	 */
	private String setToBases(Node u) {
		ArrayStack<Character> s = new ArrayStack<Character>();
		for (int i = 0; i < u.set.length; i++) {
			if (u.set[i])
				s.add(DNABases[i]);
		}
		return u.s + ": " + s.toString();
	}

	/**
	 * Returns a string representation of the set of bases in the node u. Used by
	 * the autograder; do not modify.
	 *
	 * @return
	 */
	public ArrayStack<String> allSets() {
		ArrayStack<String> s = new ArrayStack<String>();
		Node w = firstNode();
		while (w != null) {
			s.add(setToBases(w));
			w = nextNode(w);
		}
		return s;
	}

	// You should not need to modify anything above this line.
	// If you find it helpful to add fields to the Node class, you may do so.

	/**
	 * Adds a new node with String child as a child of the node with String parent
	 * Throws an IllegalArgumentException if the parent is not found or if the
	 * parent already has 2 children. Tries to add as left child, but if left child
	 * exists, adds as right child. if parent == null then we are adding the root
	 * node and current root becomes left child.
	 */
	public void addChild(String parent, String child) throws IllegalArgumentException {
		if (child != null && (child.length() != k || stringsToNodes.containsKey(child))) {
			throw new IllegalArgumentException("Child is not of length k or already exists.");
		}

		Node pNode = null;
		if (parent != null) {
			pNode = stringsToNodes.get(parent);
			if (pNode == null) {
				throw new IllegalArgumentException("Parent does not exist.");
			}
			if (pNode.left != null && pNode.right != null) {
				throw new IllegalArgumentException("Parent already has 2 children.");
			}
		}
		Node cNode = new Node(child, pNode);
		if (pNode == null) {
			if (r != null) {
				cNode.left = r;
				r.parent = cNode;
			}
			r = cNode;
		} else {
			if (pNode.left == null) {
				pNode.left = cNode;
			} else {
				pNode.right = cNode;
			}
		}
		if (child != null) {
			stringsToNodes.put(child, cNode);
		}
		n++;
	}

	/**
	 * Constructs a PhyloTree out of elements in the ArrayStack a.
	 * 
	 * @param a
	 */

//	public PhyloTree(ArrayStack<String> a) {
//		this(a.size() > 0 && a.get(0) != null ? a.get(0).length() : 1);
//
//		Node[] nodes = new Node[a.size()];
//
//		HashMap<String, Boolean> seen = new HashMap<>();
//
//		for (int i = 0; i < a.size(); i++) {
//			String s = a.get(i);
//			// null check
//			if (s == null) {
//				nodes[i] = new Node(null, null);
//			} else {
//				if (s.length() != k) {
//					throw new IllegalArgumentException();
//				}
//				if (seen.containsKey(s)) {
//					throw new IllegalArgumentException();
//				}
//				seen.put(s, true);
//				nodes[i] = new Node(s, null);
//			}
//
//			if (i > 0) {
//				int pIndex = (i - 1) / 2;
//				nodes[pIndex].left = (i % 2 == 1) ? nodes[i] : nodes[pIndex].left;
//				nodes[pIndex].right = (i % 2 == 0) ? nodes[i] : nodes[pIndex].right;
//				nodes[i].parent = nodes[pIndex];
//			}
//		}
//
//		r = (nodes.length > 0) ? nodes[0] : null;
//		n = a.size();
//
//		for (Node node : nodes) {
//			if (node.s != null) {
//				stringsToNodes.put(node.s, node);
//			}
//		}
//	}

	public PhyloTree(ArrayStack<String> a) {
		this();

		boolean nonns = true;

		Node[] nodes = new Node[a.size()];

		HashMap<String, Boolean> seen = new HashMap<>();

		for (int i = 0; i < a.size(); i++) {
			String s = a.get(i);
			// null check
			if (s == null) {
				nodes[i] = new Node(null, null);
			} else {
				if (nonns) {
					this.k = s.length();
				}

				if (s.length() != k) {
					throw new IllegalArgumentException();
				}
				if (seen.containsKey(s)) {
					throw new IllegalArgumentException();
				}
				seen.put(s, true);
				nodes[i] = new Node(s, null);
				nonns = false;
			}

			if (i > 0) {
				int pIndex = (i - 1) / 2;
				nodes[pIndex].left = (i % 2 == 1) ? nodes[i] : nodes[pIndex].left;
				nodes[pIndex].right = (i % 2 == 0) ? nodes[i] : nodes[pIndex].right;
				nodes[i].parent = nodes[pIndex];
			}
		}

		this.r = (nodes.length > 0) ? nodes[0] : null;
		this.n = a.size();

		for (Node node : nodes) {
			if (node.s != null) {
				stringsToNodes.put(node.s, node);
			}
		}
	}

	/**
	 * Returns the String least common ancestor of the Strings u and v. Throws
	 * IllegalArgumentException if s or t is null or not in the tree.
	 *
	 */
	public String LCA(String s, String t) throws IllegalArgumentException {
		if (s == null || t == null || !stringsToNodes.containsKey(s) || !stringsToNodes.containsKey(t)) {
			throw new IllegalArgumentException("s or t is null or not in the tree.");
		}

		Node sNode = stringsToNodes.get(s);
		Node tNode = stringsToNodes.get(t);

//		System.out.println(prettyPrint());
		// all of the edge cases haha
		if (s == t) {
			return s;
		}

		if (sNode == r || tNode == r) {
			return r.s;
		}

		if (sNode.parent == tNode) {
			return t;
		} else if (tNode.parent == sNode) {
			return s;
		}

		if (sNode.parent != null) {
			if (sNode.parent.left == tNode || sNode.parent.right == tNode) {
				return sNode.parent.s;
			}
		} else if (tNode.parent != null) {
			if (tNode.parent.left == sNode || tNode.parent.right == sNode) {
				return tNode.parent.s;
			}
		}

		// calc depth
		int depthS = depth(sNode);
		int depthT = depth(tNode);

		// equalize heights
		while (depthS > depthT) {
			sNode = sNode.parent;
			depthS--;
		}
		while (depthT > depthS) {
			tNode = tNode.parent;
			depthT--;
		}

		// find lca
		while (sNode != tNode) {
			sNode = sNode.parent;
			tNode = tNode.parent;
		}

		return sNode.s; // or tnode.s since they are same at this point
	}

	// helper method to calculate the depth of a node from the root
	private int depth(Node node) {
		int depth = 0;
		while (node != null) {
			node = node.parent;
			depth++;
		}
		return depth;
	}

	/**
	 * Finds the node that contains s. If that node does not satisfy the BST
	 * property with respect to its parent, we rotate it into place and continue up
	 * the tree until it satisfies the BST property with its parent. We use a single
	 * rotation. When comparing against null, we assume the BST property holds and
	 * we can stop. Throws an IllegalArgumentException if s is null, the empty
	 * string, or not in the tree.
	 * 
	 * @param s
	 */

	public void fixUp(String s) throws IllegalArgumentException {
		if (s == null || !stringsToNodes.containsKey(s)) {
			throw new IllegalArgumentException("s is null or not in the tree.");
		}
		Node target = stringsToNodes.get(s);
		while (target.parent != null) {
			if (target.parent.left == target && target.s.compareTo(target.parent.s) > 0) {
				rightRotate(target.parent);

			} else if (target.parent.right == target && target.s.compareTo(target.parent.s) < 0) {
				leftRotate(target.parent);
			} else {
				break;
			}
		}
	}

	private void rightRotate(Node node) {
		Node leftN = node.left;
		if (leftN == null) {
			return;
		}

		node.left = leftN.right;
		if (leftN.right != null) {
			leftN.right.parent = node;
		}

		leftN.parent = node.parent;

		if (node.parent == null) {
			r = leftN;
		} else if (node == node.parent.right) {
			node.parent.right = leftN;
		} else {
			node.parent.left = leftN;
		}
		leftN.right = node;
		node.parent = leftN;
	}

	private void leftRotate(Node node) {
		Node rightN = node.right;

		if (rightN == null) {
			return;
		}

		node.right = rightN.left;
		if (rightN.left != null) {
			rightN.left.parent = node;
		}

		rightN.parent = node.parent;

		if (node.parent == null) {
			r = rightN;
		} else if (node == node.parent.left) {
			node.parent.left = rightN;
		} else {
			node.parent.right = rightN;
		}
		rightN.left = node;
		node.parent = rightN;
	}

	/**
	 * For each node u, compute the set of likely bases at specific index of node's
	 * String. For a node u, u.set[i] is true if the i-th base is a likely base for
	 * the node u. For a leaf u, u.set[i] == true if the i-th base is the same as
	 * the i-th base of u.s For an internal node u with children left and right, if
	 * left and right overlap, u.set will be the elements in left and right that are
	 * in common (their intersection) If left and right do not overlap, then u.set
	 * will be their combination (their union).
	 *
	 * @param index
	 * @throws IllegalArgumentException
	 */
//	public void computeSets0(int index) throws IllegalArgumentException {
//		if (index < 0 || index >= k) {
//			throw new IllegalArgumentException("Index is out of bounds.");
//		}
//		cSets(r, index); // Start the computation from the root
//	}
//
//	private void cSets(Node node, int index) {
//		if (node == null) {
//			return;
//		}
//		int dnaBaseLen = DNABases.length;
//		if (node.left == null && node.right == null) {
//			if (node.s == null) {
//				throw new IllegalArgumentException("Leaf nodes cannot be null.");
//			}
//			node.set = new boolean[dnaBaseLen];
//			char base = node.s.charAt(index);
//			for (int i = 0; i < dnaBaseLen; i++) {
//				if (DNABases[i] == base) {
//					node.set[i] = true;
//					break;
//				}
//			}
//		} else {
//			if (node.left != null) {
//				cSets(node.left, index);
//			}
//			if (node.right != null) {
//				cSets(node.right, index);
//			}
//			node.set = new boolean[dnaBaseLen];
//			if (node.left != null && node.right != null) {
//				boolean iExists = false;
//				for (int i = 0; i < dnaBaseLen; i++) {
//					if (node.left.set[i] && node.right.set[i]) {
//						node.set[i] = true;
//						iExists = true;
//					}
//				}
//				if (!iExists) {
//					for (int i = 0; i < dnaBaseLen; i++) {
//						node.set[i] = node.left.set[i] || node.right.set[i];
//					}
//				}
//			} else {
//
//				Node child = node.left != null ? node.left : node.right;
//				System.arraycopy(child.set, 0, node.set, 0, dnaBaseLen);
//			}
//		}
//	}

	public void computeSets(int index) {
		if (index < 0 || index >= k) {
			throw new IllegalArgumentException("Index out of bounds");
		}
		ArrayStack<Node> poN = new ArrayStack<>();
		ArrayStack<Node> stack = new ArrayStack<>();
		Node current = r;
		Node pre = null;

		while (!stack.isEmpty() || current != null) {
			if (current != null) {
				stack.add(current);
				current = current.left;
			} else {
				Node peeked = stack.get(stack.size() - 1);
				if (peeked.right != null && pre != peeked.right) {
					current = peeked.right;
				} else {
					poN.add(peeked);
					pre = stack.remove(stack.size() - 1);
				}
			}
		}

		int dnaBaseLen = DNABases.length;
		for (Node node : poN) {

			if (node.left == null && node.right == null) {
				// leaf node case
				if (node.s == null) {
					throw new IllegalArgumentException("Leaf nodes cannot be null.");
				}

				node.set = new boolean[dnaBaseLen];
				for (int i = 0; i < dnaBaseLen; i++) {
					if (DNABases[i] == node.s.charAt(index)) {
						node.set[i] = true;
						break;
					}
				}
			} else {
				boolean[] leftSet = node.left != null ? node.left.set : new boolean[dnaBaseLen];
				boolean[] rightSet = node.right != null ? node.right.set : new boolean[dnaBaseLen];
				node.set = new boolean[dnaBaseLen];
				boolean overlap = false;
				for (int i = 0; i < dnaBaseLen; i++) {
					if (leftSet[i] && rightSet[i]) {
						node.set[i] = true;
						overlap = true;
					}
				}
				if (!overlap) {
					for (int i = 0; i < dnaBaseLen; i++) {
						node.set[i] = leftSet[i] || rightSet[i];
					}
				}
			}
		}
	}
	
/**
	// Here are some *limited* local tests.
	// You can and should add your own to test your code!

	private static void testAddChild() {
		testAddChildVariety(true);

		for (int n = 0; n < 8; n++) {
			testAddChildLongSkinny(n, 1, true);
		}
		for (int n = 0; n < 9; n++) {
			testAddChildBalanced(n, 1);
		}

		// Now test k > 1
		testAddChildLongSkinny(5, 2, true);
		testAddChildBalanced(5, 2);
		testAddChildBalanced(6, 2);
		testAddChildBalanced(6, 3);

		testAddChildBalanced(15, 2);

		testAddChildBalanced(26, 3);
		testAddChildBalanced(30, 4);

		testAddChildExceptions();

	}

	private static PhyloTree testAddChildVariety(boolean print) {
		System.out.println("Testing PhyloTree addChild...");
		PhyloTree t = new PhyloTree(1);
		t.addChild(null, "A");
		if (print) {
			System.out.println(t.prettyPrint());
			System.out.println("Expect [A]: " + t);
		}

		t.addChild("A", "B");
		if (print) {
			System.out.println(t.prettyPrint());
			System.out.println("Expect [B, A]: " + t);
		}

		t.addChild("A", "C");
		if (print) {
			System.out.println("Expect [B, A, C]: " + t);
			System.out.println(t.prettyPrint());
		}

		t.addChild("C", "F");
		if (print) {
			System.out.println("Expect [B, A, F, C]: " + t);
			System.out.println(t.prettyPrint());
		}
		t.addChild("B", "D");
		if (print) {
			System.out.println("Expect [D, B, A, F, C]: " + t);
			System.out.println(t.prettyPrint());
		}

		t.addChild("F", "L");
		if (print) {
			System.out.println("Expect [D, B, A, L, F, C]: " + t);
			System.out.println(t.prettyPrint());
		}

		t.addChild("C", "G");
		if (print) {
			System.out.println("Expect [D, B, A, L, F, C, G]: " + t);
			System.out.println(t.prettyPrint());
		}

		t.addChild("F", "M");
		if (print) {
			System.out.println("Expect [D, B, A, L, F, M, C, G]: " + t);
			System.out.println(t.prettyPrint());
		}

		t.addChild("B", "E");
		if (print) {
			System.out.println("Expect [D, B, E, A, L, F, M, C, G]: " + t);
			System.out.println(t.prettyPrint());
		}

		return t;
	}

	private static PhyloTree testAddChildLongSkinny(int n, int k, boolean print) {
		System.out.println("Testing PhyloTree addChildLongSkinny with n=" + n + " and k=" + k + "...");
		PhyloTree t = new PhyloTree(k);
		ArrayStack<String> a = new ArrayStack<String>();
		String parent = null;
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < k; j++) {
				sb.append((char) ('a' + i));
			}
			String child = sb.toString();
			a.add(child);
			t.addChild(parent, child);
			parent = sb.toString();
		}
		if (print) {
			System.out.println(t.prettyPrint());
			System.out.println("Expect reverse of: " + a + ": " + t);
		}
		return t;
	}

	private static PhyloTree testAddChildBalanced(int n, int k) {
		System.out.println("Testing PhyloTree addChildBalanced with n=" + n + " and k=" + k + "...");
		PhyloTree t = new PhyloTree(k);
		ArrayStack<String> a = new ArrayStack<String>();
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < k; j++) {
				sb.append((char) ('a' + i));
			}
			a.add(sb.toString());
		}
		for (int i = 0; i < n; i++) {
			String parent = null;
			if (i > 0)
				parent = a.get((i - 1) / 2);
			t.addChild(parent, a.get(i));
		}
		System.out.println(t.prettyPrint());
		return t;
	}

	private static void testAddChildExceptions() {
		System.out.println("Testing PhyloTree addChildExceptions...");
		System.out.println(
				"\tYou have to uncomment each potential exception-throwing line one at a time in order to test");
		PhyloTree t = new PhyloTree();
		t.addChild(null, "A"); // this is okay
		t.addChild("A", null); // this is okay
		// t.addChild(null, "A"); // should throw IllegalArgumentException for duplicate
		// t.addChild("B", "C"); // should throw IllegalArgumentException for parent not
		// existing
		// t.addChild("A", "CC" ); //should throw IllegalArgumentException for wrong
		// length
		t.addChild("A", "B"); // this is okay
		// t.addChild("A", "C"); // this is not okay as A already has 2 children
	}

	private static void testArrayConstructor(int n, int k) {
		System.out.println("Testing PhyloTree array constructor with n=" + n + " and k=" + k + "...");
		ArrayStack<String> a = new ArrayStack<String>();
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < k; j++) {
				sb.append((char) ('a' + i));
			}
			a.add(sb.toString());
		}
		PhyloTree t = new PhyloTree(a);
		System.out.println(t.prettyPrint());
		Iterator<String> it = t.iterator();
		int i = 0;
		while (it.hasNext()) {
			String next = it.next();
			System.out.print(next + " ");
			i++;
		}
		System.out.println();
		if (i != n) {
			System.out.println("Error: expected " + n + " nodes, but got " + i + " nodes.");
		}
	}

	private static void testArrayConstructor() {
		for (int n = 1; n < 10; n++) {
			testArrayConstructor(n, 1);
		}
		testArrayConstructor(7, 2);
		testArrayConstructor(8, 3);
		testArrayConstructor(9, 4);
		testArrayConstructor(10, 3);
		testArrayConstructor(15, 4);
		testArrayConstructor(26, 1);
	}

	private static void testLCA(int n, int k) {
		ArrayStack<String> a = new ArrayStack<String>();
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < k; j++) {
				sb.append((char) ('a' + i));
			}
			a.add(sb.toString());
		}
		PhyloTree t = new PhyloTree(a);
		System.out.println(t.prettyPrint());

		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < a.size(); j++) {
				if (i == j) {
					System.out.println("Expect " + a.get(i) + ": LCA(" + a.get(i) + "," + a.get(j) + ") = "
							+ t.LCA(a.get(i), a.get(j)));
				} else if (i == 0 || j == 0) {
					System.out.println("Expect " + a.get(0) + ": LCA(" + a.get(i) + "," + a.get(j) + ") = "
							+ t.LCA(a.get(i), a.get(j)));
				} else if (j == 2 * i + 1 || j == 2 * i + 2) { // j is child of i
					System.out.println("Expect " + a.get(i) + ": LCA(" + a.get(i) + "," + a.get(j) + ") = "
							+ t.LCA(a.get(i), a.get(j)));
				} else if (i == 2 * j + 1 || i == 2 * j + 2) { // i is child of j
					System.out.println("Expect " + a.get(j) + ": LCA(" + a.get(i) + "," + a.get(j) + ") = "
							+ t.LCA(a.get(i), a.get(j)));
				} else if ((i - 1) / 2 == (j - 1) / 2) { // i and j are siblings
					System.out.println("Expect " + a.get((i - 1) / 2) + ": LCA(" + a.get(i) + "," + a.get(j) + ") = "
							+ t.LCA(a.get(i), a.get(j)));
				} else {
					System.out.println("LCA(" + a.get(i) + "," + a.get(j) + ") = " + t.LCA(a.get(i), a.get(j)));
				}
			}
		}
	}

	private static void testLCA() {
		testLCA(4, 1);
		testLCA(7, 2);
	}

	private static void testFixUp() {

		PhyloTree t = testAddChildVariety(false);
		System.out.println(t.prettyPrint());

		t.fixUp("A"); // nothing should happen when fixUp root
		System.out.println("Expect no change after fixup(A): \n" + t.prettyPrint());

		t.fixUp("C"); // nothing should happen because C is already "in place" w.r.t. its parent "A"
		System.out.println("Expect no change after fixup(C): \n" + t.prettyPrint());

		t.fixUp("B"); // right rotation because B is bigger than its parent A but shouldn't be
		System.out.println("Expect a right rotation after fixup(B): \n" + t.prettyPrint());

		t.fixUp("G"); // no change because G bigger than its parent C
		System.out.println("Expect no change after fixup(G): \n" + t.prettyPrint());

		t = testAddChildLongSkinny(3, 1, false);
		System.out.println("New tree: \n" + t.prettyPrint());

		t.fixUp("c"); // should require two right rotations
		System.out.println("Expect two rotations after fixup(c), c as root: \n" + t.prettyPrint());

		t = testAddChildLongSkinny(4, 1, false);
		System.out.println("New tree: \n" + t.prettyPrint());

		t.fixUp("d"); // should require two right rotations
		System.out.println("Expect three rotations after fixup(d), d as root: \n" + t.prettyPrint());

		t = testAddChildBalanced(7, 1);
		System.out.println("New tree: \n" + t.prettyPrint());

		t.fixUp("f"); // should require only one right rotation
		System.out.println("Expect [d, b, e, a, f, c, g] one rotation after fixup(f), a as root: " + t);
		// Note that prettyPrint doesn't always work that well when there are missing
		// nodes
		// Trust the pre-order traversal of toString over prettyPrint, when in doubt.

		ArrayStack<String> a = new ArrayStack<>();
		a.add("D");
		a.add("A");
		a.add("B");
		t = new PhyloTree(a);
		t.addChild("B", "C");
		System.out.println("New tree: \n" + t.prettyPrint());
		t.fixUp("C"); // should require a right then a left rotation
		System.out.println("After fixUp(C): " + t.prettyPrint());
	}

	private static void testComputeSets() {
		ArrayStack<String> a = new ArrayStack<String>();
		a.add("AAA");
		PhyloTree t = new PhyloTree(a);
		System.out.println(t.prettyPrint());
		t.computeSets(0); // should be [A] for the only node
		System.out.println("Expect: [AAA: [A]]: " + t.allSets());
		t.computeSets(1); // should be [A] for the only node
		System.out.println("Expect: [AAA: [A]]: " + t.allSets());
		t.computeSets(2); // should be [A] for the only node
		System.out.println("Expect: [AAA: [A]]: " + t.allSets());

		a.add("ACA"); // Now there are two nodes, ACA is the leaf, AAA the root
		t = new PhyloTree(a);
		t.computeSets(0);
		System.out.println("Expect: [ACA: [A], AAA: [A]]: " + t.allSets());
		t.computeSets(1);
		System.out.println("Expect: [ACA: [C], AAA: [C]]: " + t.allSets());
		t.computeSets(2);
		System.out.println("Expect: [ACA: [A], AAA: [A]]: " + t.allSets());

		a.add("AGA");
		a.add("ATA");
		t = new PhyloTree(a);
		System.out.println(t.prettyPrint());
		t.computeSets(0);
		System.out
				.println("Expect [ATA: [A], ACA: [A], AAA: [A], AGA: [A]] since all index 0's are As: " + t.allSets());
		t.computeSets(1);
		System.out.println("Expect [ATA: [T], ACA: [T], AAA: [G, T], AGA: [G]] for index 1: " + t.allSets()); // left
																												// child
																												// but
																												// no
																												// right,
																												// root
																												// union
		a.add("ATC");
		t = new PhyloTree(a);
		System.out.println(t.prettyPrint());
		t.computeSets(2);
		System.out.println("Expect [ATA: [A], ACA: [A, C], ATC: [C], AAA: [A], AGA: [A]] for index 2: " + t.allSets()); // left
																														// child
																														// but
																														// no
																														// right

		a.clear();
		a.add(null);
		a.add(null);
		a.add(null);
		a.add("A");
		a.add("C");
		a.add("G");
		a.add("T");
		t = new PhyloTree(a);
		System.out.println(t.prettyPrint());
		t.computeSets(0);
		System.out.println("Expect [A: [A], null: [A, C], C: [C], null: [A, C, G, T], G: [G], null: [G, T], T: [T]]: "
				+ t.allSets()); // left child but no right

	}

	// These are a few examples of how to use main to run local tests
	// You should test more extensively than this.
//	public static void main(String[] args) {
//		System.out.println("Testing PhyloTree via PhyloTree.main...");
//		System.out.println("These are limited local tests; add more tests to test more thoroughly.");
//		System.out.println("You should also try testing via tests/PhyloTreeTest.java");
//
//		int n = 7;
//
//		// Comment out any of the following methods to stop testing that method.
//		testAddChild();
//		testArrayConstructor();
//		testLCA();
//		testFixUp();
//		testComputeSets();
//		PhyloTree a = new PhyloTree(["A", null]);
//
//	}
**/
}
