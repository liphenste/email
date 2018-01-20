import java.io.*;

public class Globals {
    public static RandomAccessFile msg = null;
    public static int totalRecordsInMessageFile = 0;
    
    //record constants
    public static final int NEXT_RECORD_LEN = 4;
    public static final int END_OF_MESSAGE = -1;
    public static final char BLANK = ' ';
    
    //error constants
    public static final int PROCESS_OK = 0;
    public static final int PROCESS_ERROR = -1;
    
    //modes for writing in file
    public static final int APPEND = 1;
    public static final int MODIFY = 2;
    
    //available list constants
    public static final int EMPTY_AVAILABLE_LIST = -1;
    public static AvailableList availableList = null;
    public static final int AVAILABLE_NODE_RECORD_NUMBER_LENGTH = 4;
    
    //deleting constant
    public static final char DELETED = '*';
    
    //
    public static final String STR_NULL = "";
    public static final char NULL = '\0';
    public static final int COMMAND_POS = 0;
    public static final int COMMAND_LEN = 1;

    public static final int CLIENT_ID_LEN = 9;
    public static final int CLIENT_POS = COMMAND_POS + COMMAND_LEN;
    public static final int SENDER_POS = COMMAND_POS + COMMAND_LEN;
    public static final int SENDER_LEN = CLIENT_ID_LEN;
    public static final int RECEIVER_POS = SENDER_POS + SENDER_LEN;
    public static final int RECEIVER_LEN = CLIENT_ID_LEN;
    public static final int DATE_TIME_POS = RECEIVER_POS + RECEIVER_LEN;
    public static final int DATE_TIME_LEN = 8; // long current milliseconds coded as eight bytes
    public static final int MARKER_POS = DATE_TIME_POS + DATE_TIME_LEN;
    public static final int MARKER_LEN = 1;
    public static final char END_OF_SUBJECT_MARKER = '~';
    public static final int END_OF_SUBJECT_MARKER_LEN = 1;
    public static final int IDENTIFICATION_LEN = SENDER_LEN + RECEIVER_LEN + DATE_TIME_LEN;
    public static final int INTEGER_LEN = 4;
    
    //server command constants
    public static final char SEND_MESSAGE = 'S';
    public static final char IN_BOX = 'I';
    public static final char OUT_BOX = 'O';
    public static final char SERVER_SHUTDOWN = 'Q';
    
    //more record constants
    public static final int TEXT_LEN = END_OF_SUBJECT_MARKER_LEN + 30;
    public static final int RECORD_DATA_LEN = COMMAND_LEN + 
					      SENDER_LEN +
					      RECEIVER_LEN +
					      DATE_TIME_LEN +
					      MARKER_LEN +
					      TEXT_LEN;
    public static final int RECORD_LEN = RECORD_DATA_LEN + NEXT_RECORD_LEN;
    
    public static final char FIRST_RECORD_OF_MESSAGE = '+';
    
    public static final String MESSAGES_FILE = "_messages.dat";
    public static final String AVAILABLE_LIST_FILE = "_available.dat";
}
