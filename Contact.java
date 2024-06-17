import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Contact {
  private String id;
  private String name;
  private String phoneNumber;
  private String companyName;
  private double salary;
  private LocalDate birthday;

  private static Contact[] contacts = new Contact[0];

  public Contact(String id, String name, String phoneNumber, String companyName, double salary, String birthday) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.companyName = companyName;
    this.salary = salary;
    if (isValidDateFormat(birthday)) {
      this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else {
      throw new IllegalArgumentException("Invalid date format for birthday: " + birthday);
    }
    addContact(this);
  }

  private boolean isValidDateFormat(String date) {
    try {
      LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Getters and setters for encapsulation
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    if (isValidDateFormat(birthday)) {
      this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else {
      throw new IllegalArgumentException("Invalid date format for birthday: " + birthday);
    }
  }

  // Method to add a contact
  public static void addContact(Contact contact) {
    contacts = extendContacts();
    contacts[contacts.length - 1] = contact;
  }

  // Method to extend the contacts array
  public static Contact[] extendContacts() {
    Contact[] tempContacts = new Contact[contacts.length + 1];
    System.arraycopy(contacts, 0, tempContacts, 0, contacts.length);
    return tempContacts;
  }

  // Method to update a contact
  public static void updateContact(String id, Contact updatedContact) {
    for (int i = 0; i < contacts.length; i++) {
      if (contacts[i].getId().equals(id)) {
        contacts[i] = updatedContact;
        break;
      }
    }
  }

  // Method to get a contact by ID
  public static Contact getContactById(String id) {
    for (Contact contact : contacts) {
      if (contact.getId().equals(id)) {
        return contact;
      }
    }
    return null;
  }

  // Method to get a contact by name or phone number
  public static Contact getContactByNameOrPhone(String nameOrPhone) {
    for (Contact contact : contacts) {
      if (contact.getName().equalsIgnoreCase(nameOrPhone) || contact.getPhoneNumber().equals(nameOrPhone)) {
        return contact;
      }
    }
    return null;
  }

  // Method to delete a contact and shift subsequent contacts backward
  public static boolean deleteContactAndShift(String id) {
    int index = -1;
    for (int i = 0; i < contacts.length; i++) {
      if (contacts[i].getId().equals(id)) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      return false;
    }

    for (int i = index; i < contacts.length - 1; i++) {
      contacts[i] = contacts[i + 1];
    }

    contacts[contacts.length - 1] = null;

    contacts = Arrays.copyOf(contacts, contacts.length - 1);

    return true;
  }

  // Method to get all contacts
  public static Contact[] getAllContacts() {
    return Arrays.copyOf(contacts, contacts.length);
  }
}
