import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class diplom {
    
    public static void main(String[] args) {
        new diplom();
    }
    
    private ArrayList<String> rooms = new ArrayList<>();        //список комнат
    private ArrayList<Client> clientInfo = new ArrayList();     //список бронирований по текущей комнате
    private String currentRoom = null;                          //текущая комната
    private String startDate = null;                            //начало периода
    private String endDate = null;                              //конец периода
    private int currentClient = 0;                              //текущее количество бронировавших клиентов
    
    DBConnector connector;                      //Класс соединения с базой данных
    
    //main frame
    JFrame mainFrame;
    JPanel mainPanel;
    
    JList list;                                 //room list
    JLabel label1;                              //"Забронировано"
    JLabel label2;                              //"Заказано"
    JTextField CountBookingFromDB;              //count of booking by current client
    JTextField CountClientRoomFromDB;           //count of client_room by current client
    JButton searchButton;                       //search button
    JTextField f1;
    JTextField f2;
    JTextField f3;
    JTextField f4;
    JButton prevButton;
    JButton nextButton;
    
    //search parametrs frame
    JFrame parFrame;
    JPanel parPanel;
    JLabel infoLabel2_1;
    JTextField startTextField;
    JTextField endTextField;
    JLabel infoLabel2_2;
    JButton confirmButton;
    
    diplom() {
        loadBack();
        loadMainFrame();
    }
    
    private void loadBack()
    {
        connector = new DBConnector();
        rooms = connector.getRoomsFromDB();
    }
    
    private void loadMainFrame()
    {
        mainFrame = new JFrame("Лабораторная работа №8");
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainFrame.add(mainPanel);
        
        list = new JList(new Vector<>(rooms));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSize(150, 190);
        list.setLocation(50, 50);
        mainPanel.add(list);
        
        ListSelectionListener listSelectionListener;        //change selected list list item
        listSelectionListener = (ListSelectionEvent e) -> {
            currentRoom = ((String) list.getSelectedValue());
            currentClient = 0;
            updateFirstGroup();
            updateSecondGroup();
        };
        list.addListSelectionListener(listSelectionListener);
        
        label1 = new JLabel("Бронировано");
        label1.setSize(100, 25);
        label1.setLocation(225, 50);
        mainPanel.add(label1);
        
        label2 = new JLabel("Заказано");
        label2.setSize(100, 25);
        label2.setLocation(225, 100);
        mainPanel.add(label2);
        
        CountBookingFromDB = new JTextField("0");
        CountBookingFromDB.setEditable(false);
        CountBookingFromDB.setSize(100, 25);
        CountBookingFromDB.setLocation(350, 50);
        mainPanel.add(CountBookingFromDB);
        
        CountClientRoomFromDB = new JTextField("0");
        CountClientRoomFromDB.setEditable(false);
        CountClientRoomFromDB.setSize(100, 25);
        CountClientRoomFromDB.setLocation(350, 100);
        mainPanel.add(CountClientRoomFromDB);
        
        searchButton = new JButton("Поиск");
        searchButton.addActionListener((ActionEvent e) -> {
            if(currentRoom != null)
                loadSecondFrame();
        });
        searchButton.setSize(100, 50);
        searchButton.setLocation(300, 150);
        mainPanel.add(searchButton);
        
        f1 = new JTextField();
        f1.setEditable(false);
        f1.setVisible(false);
        f1.setSize(75, 25);
        f1.setLocation(50, 250);
        mainPanel.add(f1);
        
        f2 = new JTextField();
        f2.setVisible(false);
        f2.setEditable(false);
        f2.setSize(75, 25);
        f2.setLocation(150, 250);
        mainPanel.add(f2);
        
        f3 = new JTextField();
        f3.setVisible(false);
        f3.setEditable(false);
        f3.setSize(75, 25);
        f3.setLocation(250, 250);
        mainPanel.add(f3);
        
        f4 = new JTextField();
        f4.setVisible(false);
        f4.setEditable(false);
        f4.setSize(75, 25);
        f4.setLocation(350, 250);
        mainPanel.add(f4);
        
        prevButton = new JButton("<<");
        prevButton.setEnabled(false);
        prevButton.setSize(50, 25);
        prevButton.setLocation(175, 300);
        prevButton.addActionListener((ActionEvent e) -> {
            currentClient = currentClient - 1;
            updateSecondGroup();
        });
        mainPanel.add(prevButton);
        
        nextButton = new JButton(">>");
        nextButton.setEnabled(false);
        nextButton.setSize(50, 25);
        nextButton.setLocation(275, 300);
        nextButton.addActionListener((ActionEvent e) -> {
            currentClient = currentClient + 1;
            updateSecondGroup();
        });
        mainPanel.add(nextButton);
        
        //open frame
        mainFrame.setPreferredSize(new Dimension(500, 400));
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
    
    private void loadSecondFrame()
    {
        parFrame = new JFrame("Ввод параметров");
        
        parPanel = new JPanel();
        parPanel.setLayout(null);
        parFrame.add(parPanel);
        
        infoLabel2_1 = new JLabel("Input date in format: YYYY-MM-DD");
        infoLabel2_1.setSize(220, 25);
        infoLabel2_1.setLocation(10, 0);
        parPanel.add(infoLabel2_1);
        
        startTextField = new JTextField();
        startTextField.setSize(100, 25);
        startTextField.setLocation(10, 30);
        parPanel.add(startTextField);
        
        endTextField = new JTextField();
        endTextField.setSize(100, 25);
        endTextField.setLocation(120, 30);
        parPanel.add(endTextField);
        
        infoLabel2_2 = new JLabel();
        infoLabel2_2.setVisible(false);
        infoLabel2_2.setSize(220, 25);
        infoLabel2_2.setLocation(10, 60);
        parPanel.add(infoLabel2_2);
        
        confirmButton = new JButton("OK");
        confirmButton.setSize(75, 25);
        confirmButton.setLocation(75, 90);
        confirmButton.addActionListener((ActionEvent e) -> {
            startDate = startTextField.getText();
            endDate = endTextField.getText();
            if(connector.getClientInfoFromDB(currentRoom, startDate, endDate).size() > 0)
            {
                updateSecondGroup();
                parFrame.dispose();
            } else {
                infoLabel2_2.setText("Совпадений не найдно!");
                infoLabel2_2.setVisible(true);
            }
        });
        parPanel.add(confirmButton);
        
        parFrame.setPreferredSize(new Dimension(235, 150));
        parFrame.pack();
        parFrame.setLocationRelativeTo(null);
        parFrame.setResizable(false);
        parFrame.setVisible(true);
    }
    
    private void updateFirstGroup()
    {
        CountBookingFromDB.setText("" + connector.getCountBookingFromDB(currentRoom));
        CountClientRoomFromDB.setText("" + connector.getCountClientRoomFromDB(currentRoom));
    }
    
    private void updateSecondGroup()
    {
        clientInfo = connector.getClientInfoFromDB(currentRoom, startDate, endDate);
        
        if(clientInfo.size() > 0)
        {
            f1.setText(clientInfo.get(currentClient).getLastName());
            f2.setText(clientInfo.get(currentClient).getFirstName());
            f3.setText(clientInfo.get(currentClient).getStartDate());
            f4.setText(clientInfo.get(currentClient).getFinDate());
            f1.setVisible(true);
            f2.setVisible(true);
            f3.setVisible(true);
            f4.setVisible(true);
            if(clientInfo.size() > 1)
            {
                if(currentClient > 0)
                    prevButton.setEnabled(true);
                else
                    prevButton.setEnabled(false);
                if(currentClient < clientInfo.size() - 1)
                    nextButton.setEnabled(true);
                else
                    nextButton.setEnabled(false);
                
            }
            else if(clientInfo.size() == 1) {
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
            }
            
        }
        else
        {
            f1.setVisible(false);
            f2.setVisible(false);
            f3.setVisible(false);
            f4.setVisible(false);
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }
    
}
