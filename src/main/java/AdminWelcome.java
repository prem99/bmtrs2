import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AdminWelcome extends JFrame implements ActionListener {
    private Container north;
    private JLabel title;
    private GridLayout table;
    private JButton acceptCuratorRequest;
    private JButton addMuseum;
    private JButton deleteMuseum;
    private JButton logout;

    AdminWelcome() {
        super("Admin: " + CurrentUserInfo.getInstance().getUser());
        title = new JLabel("Welcome Admin");
        table = new GridLayout(0, 1);

        north = getContentPane();
        north.setLayout(table);
        north.add(title);

        acceptCuratorRequest = new JButton("Accept Curator Request");
        acceptCuratorRequest.setActionCommand("accept curator request");
        acceptCuratorRequest.addActionListener(this);

        addMuseum = new JButton("Add Museum");
        addMuseum.setActionCommand("add museum");
        addMuseum.addActionListener(this);

        deleteMuseum = new JButton("Delete Museum");
        deleteMuseum.setActionCommand("delete museum");
        deleteMuseum.addActionListener(this);

        logout = new JButton("Logout");
        logout.setActionCommand("logout");
        logout.addActionListener(this);

        north.add(acceptCuratorRequest);
        north.add(addMuseum);
        north.add(deleteMuseum);
        north.add(logout);
        north.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.print("admin page closed");
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

        if("accept curator request".equals(e.getActionCommand())) {
            BMTRS.focusAcceptCuratorRequest();
            this.dispose();
        }

        if("delete museum".equals(e.getActionCommand())) {
            BMTRS.focusDeleteMuseumForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        }

        if("add museum".equals(e.getActionCommand())) {
            BMTRS.focusNewMuseumForm();
            this.dispose();
        }
    }
}
