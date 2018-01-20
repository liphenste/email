import hsa.*;

public class EmailServer {
    public static void main(String[] args) {
	Tree senderIndex = new Tree();
	Message message = new Message();
	String identification = Globals.STR_NULL;
	int recordNumber = -1;
	TNode p = new TNode();
	
	
	int error = FileIO.openMessagesFile(Globals.MESSAGES_FILE);
	if (error == Globals.PROCESS_OK) {
	    error = FileIO.retrieveAvailableList(Globals.AVAILABLE_LIST_FILE);
	    
	    if (error == Globals.PROCESS_OK) {
		
		int command = -1;
		do {
		    System.out.println("Available List: ");
		    System.out.println(Globals.availableList);
		    
		    System.out.println("Server options: ");
		    System.out.println("1. Add message");
		    System.out.println("2. Delete message");
		    System.out.println("3. Print all messages");
		    System.out.println("4. Find message (sender Id + receiver Id + dateTime)");
		    System.out.println("5. Find message (receiver Id + sender Id + dateTime)");
		    System.out.println("6. Find messages from partial identifications");
		    System.out.println("7. Rebuild available list");
		    System.out.println("8. Rebuild trees");
		    System.out.println("9. Breadth-first print of trees");
		    System.out.println("99. Quit");
		    System.out.println();
		    System.out.print("Option -> ");
		    command = Stdin.readInt();
		    
		    switch (command) {
			case 1:
			    System.out.print("Sender id: ");
			    String senderId = Stdin.readString();
			    System.out.print("Receiver id: ");
			    String receiverId = Stdin.readString();
			    System.out.print("Date (8 characters): ");
			    String dateTime = Stdin.readString();
			    System.out.print("Subject: ");
			    String subject = Stdin.readLine();
			    System.out.print("Message: ");
			    String text = Stdin.readLine();
			    
			    message.setMessage(Globals.SEND_MESSAGE +
					       senderId +
					       receiverId +
					       dateTime +
					       Globals.FIRST_RECORD_OF_MESSAGE +
					       subject +
					       Globals.END_OF_SUBJECT_MARKER +
					       text);
							  
			    recordNumber = message.writeToMessagesFile();
			    
			    identification = senderId + receiverId + dateTime;
			    p = new TNode(identification, recordNumber, null, null, null);
			    senderIndex.insertNode(p);
			    System.out.println(p);
			    break;
			case 2:
			    break;
			case 3:
			    break;
			case 4:
			    System.out.print("Sender message identification (senderId + receiverId + dateTime): ");
			    identification = Stdin.readString();
			    p = senderIndex.findNode(identification);
			    if (p != null) {
				recordNumber = p.getRecordNumber();
				System.out.println("At record number: " + recordNumber);
				message.printFromMessagesFile(recordNumber);
			    }
			    else System.out.println("***identification not found");
			    break;
			case 5:
			    break;
			case 6:
			    break;
			case 7:
			    break;
			case 8:
			    break;
			case 9:
			    break;
			case 99:
			    break;
		    }
		} while (command != 99);
		
		error = FileIO.saveAvailableList(Globals.AVAILABLE_LIST_FILE);
	    }
	    else {
		System.out.println("Error: Unable to open available list file");
	    }
	    FileIO.closeMessagesFile();
	}  
    }
}
