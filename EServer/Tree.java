import java.io.*;

public class Tree {
    public TNode root = null;
    
    public Tree() {
	root = null;
    }
    
    public Tree(TNode r) {
	root = r;
    }
    
    public TNode getRoot() {
	return root;
    }
    
    public void setRoot(TNode p) {
	root = p;
    }
    
    public void insertNode(TNode p) {
	insertNodeRec(p);
	balance(p);
    }
    
    private void insertNodeRec(TNode p) {
	if (root == null) {
	    root = p;
	}
	else if (p.getId().compareTo(root.getId()) < 0) {
	    if (root.getLeft() == null) {
		root.setLeft(p);
		p.setParent(root);
	    }
	    else {
		Tree tree = new Tree(root.getLeft());
		tree.insertNodeRec(p);
	    }
	}
	else if (p.getId().compareTo(root.getId()) > 0) {
	    if (root.getRight() == null) {
		root.setRight(p);
		p.setParent(root);
	    }
	    else {
		Tree tree = new Tree(root.getRight());
		tree.insertNodeRec(p);
	    }
	}
	else {
	    System.out.println("Error inserting a node. ID already exists");
	}
    }
    
    public TNode findNode(String id) {
	if (root == null) {
	    return null;
	}
	else if (id.equals(root.getId())) {
	    return root;
	}
	else if (id.compareTo(root.getId()) < 0) {
	    Tree t = new Tree(root.getLeft());
	    return t.findNode(id);
	}
	else if (id.compareTo(root.getId()) > 0) {
	    Tree t = new Tree(root.getRight());
	    return t.findNode(id);
	}
	else {
	    System.out.println("fatal error in findNode");
	    return null;
	}
    }
    
    public void deleteNode(TNode p) {
	 if (p != null) {
	    //case 1
	    if (p.getLeft() == null && p.getRight() == null) {
		if (p == root) {
		    root = null;
		}
		else {
		    setParentsChildLink(p, null);
		    p = null;
		}
	    }
	    //case 2/3
	    else if (p.getLeft() != null && p.getRight() == null ||
		     p.getLeft() == null && p.getRight() != null) {
		TNode q = null; //q will be p's child
		if (p.getLeft() != null) {
		    q = p.getLeft();
		}
		else {
		    q = p.getRight();
		}
		
		if (p == root) {
		    root = q;
		}
		else {
		    setParentsChildLink(p, q);
		}
		q.setParent(p.getParent()); 
	    }
	    //case 4/5
	    else if (p.getLeft() != null && p.getRight() != null) {
		TNode q = p.getLeft(); //q will be p's LEFT child
		//case 4/5a
		if (q.getRight() == null) {
		    if (p == root) {
			root = q;
		    }
		    else {
			setParentsChildLink(p, q);
		    }
		    q.setParent(p.getParent());
		    q.setRight(p.getRight());
		    q.getRight().setParent(q);
		}
		//case 5b/5c
		else {
		    while (q.getRight() != null) {
			q = q.getRight();
		    }
		    q.getParent().setRight(q.getLeft());
		    if (q.getLeft() != null) {
			q.getLeft().setParent(q.getParent());
		    }
		    if (p == root) {
			root = q;
		    }
		    else {
			setParentsChildLink(p, q);
		    }
		    q.setParent(p.getParent());
		    q.setLeft(p.getLeft());
		    q.getLeft().setParent(q);
		    q.setRight(p.getRight());
		    p.getRight().setParent(q);
		}
	    }
	    p = null;
	}
    }
    
    private void setParentsChildLink(TNode old, TNode nu) { //nu:new
	if (old.getParent().getLeft() == old) old.getParent().setLeft(nu);
	else old.getParent().setRight(nu);
    }
    
