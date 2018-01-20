import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EmailClientGUI implements ActionListener {
    private JFrame frame = null;
    private JPanel panel1 = null;
    private JPanel panel2 = null;
    private JPanel panel3 = null;
    private EmailClientPane eMailClientPane = null;
    private JLabel label = null;
    private JButton compose = null;
    private JButton getMail = null;
    private JButton delete = null;
    private JButton inbox = null;
    private JButton outbox = null;
    
    public EmailClientGUI() {
	frame = new JFrame("Bloor CI Email Client Version 2016.0");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setLocation(100, 50);
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
	
	//panel1
	FlowLayout panel1layout = new FlowLayout(FlowLayout.LEFT);
	panel1.setLayout(panel1layout);
	
	label = new JLabel("From                                                Date Received                                                          Subject");
	panel1.add(label);
	
	//panel2
	FlowLayout panel2Layout = new FlowLayout(FlowLayout.LEFT);
	panel2.setLayout(panel2Layout);
	eMailClientPane = new EmailClientPane();
	panel2.add(eMailClientPane.getSplitPane());
	
	//panel3
	FlowLayout panel3Layout = new FlowLayout(FlowLayout.CENTER);
	panel3.setLayout(panel3Layout);
	
	compose   = new JButton("Compose");
	getMail = new JButton("Get Mail");
	delete = new JButton("Delete");
	inbox = new JButton("InBox");
	outbox = new JButton("OutBox");
	
	compose.addActionListener(this);
	getMail.addActionListener(this);
	delete.addActionListener(this);
	inbox.addActionListener(this);
	outbox.addActionListener(this);
	
	panel3.add(compose);
	panel3.add(getMail);
	panel3.add(delete);
	panel3.add(inbox);
	panel3.add(outbox);
	
	//pack and make visible
	frame.pack();
	frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
	Object buttonPressed = event.getSource();
	
	if (buttonPressed == compose) {
	    EMailClientComposeMessage c = new EMailClientComposeMessage();
	}
    }
}
