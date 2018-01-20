import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EMailClientComposeMessage implements ActionListener {
    private JFrame frame = null;
    private JPanel panel1 = null;
    private JPanel panel2 = null;
    private JPanel panel3 = null;
    private JLabel receiverLabel = null;
    private JTextField receiver = null;
    private JLabel subjectLabel = null;
    private JTextField subject = null;
    private JTextArea messageText = null;
    private JButton send = null;
    private JButton cancel = null;
    
    public EMailClientComposeMessage() {
	//set up the frame
	frame = new JFrame("New Message - ICS3U Bloor CI");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLocation(180, 160);
	frame.setResizable(false);
	
	//set up the container with three panels
	Container contentPane = frame.getContentPane();
	BoxLayout contentPaneLayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
	contentPane.setLayout(contentPaneLayout);
	
	panel1 = new JPanel();
	panel2 = new JPanel();
	panel3 = new JPanel();
	
	contentPane.add(panel1);
	contentPane.add(panel2);
	contentPane.add(panel3);
	
	// set up the first time;
	FlowLayout panel1layout = new FlowLayout(FlowLayout.CENTER);
	panel1.setLayout(panel1layout);
	
	receiverLabel = new JLabel("To");
	panel1.add(receiverLabel);
	receiver = new JTextField(Globals.RECEIVER_LEN);
	receiver.setFont(new Font("Arial", Font.PLAIN, 14));
	panel1.add(receiver);
	
	subjectLabel = new JLabel("Subject");
	panel1.add(subjectLabel);
	subject = new JTextField(51);
	subject.setFont(new Font("Arial", Font.PLAIN, 14));
	panel1.add(subject);
	
	// set up the second panel
	FlowLayout panel2Layout = new FlowLayout(FlowLayout.CENTER);
	panel2.setLayout(panel2Layout);
	
	messageText = new JTextArea(10, 69);
	messageText.setBorder(BorderFactory.createLineBorder(Color.blue));
	messageText.setFont(new Font("Arial", Font.PLAIN, 14));
	messageText.setLineWrap(true);
	messageText.setWrapStyleWord(true);
	panel2.add(messageText);
	
	// set up the third panel
	
	FlowLayout panel3Layout = new FlowLayout(FlowLayout.CENTER);
	panel3.setLayout(panel3Layout);
	
	send   = new JButton("Send");
	cancel = new JButton("Cancel");
	
	panel3.add(send);
	panel3.add(cancel);
	
	send.addActionListener(this);
	cancel.addActionListener(this);
	
	// make the whole thing visible;
	frame.pack();
	frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
	Object buttonPressed = event.getSource();
	if (buttonPressed == send) {
	    String senderId = NetIO.myUserName();
	    senderId = Utils.leftPad(senderId, Globals.SENDER_LEN, '0');
	    if (senderId.length() == Globals.SENDER_LEN) {
		String receiverId = receiver.getText();
		receiverId = Utils.leftPad(receiverId, Globals.SENDER_LEN, '0');
		if (receiverId.length() == Globals.RECEIVER_LEN && Utils.isANumber(receiverId)) {
		    String entireMessage = Globals.SEND_MESSAGE +
					   senderId +
					   receiverId +
					   Utils.leftPad(Globals.STR_NULL, Globals.DATE_TIME_LEN, '0') +
					   Globals.FIRST_RECORD_OF_MESSAGE + 
					   subject.getText() +
					   Globals.END_OF_SUBJECT_MARKER +
					   messageText.getText();
		    
		    int errorCode = NetIO.sendRequest(entireMessage, Globals.SERVER_IP_ADDRESS);
		    if (errorCode == Globals.NET_OK) {
			frame.dispose();
		    }
		    else {
			JOptionPane.showMessageDialog(null,
						      "Message not delivered",
						      "ICS3U Bloor CI",
						      JOptionPane.ERROR_MESSAGE);
		    }
		}
		else {
		JOptionPane.showMessageDialog(null,
					      "Receiver Id must be 9 Digits",
					      "ICS3U Bloor CI",
					      JOptionPane.ERROR_MESSAGE);
		}
	    }
	    else {
		JOptionPane.showMessageDialog(null,
					      "Sender Id must be 9 Digits",
					      "ICS3U Bloor CI",
					      JOptionPane.ERROR_MESSAGE);
	    }
	}
	else if (buttonPressed == cancel) {
	    System.out.println("Cancel button was pressed");
	    frame.dispose();
	}
    }
    
    public static void main(String[] args) {
	EMailClientComposeMessage compose = new EMailClientComposeMessage();
    }
}

