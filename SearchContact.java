import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchContact extends JFrame {

  private JTextField searchTextField;
  private JButton searchButton;
  private JLabel contactIdText;
  private JLabel nameText;
  private JLabel phoneNumberText;
  private JLabel companyText;
  private JLabel salaryText;
  private JLabel birthdayText;
  private Contact[] contacts;

  public SearchContact(Contact[] contacts) {
    this.contacts = contacts;

    setTitle("Update Contact");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("SEARCH CONTACT");
    titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    northPanel.add(titleLabel, BorderLayout.NORTH);

    titleLabel.setBackground(new Color(200, 220, 240));
    titleLabel.setOpaque(true);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel searchPanel = new JPanel();
    searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    searchTextField = new JTextField(20);
    searchButton = new JButton("Search");

    searchPanel.add(searchTextField);
    searchPanel.add(searchButton);

    northPanel.add(searchPanel, BorderLayout.CENTER);

    add(northPanel, BorderLayout.NORTH);

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(6, 1));
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    infoPanel.add(new JLabel("Contact Id:"));
    infoPanel.add(new JLabel("Name:"));
    infoPanel.add(new JLabel("Phone Number:"));
    infoPanel.add(new JLabel("Company:"));
    infoPanel.add(new JLabel("Salary:"));
    infoPanel.add(new JLabel("Birthday:"));

    add(infoPanel, BorderLayout.WEST);

    JPanel dataPanel = new JPanel();
    dataPanel.setLayout(new GridLayout(6, 1));
    dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    contactIdText = new JLabel();
    nameText = new JLabel();
    phoneNumberText = new JLabel();
    companyText = new JLabel();
    salaryText = new JLabel();
    birthdayText = new JLabel();

    dataPanel.add(contactIdText);
    dataPanel.add(nameText);
    dataPanel.add(phoneNumberText);
    dataPanel.add(companyText);
    dataPanel.add(salaryText);
    dataPanel.add(birthdayText);

    add(dataPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Dimension bottomBtnSize = new Dimension(225, 30);

    JPanel buttonPanel2 = new JPanel();
    buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton homeButton = new JButton("Go to Home Page");

    homeButton.setPreferredSize(bottomBtnSize);

    buttonPanel2.add(homeButton);

    buttonPanel.add(buttonPanel2);

    add(buttonPanel, BorderLayout.SOUTH);

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String searchText = searchTextField.getText();
        Contact contact = searchContact(searchText);
        if (contact != null) {
          populateContactDetails(contact);
        } else {
          JOptionPane.showMessageDialog(SearchContact.this, "Contact not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    homeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            new HomePage().setVisible(true);
          }
        });
      }
    });
  }

  private Contact searchContact(String searchText) {
    for (Contact contact : contacts) {
      if (contact.getName().equalsIgnoreCase(searchText) || contact.getPhoneNumber().equals(searchText)) {
        return contact;
      }
    }
    return null;
  }

  private void populateContactDetails(Contact contact) {
    contactIdText.setText(contact.getId());
    nameText.setText(contact.getName());
    phoneNumberText.setText(contact.getPhoneNumber());
    companyText.setText(contact.getCompanyName());
    salaryText.setText(String.valueOf(contact.getSalary()));
    birthdayText.setText(contact.getBirthday().toString());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Contact[] contacts = Contact.getAllContacts();
        new SearchContact(contacts).setVisible(true);
      }
    });
  }
}
