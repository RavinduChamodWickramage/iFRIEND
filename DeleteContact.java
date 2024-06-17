import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteContact extends JFrame {

  private JTextField searchTextField;
  private JButton searchButton;
  private JLabel contactIdText;
  private JLabel nameText;
  private JLabel phoneNumberText;
  private JLabel companyText;
  private JLabel salaryText;
  private JLabel birthdayText;

  public DeleteContact(Contact[] contacts) {

    setTitle("Delete Contact");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("DELETE CONTACT");
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

    Dimension topLeftBtnSize = new Dimension(150, 30);
    Dimension topRightBtnSize = new Dimension(75, 30);
    Dimension bottomBtnSize = new Dimension(225, 30);

    JPanel buttonPanel1 = new JPanel();
    buttonPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton deleteButton = new JButton("Delete Contact");
    JButton cancelButton = new JButton("Cancel");

    deleteButton.setPreferredSize(topLeftBtnSize);
    cancelButton.setPreferredSize(topRightBtnSize);

    buttonPanel1.add(deleteButton);
    buttonPanel1.add(cancelButton);

    JPanel buttonPanel2 = new JPanel();
    buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton homeButton = new JButton("Go to Home Page");

    homeButton.setPreferredSize(bottomBtnSize);

    buttonPanel2.add(homeButton);

    buttonPanel.add(buttonPanel1);
    buttonPanel.add(buttonPanel2);

    add(buttonPanel, BorderLayout.SOUTH);

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String searchText = searchTextField.getText();
        Contact contact = Contact.getContactByNameOrPhone(searchText);
        if (contact != null) {
          populateContactDetails(contact);
        } else {
          JOptionPane.showMessageDialog(DeleteContact.this, "Contact not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clearContactDetails();
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

    // Delete button action listener
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String searchText = searchTextField.getText();
        Contact contact = Contact.getContactByNameOrPhone(searchText);
        if (contact != null) {
          int confirmation = JOptionPane.showConfirmDialog(DeleteContact.this,
              "Are you sure you want to delete this contact?", "Confirm Delete",
              JOptionPane.YES_NO_OPTION);

          if (confirmation == JOptionPane.YES_OPTION) {
            boolean isDeleted = Contact.deleteContactAndShift(contact.getId());
            if (isDeleted) {
              JOptionPane.showMessageDialog(DeleteContact.this, "Contact deleted successfully!", "Success",
                  JOptionPane.INFORMATION_MESSAGE);
              clearContactDetails();
            } else {
              JOptionPane.showMessageDialog(DeleteContact.this, "Error deleting contact", "Error",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        } else {
          JOptionPane.showMessageDialog(DeleteContact.this, "Contact not found", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private void populateContactDetails(Contact contact) {
    contactIdText.setText(contact.getId());
    nameText.setText(contact.getName());
    phoneNumberText.setText(contact.getPhoneNumber());
    companyText.setText(contact.getCompanyName());
    salaryText.setText(String.valueOf(contact.getSalary()));
    birthdayText.setText(contact.getBirthday().toString());
  }

  private void clearContactDetails() {
    contactIdText.setText("");
    nameText.setText("");
    phoneNumberText.setText("");
    companyText.setText("");
    salaryText.setText("");
    birthdayText.setText("");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Contact[] contacts = Contact.getAllContacts();
        new DeleteContact(contacts).setVisible(true);
      }
    });
  }
}
