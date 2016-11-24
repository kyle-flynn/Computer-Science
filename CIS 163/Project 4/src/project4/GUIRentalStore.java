package project4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public class GUIRentalStore implements ActionListener {

    private JFrame frame;

    private JMenuBar menus;

    private JMenu fileMenu;
    private JMenu actionMenu;

    // fileMenu
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;

    // auto Menu
    private JMenuItem rentDvdItem;
    private JMenuItem rentGameItem;
    private JMenuItem returnItem;
    boolean filter;


    private JList jListArea;

    private ListEngine dList;

    //private
    public GUIRentalStore() {
        frame = new JFrame();

        fileMenu = new JMenu("File");

        openSerItem = new JMenuItem("Open Serial...");
        saveSerItem = new JMenuItem("Save Serial...");
        exitItem = new JMenuItem("Exit!");
        openTextItem = new JMenuItem("Open Text...");
        saveTextItem = new JMenuItem("Save Text...");
        actionMenu = new JMenu("Action");
        rentDvdItem = new JMenuItem("Rent DVD");
        rentGameItem = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");

        menus = new JMenuBar();
        dList = new ListEngine();
        jListArea = new JList(dList);
    }

    public void init() {
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        actionMenu.add(rentDvdItem);
        actionMenu.add(rentGameItem);
        actionMenu.addSeparator();
        actionMenu.add(returnItem);
        actionMenu.addSeparator();

        rentDvdItem.addActionListener(this);
        rentGameItem.addActionListener(this);
        returnItem.addActionListener(this);

        openSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);

        menus.add(fileMenu);
        menus.add(actionMenu);

        frame.setJMenuBar(menus);
        frame.add(jListArea);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GUIRentalStore GUI = new GUIRentalStore();
        GUI.init();
    }

    public void actionPerformed(ActionEvent e) {

        JComponent comp = (JComponent) e.getSource();

        if (exitItem == comp)
            System.exit(0);

        if (openSerItem == comp || openTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp)
                    dList.loadDatabase(filename);
                if (openTextItem == e.getSource())
                    dList.loadFromText(filename);
            }
        }

        if (saveSerItem == comp || saveTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    dList.saveDatabase(filename);
                if (saveTextItem == e.getSource())
                    dList.saveAsText(filename);
            }
        }

        if (rentDvdItem == comp) {
            DVD dvd = new DVD();
            DialogRentDvd x = new DialogRentDvd(frame, dvd);
            if (x.closeOK())
                dList.add(dvd);
        }


        if (rentGameItem == comp) {
            Game game = new Game();
            DialogRentGame x = new DialogRentGame(frame, game);
            if (x.closeOK())
                dList.add(game);
        }

        if (returnItem == e.getSource()) {
            int index = jListArea.getSelectedIndex();
            if (index != -1) {
            	GregorianCalendar dat = new GregorianCalendar();
				
				DVD unit = dList.remove(index);
				JOptionPane.showMessageDialog(null, "Thanks " + unit.getNameOfRenter() + 
						"\n for returning " + unit.getTitle() + ", you owe: " + unit.getCost(dat) +
						" dollars");

            }
        }
    }
}


