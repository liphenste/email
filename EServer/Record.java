import java.io.*;

public class Record {
    //private variables
    private byte[] data = new byte[Globals.RECORD_DATA_LEN];
    private int next = Globals.END_OF_MESSAGE;
    
    //constructors
    public Record() {
	for (int i = 0; i < data.length; i++)
	    data[i] = (byte) Globals.BLANK;
	next = Globals.END_OF_MESSAGE;
    }
    
    public Record(String s, int nextRecord) {
	setData(s, nextRecord);
    }
    
    //access methods
    public String getData() {
	String string = "";
	for (int i = 0; i < data.length; i++)
	    string = string + (char) (data[i]);
	return string;
    }
    
    public void setData(String txt, int nextRecord) {
	for (int i = 0; i < data.length; i++)
	    data[i] = i < txt.length() ? (byte) txt.charAt(i) : (byte) Globals.BLANK;
	next = nextRecord;
    }
    
    public int getNext() {
	return next;
    }
    
    public int readFromMessagesFile(int recordNumber) {
	try {
	    Globals.msg.seek(recordNumber * Globals.RECORD_LEN);
	    int bytes = Globals.msg.read(data); //"read(byte[])" method returns the total number of bytes read into the buffer
	    next = Globals.msg.readInt();
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }

    public int writeToMessagesFile(int recordNumber, int mode) {
	try {
	    Globals.msg.seek(recordNumber * Globals.RECORD_LEN);
	    Globals.msg.write(data);
	    Globals.msg.writeInt(next);
	    if (mode == Globals.APPEND)
		Globals.totalRecordsInMessageFile++;
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }
    
    public void deleteFromMessagesFile(int recordNumber) {
	int error = readFromMessagesFile(recordNumber);
	if (error == Globals.PROCESS_OK) {
	    data[0] = (byte) Globals.DELETED;
	    error = writeToMessagesFile(recordNumber, Globals.MODIFY);
	    if (error == Globals.PROCESS_OK) {
		Globals.availableList.addRecord(recordNumber);
	    }
	    else {
		System.out.println("Error writing record in deleteFromMessagesFile");
	    }
	}
	else {
	    System.out.println("Error reading record in deleteFromMessagesFile");
	}
    }
    
    public String toString() {
	return getData() + next;
    }
    
    public void dumpData() { //Used for Testing01_01.java
	for (int i = 0; i < data.length; i++) {
	    System.out.print(i + " ");
	    if (data[i] == (byte) Globals.BLANK)
		System.out.println("BLANK!");
	    else
		System.out.println((char) data[i]);
	}
    }
}
