import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageAccountForm extends JFrame implements ActionListener {

    private String user;
    private Container north;
    private JLabel title;
    private GridLayout table;
    private JButton logout;
    private JButton curatorRequest;
    private JButton deleteAccount;
    private JButton goBack;

    ManageAccountForm(String userName) {
        super(userName);
        user = userName;
        title = new JLabel("Manage Account");
        table = new GridLayout(0, 1);

        north = getContentPane();
        north.setLayout(table);
        north.add(title);

        logout = new JButton("Log Out");
        logout.setActionCommand("logout");
        logout.addActionListener(this);

        curatorRequest = new JButton("Curator Request");
        curatorRequest.setActionCommand("request");
        curatorRequest.addActionListener(this);

        deleteAccount = new JButton("Delete Account");
        deleteAccount.setActionCommand("delete");
        deleteAccount.addActionListener(this);

        goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        north.add(logout);
        north.add(curatorRequest);
        north.add(deleteAccount);
        north.add(goBack);
        north.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.print("museum page closed");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });
        this.setSize(600, 600);
        this.setVisible(true);
        this.setResizable(false);

    }

    public void actionPerformed(ActionEvent e) {
        if("logout".equals(e.getActionCommand())) {
            BMTRS.focusLogin();
            this.dispose();
        }

        if ("delete".equals(e.getActionCommand())) {
            BMTRS.focusDeleteAccountForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        }

        if ("request".equals(e.getActionCommand())) {
            BMTRS.focusCuratorRequestForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        }

        if("back".equals(e.getActionCommand())) {
            BMTRS.focusWelcomeForm();
            this.dispose();
        }
    }

}
