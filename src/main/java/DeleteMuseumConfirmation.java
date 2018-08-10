import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteMuseumConfirmation extends JFrame implements ActionListener {

    private String user;
    private Container north;
    JLabel title;
    private GridLayout deleteTable;
    private String mName;

    DeleteMuseumConfirmation(String userName, String museumName) {
        super(userName);
        user = userName;
        mName = museumName;
        title = new JLabel("Are you sure you want to delete this museum?");
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
            DatabaseInterfacer.getInstance().deleteMuseum(mName);
            BMTRS.focusDeleteMuseumForm(user);
            this.dispose();
        }
        if("no".equals(e.getActionCommand())) {
            BMTRS.focusDeleteMuseumForm(user);
            this.dispose();
        }
    }

}
