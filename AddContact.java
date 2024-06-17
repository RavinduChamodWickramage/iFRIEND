import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddContact extends JFrame {

  private JLabel contactIdLabel;
  private JTextField nameTextField;
  private JTextField phoneNumberTextField;
  private JTextField companyTextField;
  private JTextField salaryTextField;
  private JTextField birthdayTextField;

  public AddContact() {
    setTitle("Add New Contact");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(2, 1));

    JLabel titleLabel = new JLabel("ADD CONTACT");
    titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    northPanel.add(titleLabel);

    titleLabel.setBackground(new Color(200, 220, 240));
    titleLabel.setOpaque(true);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel contactIdPanel = new JPanel();
    contactIdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    JLabel contactLabel = new JLabel("Contact ID:");
    contactIdLabel = new JLabel(generateId());
    contactIdLabel.setFont(new Font("Poppins", Font.BOLD, 18));
    contactIdPanel.add(contactLabel);
    contactIdPanel.add(contactIdLabel);

    northPanel.add(contactIdPanel);

    setLayout(new BorderLayout());
    add(northPanel, BorderLayout.NORTH);

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(5, 1));
    infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    JLabel nameLabel = new JLabel("Name:");
    JLabel phoneNumberLabel = new JLabel("Phone Number:");
    JLabel companyLabel = new JLabel("Company:");
    JLabel salaryLabel = new JLabel("Salary:");
    JLabel birthdayLabel = new JLabel("Birthday:");

    infoPanel.add(nameLabel);
    infoPanel.add(phoneNumberLabel);
    infoPanel.add(companyLabel);
    infoPanel.add(salaryLabel);
    infoPanel.add(birthdayLabel);

    add(infoPanel, BorderLayout.WEST);

    JPanel dataPanel = new JPanel();
    dataPanel.setLayout(new GridLayout(5, 1));
    dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    nameTextField = new JTextField();
    phoneNumberTextField = new JTextField();
    companyTextField = new JTextField();
    salaryTextField = new JTextField();
    birthdayTextField = new JTextField();

    dataPanel.add(nameTextField);
    dataPanel.add(phoneNumberTextField);
    dataPanel.add(companyTextField);
    dataPanel.add(salaryTextField);
    dataPanel.add(birthdayTextField);

    add(dataPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 1));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Dimension topLeftBtnSize = new Dimension(150, 30);
    Dimension topRightBtnSize = new Dimension(75, 30);
    Dimension bottomBtnSize = new Dimension(225, 30);

    JPanel buttonPanel1 = new JPanel();
    buttonPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton addButton = new JButton("Add Contact");
    JButton cancelButton = new JButton("Cancel");

    addButton.setPreferredSize(topLeftBtnSize);
    cancelButton.setPreferredSize(topRightBtnSize);

    buttonPanel1.add(addButton);
    buttonPanel1.add(cancelButton);

    JPanel buttonPanel2 = new JPanel();
    buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JButton homeButton = new JButton("Back To Home Page");

    homeButton.setPreferredSize(bottomBtnSize);

    buttonPanel2.add(homeButton);

    buttonPanel.add(buttonPanel1);
    buttonPanel.add(buttonPanel2);

    add(buttonPanel, BorderLayout.SOUTH);

    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Get data from text fields
        String name = nameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String company = companyTextField.getText();
        String salaryStr = salaryTextField.getText();
        String birthday = birthdayTextField.getText();

        // Validate phone number
        if (!isValidPhoneNumber(phoneNumber)) {
          showErrorDialog("Invalid phone number format. Please enter a valid 10-digit phone number starting with 0.");
          return;
        }

        // Validate salary
        double salary;
        try {
          salary = Double.parseDouble(salaryStr);
          if (salary <= 0) {
            throw new NumberFormatException();
          }
        } catch (NumberFormatException ex) {
          showErrorDialog("Invalid salary format. Please enter a valid positive number.");
          return;
        }

        // Validate the birthday format and if it's a future date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
          LocalDate parsedDate = LocalDate.parse(birthday, formatter);
          LocalDate currentDate = LocalDate.now();
          if (parsedDate.isAfter(currentDate)) {
            showErrorDialog("Invalid birthday. Please enter a valid date.");
            return;
          }
        } catch (DateTimeParseException ex) {
          showErrorDialog("Invalid birthday format. Please use YYYY-MM-DD.");
          return;
        }

        // Create a new Contact object
        new Contact(generateId(), name, phoneNumber, company, salary, birthday);

        // Show success message
        JOptionPane.showMessageDialog(AddContact.this, "Contact added successfully!");

        // Clear text fields
        nameTextField.setText("");
        phoneNumberTextField.setText("");
        companyTextField.setText("");
        salaryTextField.setText("");
        birthdayTextField.setText("");

        // Update contact ID
        contactIdLabel.setText(generateId());
      }
    });

    cancelButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        nameTextField.setText("");
        phoneNumberTextField.setText("");
        companyTextField.setText("");
        salaryTextField.setText("");
        birthdayTextField.setText("");
      }
    });

    homeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
          }
        });
      }
    });

    buttonPanel.add(buttonPanel1);
    buttonPanel.add(buttonPanel2);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  // Method to generate contact ID
  private String generateId() {
    Contact[] allContacts = Contact.getAllContacts();
    if (allContacts.length == 0) {
      return "C0001";
    } else {
      String lastId = allContacts[allContacts.length - 1].getId();
      int lastIdNum = Integer.parseInt(lastId.substring(1));
      return String.format("C%04d", lastIdNum + 1);
    }
  }

  // Method to validate phone number format
  private boolean isValidPhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^0\\d{9}$");
  }

  // Method to display error dialog
  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(AddContact.this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        AddContact addContact = new AddContact();
        addContact.setVisible(true);
      }
    });
  }
}
