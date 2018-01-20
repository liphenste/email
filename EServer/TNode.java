public class TNode {
    private String id = "";
    private int recordNumber = -1;
    private TNode left = null;
    private TNode right = null;
    private TNode parent = null;
    
    public TNode() {
	id = "";
	recordNumber = -1;
	left = null;
	right = null;
	parent = null;

    }
    
    public TNode(String i, int rn, TNode l, TNode r, TNode p) {
	id = i;
	recordNumber = rn;
	left = l;
	right = r;
	parent = p;
    }
    
    public String getId() {
	return id;
    }
    
    public int getRecordNumber() {
	return recordNumber;
    }
    
    public TNode getLeft() {
	return left;
    }
    
    public TNode getRight() {
	return right;
    }
    
    public TNode getParent() {
	return parent;
    }
    
    public void setId(String i) {
	id = i;
    }
    
    public void setRecordNumber(int rn) {
	recordNumber = rn;
    }
    
    public void setLeft(TNode p) {
	left = p;
    }
    
    public void setRight(TNode p) {
	right = p;
    }
    
    public void setParent(TNode p) {
	parent = p;
    }
    
    public String toString() {
	return this == null ? "null" : "Id: " + id;
    }
}
