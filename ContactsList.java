import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Comparator;

public class ContactsList extends JFrame {
  private Contact[] contacts;

  public ContactsList(Contact[] contacts) {
    this.contacts = contacts;

    setTitle("iFRIEND CONTACT ORGANIZER");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    Font labelFont = new Font("Poppins", Font.BOLD, 24);
    JLabel titleLabel = new JLabel("CONTACT LIST", JLabel.CENTER);
    titleLabel.setFont(labelFont);

    titleLabel.setBackground(new Color(200, 220, 240));
    titleLabel.setOpaque(true);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Dimension btnSize = new Dimension(225, 30);
    JButton nameBtn = new JButton("List by Name");
    nameBtn.setPreferredSize(btnSize);
    JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    namePanel.add(nameBtn);

    nameBtn.addActionListener(new NameButtonListener());

    JButton salaryBtn = new JButton("List by Salary");
    salaryBtn.setPreferredSize(btnSize);
    JPanel salaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    salaryPanel.add(salaryBtn);

    salaryBtn.addActionListener(new SalaryButtonListener());

    JButton birthdayBtn = new JButton("List by Birthday");
    birthdayBtn.setPreferredSize(btnSize);
    JPanel birthdayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    birthdayPanel.add(birthdayBtn);

    birthdayBtn.addActionListener(new BirthdayButtonListener());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(namePanel);
    buttonPanel.add(salaryPanel);
    buttonPanel.add(birthdayPanel);

    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

    Dimension bottomBtnSize = new Dimension(150, 30);

    JButton homeBtn = new JButton("Go to Home Page");

    homeBtn.setPreferredSize(bottomBtnSize);

    JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    homePanel.add(homeBtn);

    homeBtn.addActionListener(new HomeButtonListener());

    JPanel homeButtonPanel = new JPanel();
    homeButtonPanel.setLayout(new BoxLayout(homeButtonPanel, BoxLayout.Y_AXIS));
    homeButtonPanel.add(homePanel);

    homeButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    setLayout(new BorderLayout());
    add(titleLabel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);
    add(homeButtonPanel, BorderLayout.SOUTH);
  }

  private class NameButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // Sort contacts by name
      Arrays.sort(contacts, Comparator.comparing(Contact::getName));
      showContacts("Contacts Sorted by Name", contacts, "NAME");
    }
  }

  private class SalaryButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // Sort contacts by salary
      Arrays.sort(contacts, Comparator.comparingDouble(Contact::getSalary));
      showContacts("Contacts Sorted by Salary", contacts, "SALARY");
    }
  }

  private class BirthdayButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      // Sort contacts by birthday
      Arrays.sort(contacts, Comparator.comparing(Contact::getBirthday));
      showContacts("Contacts Sorted by Birthday", contacts, "BIRTHDAY");
    }
  }

  private class HomeButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      ContactsList.this.dispose();
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          new HomePage().setVisible(true);
        }
      });
    }
  }

  private void showContacts(String title, Contact[] sortedContacts, String sortType) {
    JFrame listFrame = new JFrame(title);
    listFrame.setSize(700, 500);
    listFrame.setLocationRelativeTo(null);
    listFrame.setLayout(new BorderLayout(10, 10));

    JLabel sortLabel = new JLabel("SORTED BY " + sortType, JLabel.CENTER);
    sortLabel.setFont(new Font("Poppins", Font.BOLD, 16));
    sortLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    sortLabel.setBackground(new Color(200, 220, 240));
    sortLabel.setOpaque(true);
    sortLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    String[] columnNames = { "Contact ID", "Name", "Phone", "Company", "Salary", "Birthday" };

    String[][] data = new String[sortedContacts.length][columnNames.length];
    for (int i = 0; i < sortedContacts.length; i++) {
      Contact contact = sortedContacts[i];
      data[i][0] = contact.getId();
      data[i][1] = contact.getName();
      data[i][2] = contact.getPhoneNumber();
      data[i][3] = contact.getCompanyName();
      data[i][4] = String.valueOf(contact.getSalary());
      data[i][5] = contact.getBirthday().toString();
    }

    JTable table = new JTable(data, columnNames);
    table.setAutoCreateRowSorter(true);
    table.setFillsViewportHeight(true);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton backButton = new JButton("Back to Home");
    backButton.setPreferredSize(new Dimension(150, 30));
    buttonPanel.add(backButton);

    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listFrame.dispose();
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            new HomePage().setVisible(true);
          }
        });
      }
    });

    listFrame.add(sortLabel, BorderLayout.NORTH);
    listFrame.add(scrollPane, BorderLayout.CENTER);
    listFrame.add(buttonPanel, BorderLayout.SOUTH);

    listFrame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Contact[] contacts = Contact.getAllContacts();
        new ContactsList(contacts).setVisible(true);
      }
    });
  }
}
