import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomePage extends JFrame {

  public HomePage() {
    setTitle("iFRIEND CONTACT ORGANIZER");
    setSize(700, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel leftPanel = new JPanel(new BorderLayout());

    JPanel titlePanel = new JPanel(new GridLayout(2, 1));

    Font labelFontTop = new Font("Poppins", Font.BOLD, 30);
    JLabel titleLabelTop = new JLabel("iFRIEND", JLabel.CENTER);
    titleLabelTop.setFont(labelFontTop);

    Font labelFontDown = new Font("Poppins", Font.BOLD, 24);
    JLabel titleLabelDown = new JLabel("Contact Manager", JLabel.CENTER);
    titleLabelDown.setFont(labelFontDown);

    titlePanel.add(titleLabelTop);
    titlePanel.add(titleLabelDown);

    titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel imageLabel = new JLabel();
    ImageIcon icon = new ImageIcon("contact_manager.png");
    Image image = icon.getImage();
    Image resizedImage = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(resizedImage);
    imageLabel.setIcon(resizedIcon);
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    leftPanel.add(titlePanel, BorderLayout.NORTH);
    leftPanel.add(imageLabel, BorderLayout.CENTER);

    JPanel rightPanel = new JPanel(new BorderLayout());

    rightPanel.setBackground(new Color(200, 220, 240));

    Font pageFont = new Font("Poppins", Font.BOLD, 24);
    JLabel pageLabel = new JLabel("Home Page", JLabel.CENTER);
    pageLabel.setFont(pageFont);
    pageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    Dimension btnSize = new Dimension(200, 30);

    JButton addBtn = createStyledButton("Add Contacts", btnSize);
    addBtn.addActionListener(e -> {
      HomePage.this.dispose();
      AddContact addContact = new AddContact();
      addContact.setVisible(true);
    });

    JButton updateBtn = createStyledButton("Update Contacts", btnSize);
    updateBtn.addActionListener(e -> {
      HomePage.this.dispose();
      Contact[] contacts = Contact.getAllContacts();
      UpdateContact updateContact = new UpdateContact(contacts);
      updateContact.setVisible(true);
    });

    JButton deleteBtn = createStyledButton("Delete Contacts", btnSize);
    deleteBtn.addActionListener(e -> {
      HomePage.this.dispose();
      Contact[] contacts = Contact.getAllContacts();
      new DeleteContact(contacts).setVisible(true);
    });

    JButton searchBtn = createStyledButton("Search Contacts", btnSize);
    searchBtn.addActionListener(e -> {
      HomePage.this.dispose();
      Contact[] contacts = Contact.getAllContacts();
      new SearchContact(contacts).setVisible(true);
    });

    JButton viewBtn = createStyledButton("View Contacts", btnSize);
    viewBtn.addActionListener(e -> {
      HomePage.this.dispose();
      Contact[] contacts = Contact.getAllContacts();
      new ContactsList(contacts).setVisible(true);
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(createButtonPanel(addBtn));
    buttonPanel.add(createButtonPanel(updateBtn));
    buttonPanel.add(createButtonPanel(deleteBtn));
    buttonPanel.add(createButtonPanel(searchBtn));
    buttonPanel.add(createButtonPanel(viewBtn));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    buttonPanel.setOpaque(false);

    Dimension exitBtnSize = new Dimension(100, 30);

    JButton exitBtn = createStyledButton("Exit", exitBtnSize);
    exitBtn.addActionListener(e -> System.exit(0));

    JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    exitPanel.add(exitBtn);
    exitPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    exitPanel.setOpaque(false);

    rightPanel.add(pageLabel, BorderLayout.NORTH);
    rightPanel.add(buttonPanel, BorderLayout.CENTER);
    rightPanel.add(exitPanel, BorderLayout.SOUTH);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    splitPane.setDividerLocation(getWidth() * 3 / 7);
    splitPane.setResizeWeight(3.0 / 7.0);

    getContentPane().add(splitPane);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        splitPane.setDividerLocation(getWidth() * 3 / 7);
      }
    });
  }

  private JButton createStyledButton(String text, Dimension size) {
    JButton button = new JButton(text);
    button.setPreferredSize(size);
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(230, 230, 230));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(Color.WHITE);
      }
    });

    return button;
  }

  private JPanel createButtonPanel(JButton button) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.add(button);
    panel.setOpaque(false);
    return panel;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      HomePage homePage = new HomePage();
      homePage.setVisible(true);
    });
  }
}
