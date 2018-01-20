public class Available {
    private int recordNumber = -1;
    private Available next = null;
    
    public Available() {
	recordNumber = -1;
	next = null;
    }
    
    public Available(int n) {
	recordNumber = n;
	next = null;
    }
    
    public int getRecordNumber() {
	return recordNumber;
    }
    
    public Available getNext() {
	return next;
    }
    
    public void setNext(Available q) {
	next = q;
    }
    
    public String toString() {
	return "" + recordNumber;
    }
}