    public void breadthFirstSave(String fileName) {
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    f.setLength(0);
	    for (int i = 0; i < height(); i++) {
		writeLevel(i, f);
	    }
	    f.close();
	}
	catch (IOException e) {
	    System.out.println("error opening file " + fileName + " in breadthFirstSave()");
	}
    }
    
    private void writeLevel(int level, RandomAccessFile f) {
	if (level == 0) {
	    try {
		f.write(root.getId().getBytes());
		f.writeInt(root.getRecordNumber());
	    }
	    catch (IOException e) {
		System.out.println("error writing tree to file");
	    }
	}
	else if (root != null) {
	    Tree tree = new Tree(root.getLeft());
	    tree.writeLevel(level - 1, f);
	    tree = new Tree(root.getRight());
	    tree.writeLevel(level - 1, f);
	}
    }
    
    public void breadthFirstRetrieve(String fileName) {
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    int totalNodes = (int) (f.length() / (Globals.IDENTIFICATION_LEN + Globals.INTEGER_LEN));
	    byte[] identification = new byte[Globals.IDENTIFICATION_LEN];
	    
	    for (int node = 0; node < totalNodes; node++) {
		f.read(identification);
		String identificationString = new String(identification);
		TNode p = new TNode(identificationString, f.readInt(), null, null, null);
		this.insertNode(p);
	    }
	    f.close();
	}
	catch (IOException e) {
	    System.out.println("error opening file " + fileName + " in breathFirstRetrieve()");
	}
    }
    
    // rotate the tree towards the right with respect to this
    private TNode rightRotate() {
	TNode p = root.getLeft();
	p.setParent(root.getParent());
	
	root.setLeft(p.getRight());
	
	if (root.getLeft() != null)
	    root.getLeft().setParent(root);

	p.setRight(root);
	p.getRight().setParent(p);
	
	return p;
    }
 
    // rotate the tree towards the left with respect to this
    private TNode leftRotate() {
	TNode p = root.getRight();
	p.setParent(root.getParent());

	root.setRight(p.getLeft());
	
	if (root.getRight() != null)
	    root.getRight().setParent(root);
	
	p.setLeft(root);
	p.getLeft().setParent(p);
	
	return p;
    }

    // calculate the height of the tree: one node is a height of 1
    public int height() {
	int treeHeight = 0;
	if (this.getRoot() != null) {
	    Tree lTree = new Tree(this.getRoot().getLeft());
	    int lHeight = 1 + lTree.height();
	    
	    Tree rTree = new Tree(this.getRoot().getRight());
	    int rHeight = 1 + rTree.height();
	    
	    treeHeight = lHeight > rHeight ? lHeight : rHeight;
	}
	return treeHeight;
    }

     // calculate the AVL balance factor: height of left child of this - height of right child of this
    private int balanceFactor() {
	Tree lTree = new Tree(this.getRoot().getLeft());
	Tree rTree = new Tree(this.getRoot().getRight());
	return lTree.height() - rTree.height();
    }
 
    // balance the tree starting at this according to AVL self-balancing algorithm
    // if called after insertion, p is the node that has just been inserted
    // if called after deletion, p will have to be one of these two:
    //
    //  1) a leaf that has been deleted: In the calling method, the passed 
    //     parameter will still have the link information even if the node has been
    //     deleted in deleteNode(); thus it can be safely passed into here
    //  2) the node that has been shifted up and has taken q's place since the parent of this node may
    //     experience unbalancing
    //
    //  It might be best to do the call to balance() from within the insertNode() and
    //  the deleteNode() so that all this node information is available

    private void balance(TNode p) { 
	if (p != null) {
	    TNode ancestor = p;

	    while (ancestor != null) {
		Tree ancestorTree = new Tree(ancestor);            

		if (ancestorTree.balanceFactor() == -2) {
		    Tree rTree = new Tree(ancestorTree.root.getRight());                

		    int rTreeBalanceFactor = rTree.balanceFactor();
		    if (rTreeBalanceFactor == -1 || rTreeBalanceFactor == 0) { //0 happens in delete case 7a
			if (ancestor == root)
			    root = ancestorTree.leftRotate();
			else { 

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.leftRotate()); 
			    else
				ancestor.getParent().setRight(ancestorTree.leftRotate());
			}
		    }
		    else if (rTreeBalanceFactor == 1 || rTreeBalanceFactor == 0) {
			ancestor.setRight(rTree.rightRotate());
			if (ancestor == root)
			    root = ancestorTree.leftRotate();
			else {

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.leftRotate());
			    else
				ancestor.getParent().setRight(ancestorTree.leftRotate());
			}
		    }
		}
		else if (ancestorTree.balanceFactor() == 2) {
		    Tree lTree = new Tree(ancestorTree.root.getLeft());

		    int lTreeBalanceFactor = lTree.balanceFactor();
		    if (lTreeBalanceFactor == 1 || lTreeBalanceFactor == 0) { // 0 this symmetrical case of 7a does not happen. it's here to make the method symmetric and possible future optimization
		       if (ancestor == root)
			   root = ancestorTree.rightRotate();
		       else {

			   // determine if the ancestor is a left or a right child

			   if (ancestor.getParent().getLeft() == ancestor)
			       ancestor.getParent().setLeft(ancestorTree.rightRotate());
			   else
			       ancestor.getParent().setRight(ancestorTree.rightRotate());
		       }
		    }
		    else if (lTreeBalanceFactor == -1 || lTreeBalanceFactor == 0) {
			ancestor.setLeft(lTree.leftRotate());
			if (ancestor == root)
			    root = ancestorTree.rightRotate();
			else {

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.rightRotate());
			    else
				ancestor.getParent().setRight(ancestorTree.rightRotate());
			}
		    }
		}
		ancestor = ancestor.getParent(); // ancestor may have changed in rotation. ancestor keeps moving up anyway and will reach the null of the root's parent
	    }
	}
    }
    
    
    public void printTree() {
	if (root != null) {
	    Tree tree = null;
	    tree = new Tree(root.getLeft());
	    tree.printTree();
	    System.out.println(root);
	    tree = new Tree(root.getRight());
	    tree.printTree();
	}
    }
    
    public void printTree(int level) {
	if (root != null) {
	    Tree tree = null;
	    tree = new Tree(root.getLeft());
	    tree.printTree(level + 1);
	    System.out.println(root + " in level " + level);
	    tree = new Tree(root.getRight());
	    tree.printTree(level + 1);
	}
    }
    
    // the following method prints the nodes of a tree level by level starting at level zero
    public void breadthFirstPrint() {
	int treeHeight = this.height();
	for (int i = 0; i <= treeHeight; i++) printNodesOfOneLevel(this.root, i, 0);
    }
    
    // print all nodes at level n
    private void printNodesOfOneLevel(TNode p, int level, int currentLevel) {
	if (p != null) {
	    if (currentLevel == level) {
		System.out.print(p.getId() + " in level " + level + " ");
		if (p.getParent() == null) System.out.println("Root");
		else if (p.getParent().getLeft() == p) System.out.println("Left child of " + p.getParent().getId());
		else System.out.println("Right child of " + p.getParent().getId());
	    }

	    else {
		printNodesOfOneLevel(p.getLeft(), level, currentLevel + 1);
		printNodesOfOneLevel(p.getRight(), level, currentLevel + 1);
	    }
	}
    }
}
