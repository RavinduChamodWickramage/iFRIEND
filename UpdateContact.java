import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdateContact extends JFrame {

  private JTextField searchTextField;
  private JButton searchButton;
  private JLabel contactIdText;
  private JTextField nameText;
  private JTextField phoneNumberText;
  private JTextField companyText;
  private JTextField salaryText;
  private JTextField birthdayText;

  public UpdateContact(Contact[] contacts) {

    setTitle("Update Contact");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("UPDATE CONTACT");
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
    nameText = new JTextField();
    phoneNumberText = new JTextField();
    companyText = new JTextField();
    salaryText = new JTextField();
    birthdayText = new JTextField();

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

    JButton updateButton = new JButton("Update Contact");
    JButton cancelButton = new JButton("Cancel");

    updateButton.setPreferredSize(topLeftBtnSize);
    cancelButton.setPreferredSize(topRightBtnSize);

    buttonPanel1.add(updateButton);
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
          JOptionPane.showMessageDialog(UpdateContact.this, "Contact not found", "Error", JOptionPane.ERROR_MESSAGE);
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

    updateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String searchText = searchTextField.getText();
        Contact contact = Contact.getContactByNameOrPhone(searchText);
        if (contact != null) {
          String name = nameText.getText();
          String phoneNumber = phoneNumberText.getText();
          String company = companyText.getText();
          String salaryStr = salaryText.getText();
          String birthday = birthdayText.getText();

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

          contact.setName(name);
          contact.setPhoneNumber(phoneNumber);
          contact.setCompanyName(company);
          contact.setSalary(salary);
          contact.setBirthday(birthday);

          Contact.updateContact(contact.getId(), contact);
          JOptionPane.showMessageDialog(UpdateContact.this, "Contact updated successfully!", "Success",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(UpdateContact.this, "Contact not found", "Error", JOptionPane.ERROR_MESSAGE);
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

  // Method to validate phone number format
  private boolean isValidPhoneNumber(String phoneNumber) {
    return phoneNumber.matches("^0\\d{9}$");
  }

  // Method to display error dialog
  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(UpdateContact.this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Contact[] contacts = Contact.getAllContacts();
        new UpdateContact(contacts).setVisible(true);
      }
    });
  }
}
