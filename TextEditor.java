package TextEditorApp;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener{
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu edit = new JMenu("Edit");
	JMenu format = new JMenu("Format");
	JMenu help = new JMenu("Help");
	JTextPane textPane = new JTextPane();
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPane;

	JMenuItem newFile = new JMenuItem("New");
	JMenuItem openFile = new JMenuItem("Open");
	JMenuItem saveFile = new JMenuItem("Save");
	JMenuItem printFile = new JMenuItem("Print");
	JMenuItem exit = new JMenuItem("Exit");
	
	JCheckBoxMenuItem wordWrap = new JCheckBoxMenuItem("Word Wrap");
	
	JMenuItem cut = new JMenuItem("Cut");
	JMenuItem copy = new JMenuItem("Copy");
	JMenuItem paste = new JMenuItem("Paste");
	JMenuItem selectAll = new JMenuItem("Select All");
	JMenuItem undo = new JMenuItem("Undo");
	JMenuItem redo = new JMenuItem("Redo");
	
	UndoManager manager;
	JMenuItem about = new JMenuItem("About");
	JFileChooser fileChooser;
	Cursor cur;
	public TextEditor() {
		setTitle("Text Editor");
		setSize(800,600);
		setResizable(true);
		setLocationRelativeTo(null);
		
		manager = new UndoManager();
		cur = new Cursor(Cursor.HAND_CURSOR);
		setCursor(cur);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon logo = new ImageIcon(getClass().getResource("TextEditor.png"));
		setIconImage(logo.getImage());
	
		file.add(newFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(printFile);
		file.add(exit);
		
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(selectAll);
		edit.add(undo);
		edit.add(redo);
		
		format.add(wordWrap);
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(format);
		menuBar.add(help);
		
		
		setJMenuBar(menuBar);
		
		// adding textArea and configuring it
		Font font = new Font("Roman",Font.PLAIN,15);
		textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		
		// adding events
		newFile.addActionListener(this);
		saveFile.addActionListener(this);
		openFile.addActionListener(this);
		printFile.addActionListener(this);
		exit.addActionListener(this);
		
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		selectAll.addActionListener(this);
		undo.addActionListener(this);
		redo.addActionListener(this);
		
		
		wordWrap.addActionListener(this);
		about.addActionListener(this);		
		
		// creating shortcuts
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
		printFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,KeyEvent.CTRL_DOWN_MASK));
		
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_DOWN_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_DOWN_MASK));
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_DOWN_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		
		wordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
		
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK));
		
		
		
		// file chooser configuration
		fileChooser = new JFileChooser("C:\\");
		fileChooser.setMultiSelectionEnabled(false);
		
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filterForText = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
		FileNameExtensionFilter filterForJava = new FileNameExtensionFilter("Java Files (*.java)", "java");
		FileNameExtensionFilter filterForHtml = new FileNameExtensionFilter("Html Files (*.html)", "html");
		FileNameExtensionFilter filterForJS = new FileNameExtensionFilter("JavaScript Files (*.js)", "js");
		fileChooser.addChoosableFileFilter(filterForText);
		fileChooser.addChoosableFileFilter(filterForJava);
		fileChooser.addChoosableFileFilter(filterForHtml);
		fileChooser.addChoosableFileFilter(filterForJS);
		
		wordWrap.setSelected(true);

		textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				manager.addEdit(e.getEdit());
			}
		});
		
		setVisible(true);
		revalidate();
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("New")) {
			
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure to create new file?\nYou will loose previous content.", "New File", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(choice==JOptionPane.YES_OPTION) {
				textArea.setText(null);
			}
			else {
	
			}
			
		}
		else if(e.getActionCommand().equalsIgnoreCase("Open")) {
			
			int selectedOption = fileChooser.showOpenDialog(null);
			if(selectedOption == fileChooser.APPROVE_OPTION) {
				
				String fileName = fileChooser.getSelectedFile().getAbsolutePath().toString();
				
				BufferedReader bf;
				try {
					bf = new BufferedReader(new FileReader(fileName));
					textArea.read(bf, null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Save")) {
			int selectedOption = fileChooser.showSaveDialog(null);
			if(selectedOption == fileChooser.APPROVE_OPTION) {
				
				String fileName = fileChooser.getSelectedFile().getAbsolutePath().toString();
				
				BufferedWriter bf;
				try {
					bf = new BufferedWriter(new FileWriter(fileName));
					textArea.write(bf);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Print")) {
			try {
				textArea.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Exit")) {
			int choice = JOptionPane.showConfirmDialog(this, "Do you want to Exit?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(choice==JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Cut")) {
			textArea.cut();
		}
		else if(e.getActionCommand().equalsIgnoreCase("Copy")) {
			textArea.copy();
		}
		else if(e.getActionCommand().equalsIgnoreCase("Paste")) {
			textArea.paste();
		}
		else if(e.getActionCommand().equalsIgnoreCase("Select All")) {
			textArea.selectAll();
		}
		else if(e.getActionCommand().equalsIgnoreCase("Undo")) {
			try {
				manager.undo();
			} catch (Exception ex) {}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Redo")) {
			try {
				manager.redo();
			} catch (Exception ex) {}
		}
		else if(e.getActionCommand().equalsIgnoreCase("Word Wrap")) {
			
			if (wordWrap.isSelected()) {
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			} else {
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				textArea.setLineWrap(false);
				textArea.setWrapStyleWord(false);
		
			}
			
		}
		else if(e.getActionCommand().equalsIgnoreCase("About")) {
			new About().setVisible(true);
		}
		
	}

	public static void main(String[] args) {
		new TextEditor();
	}


}
