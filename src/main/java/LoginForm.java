import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {

    private Container north;
    private JLabel title;
    private JPanel titlePanel;
    private JTextField email;
    private JPanel emailPanel;
    private JLabel emailLabel;
    private JPasswordField pass;
    private JPanel passPanel;
    private JLabel passLabel;

    private JButton submit;
    private JButton createAccount;
    private JPanel submitPanel;

    LoginForm() {
        super("BMTRS");

        titlePanel = new JPanel();
        title = new JLabel("BMTRS");
        titlePanel.add(title);

        emailPanel = new JPanel();
        emailLabel = new JLabel("Email: ");
        email = new JTextField();
        email.setPreferredSize(new Dimension(300,50));
        emailPanel.add(emailLabel);
        emailPanel.add(email);

        passPanel = new JPanel();
        passLabel = new JLabel("Password: ");
        pass = new JPasswordField();
        pass.setPreferredSize(new Dimension(300,50));
        passPanel.add(passLabel);
        passPanel.add(pass);

        submit = new JButton("Login");
        createAccount = new JButton("Create Account");
        submitPanel = new JPanel();
        submitPanel.add(createAccount);
        submitPanel.add(submit);
        submit.setActionCommand("submit");
        submit.addActionListener(this);
        createAccount.setActionCommand("createAccount");
        createAccount.addActionListener(this);

        north = getContentPane();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(titlePanel);
        north.add(emailPanel);
        north.add(passPanel);
        north.add(submitPanel);

        north.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.print("close");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });
        this.setSize(400,400);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if ("submit".equals(e.getActionCommand())) {
            System.out.println("Login attempt...");
            String password = "";
            for (char ch : pass.getPassword()) {
                password += ch;
            }
            if(DatabaseInterfacer.getInstance().checkInjection(email.getText())) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                BMTRS.focusLogin();
                this.dispose();
                return;
            }
            if(DatabaseInterfacer.getInstance().checkInjection(password)) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseInterfacer.getInstance()
                    .attemptLogin(email.getText().toLowerCase(), password)) {
                System.out.println(CurrentUserInfo.getInstance().getUser()
                        + " logged in.");
                BMTRS.focusWelcomeForm();
                this.setVisible(false);
            } else {
                System.out.println("Incorrect information.");
                JOptionPane.showMessageDialog(this,
                        "Either email or password is incorrect.",
                        "Login error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if ("createAccount".equals(e.getActionCommand())) {
            BMTRS.focusNewUserForm();
            this.dispose();
        }
    }
}
