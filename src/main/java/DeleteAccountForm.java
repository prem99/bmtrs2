import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountForm extends JFrame implements ActionListener {

    private String user;
    private Container north;
    JLabel title;
    private GridLayout deleteTable;

    DeleteAccountForm(String userName) {
        super(userName);
        user = userName;
        title = new JLabel("Are you sure you want to delete your account?");
        deleteTable = new GridLayout(0, 1);

        north = getContentPane();
        north.setLayout(deleteTable);
        north.add(title);

        JButton yesDelete = new JButton("Yes, Delete it!");
        yesDelete.setActionCommand("yes");
        yesDelete.addActionListener(this);

        JButton noDelete = new JButton("No, Don't Delete it!");
        noDelete.setActionCommand("no");
        noDelete.addActionListener(this);

        north.add(yesDelete);
        north.add(noDelete);
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
        if("yes".equals(e.getActionCommand())) {
            BMTRS.focusLogin();
            DatabaseInterfacer.getInstance().deleteAccount(user);
            this.dispose();
        }
        if("no".equals(e.getActionCommand())) {
            BMTRS.focusManageAccountForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        }
    }

}
