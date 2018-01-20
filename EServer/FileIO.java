import java.io.*;

public class FileIO {
    public static int openMessagesFile(String fileName) {
	try {
	    Globals.msg = new RandomAccessFile(fileName, "rw");
	    Globals.totalRecordsInMessageFile = (int) (Globals.msg.length() / Globals.RECORD_LEN);
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }
 
    public static int closeMessagesFile() {
	try {
	    Globals.msg.close();
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }
    
    public static int saveAvailableList(String fileName) {
	RandomAccessFile f = null;
	try {
	    f = new RandomAccessFile(fileName, "rw");
	    f.setLength(0);
	    Available p = Globals.availableList.getHead();
	    while (p != null) {
		f.writeInt(p.getRecordNumber());
		p = p.getNext();
	    }
	    f.close();
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }
    
    public static int retrieveAvailableList(String fileName) {
	RandomAccessFile f = null;
	try {
	    f = new RandomAccessFile(fileName, "rw");
	    f.seek(0);
	    Globals.availableList = new AvailableList();
	    int totalDeletedRecords = (int) (f.length() / Globals.AVAILABLE_NODE_RECORD_NUMBER_LENGTH);
	    for (int node = 0; node < totalDeletedRecords; node++) {
		Globals.availableList.addRecord(f.readInt());
	    }
	    f.close();
	    return Globals.PROCESS_OK;
	}
	catch (IOException e) {
	    return Globals.PROCESS_ERROR;
	}
    }
}
