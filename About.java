package TextEditorApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class About extends JFrame implements ActionListener{
	
	public static final String companyName = "TextEditor";
	
	public static final String AboutCompany= ""
			+ "<html>"
			+ "<hr/>"
			+ "<br/>"
			+ "Version : 1.0"
			+ "<br/>"
			+ "Build : 01-2023"
			+ "<br/>"
			+ "<br/>"
			+ "Welcome to " + companyName
			+ "<br/>"
			+ companyName + " is a simple and powerful editor for "
			+ System.getProperty("os.name") + " and a basic text editing software "
			+ "which enables computer user to creates and edits plain text documents."
			+ "<br/>"
			+ "<br/>"
			+ "Copyright 2023-24 © Logical Group Inc."
			+ "<br/>"
			+ "All rights reserved."
			+ "</html>";
	
	JButton okBtn = new JButton("OK");
	public About() {
		setTitle("About " + companyName);
		setSize(500,500);
		setResizable(false);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		
		// setting logo for our software
		ImageIcon logo = new ImageIcon(getClass().getResource("TextEditor.png"));
		setIconImage(logo.getImage());
		
		//resizing the image so that it can fit in the our label
		ImageIcon image = new ImageIcon(getClass().getResource("TextEditor.png"));
		Image img = image.getImage();
		Image new_img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		image = new ImageIcon(new_img);
		
		// section 1 : Our application main heading
		Font font = new Font("Verdana", Font.BOLD, 50);
		JLabel heading = new JLabel(companyName, image, JLabel.LEFT);
		heading.setFont(font);
		heading.setBounds(50,30,500,100);
		add(heading);
		
		//section 2 : About the application
		JLabel content = new JLabel(AboutCompany);
		content.setBounds(50,150,380,250);
		content.setFont(new Font("Verdana", Font.PLAIN, 15));
		add(content);
		
		okBtn.setBounds(400,420,70,30);
		add(okBtn);
		okBtn.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
	
	
	public static void main(String[] args) {
		new About().setVisible(true);
	}

	
}