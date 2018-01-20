public class Utils {
    public static String generateRandomCode(int len) {
	String string = "";
	for (int i = 0; i < len; i++)
	    string += (char) ('0' + (Math.random() * 10));
	return string;
    }
    
    public static boolean isANumber(String s) {
	boolean isDigit = true;
	for (int i = 0; i < s.length() && isDigit; i++)
	    if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9'))
		isDigit = false;
	return isDigit;
    }
    
    public static String leftPad(String text, int desiredLength, char paddingItem) {
	while (text.length() < desiredLength)
	    text = paddingItem + text;
	return text;
    }
    
    public static String removeChars(String text, char c) {
	String withoutChar = "";
	for (int i = 0; i < text.length(); i++)
	    if (text.charAt(i) != c) 
		withoutChar += text.charAt(i);
	return withoutChar;
    }
    
    public static void delay(int ms) {
	try {
	    Thread.sleep (ms);
	}
	catch (InterruptedException ex) {
	    Thread.currentThread().interrupt();
	    System.out.println("Process error in delay method.");
	}
    }
    
    //main method for debug
    /*
    public static void main(String[] args) {
	System.out.println(generateRandomCode(5));
	
	System.out.println(isANumber("016789"));
	
	System.out.println(leftPad("456", 6, '0'));
	System.out.println(leftPad("456", 2, '0'));
	System.out.println(leftPad("", 4, '0'));
	
	System.out.println(removeChars("Hello", 'l'));
	System.out.println(removeChars("00034255", '0'));
	System.out.println(removeChars("Holiday soon", 'f'));
	
	delay(1000);
    }
    */
}
