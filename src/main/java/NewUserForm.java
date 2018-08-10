import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.Calendar;

public class NewUserForm extends JFrame implements ActionListener {

    private Container container;
    private JLabel title;
    private JTextField email;
    private JLabel emailLabel;
    private JPasswordField pass;
    private JLabel passLabel;
    private JTextField creditCard;
    private JLabel creditCardLabel;
    private JTextField expMonth;
    private JLabel expMonthLabel;
    private JTextField expYear;
    private JLabel expYearLabel;
    private JTextField securityCode;
    private JLabel securityCodeLabel;
    private JButton createAccountButton;
    private JButton backButton;
    private GridLayout gridLayout;


    NewUserForm() {
        super("Registration");

        title = new JLabel("New User Registration");

        emailLabel = new JLabel("Email: ");
        email = new JTextField();

        passLabel = new JLabel("Password: ");
        pass = new JPasswordField();

        creditCardLabel = new JLabel("Credit Card Number: ");
        creditCard = new JTextField();

        expMonthLabel = new JLabel("Credit Card Exp. Month: ");
        expMonth = new JTextField();

        expYearLabel = new JLabel("Credit Card Exp. Year: ");
        expYear = new JTextField();

        securityCodeLabel = new JLabel("Credit Card Security Code: ");
        securityCode = new JTextField();

        gridLayout = new GridLayout(0,2);
        container = getContentPane();
        container.setLayout(gridLayout);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setActionCommand("create");
        createAccountButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        container.add(title);
        container.add(new Label(""));
        container.add(emailLabel);
        container.add(email);
        container.add(passLabel);
        container.add(pass);
        container.add(creditCardLabel);
        container.add(creditCard);
        container.add(expMonthLabel);
        container.add(expMonth);
        container.add(expYearLabel);
        container.add(expYear);
        container.add(securityCodeLabel);
        container.add(securityCode);
        container.add(createAccountButton);
        container.add(backButton);

        container.setVisible(true);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.print("close");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });

        this.setVisible(true);
        this.setSize(400, 400);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if ("back".equals(e.getActionCommand())) {
            BMTRS.focusLogin();
            this.dispose();
        } else if (("create".equals(e.getActionCommand()))){
            int intExpYear;
            int intExpMonth;
            long longCCNum;
            int intCCV;

            if(DatabaseInterfacer.getInstance().checkInjection(email.getText())) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String password = "";
            for (char ch : pass.getPassword()) {
                password += ch;
            }
            if(DatabaseInterfacer.getInstance().checkInjection(password)) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.getText().length() < 1 || email.getText().length() >= 254) {
                JOptionPane.showMessageDialog(this, "Email must not be empty and must have less than 255 characters.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (!(email.getText().contains("@") || email.getText().contains("."))) {
                    JOptionPane.showMessageDialog(this, "Email must contain '@' and '.'",
                            "Creation error", JOptionPane.ERROR_MESSAGE);
                    return;
            } else if (pass.getPassword().length < 8 || pass.getPassword().length >= 30) {
                JOptionPane.showMessageDialog(this, "Password must not be under 8 characters and must have less than 31 characters.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                longCCNum = Long.parseLong(creditCard.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Credit Card Number must be an integer.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                intExpMonth = Integer.parseInt(expMonth.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Expiration Month must be an integer.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                intExpYear = Integer.parseInt(expYear.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Expiration Year must be an integer.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                intCCV = Integer.parseInt(securityCode.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Security Code must be an integer.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (creditCard.getText().length() != 16) {
                JOptionPane.showMessageDialog(this, "Credit Card must not be empty and must have 16 characters.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (securityCode.getText().length() < 3 || securityCode.getText().length() > 4) {
                JOptionPane.showMessageDialog(this, "Security Code must not be empty and must have 3 or 4 characters.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (expYear.getText().length() != 4 || intExpYear < Year.now().getValue()) {
                JOptionPane.showMessageDialog(this, "Expiration Year must have 4 digits and must not be in the past.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (intExpMonth < 1 || intExpMonth > 12) {
                JOptionPane.showMessageDialog(this, "Expiration Month must be a valid 1 or 2 digit number",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (intExpYear == Year.now().getValue()) {
                if (intExpMonth <= Calendar.getInstance().get(Calendar.MONTH)) {
                    JOptionPane.showMessageDialog(this, "If Expiration Year is this year, Expiration Month must be in the future.",
                            "Creation error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (DatabaseInterfacer.getInstance().createUser(email.getText().toLowerCase(), password, longCCNum, intCCV, intExpYear, intExpMonth)) {
                JOptionPane.showMessageDialog(this, "Successfully created new user: " + email.getText(),
                        "Successful creation.", JOptionPane.PLAIN_MESSAGE);
                System.out.println("Created new user.");
            } else {
                JOptionPane.showMessageDialog(this, "Failure. User may already exist.",
                        "Creation error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}