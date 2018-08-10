import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteMuseumForm extends JFrame implements ActionListener {
    private Container north;
    JLabel title;
    private GridLayout deleteTable;
    String user;

    DeleteMuseumForm(String userName) {
        super(userName);
        user = userName;
        title = new JLabel("Delete Museum Form");
        deleteTable = new GridLayout(0, 1);

        north = getContentPane();
        north.setLayout(deleteTable);
        north.add(title);

        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        ArrayList<String> museumsList = DatabaseInterfacer.getInstance().getAllMuseums();
        for (String museum : museumsList) {
            JButton thisMuseum = new JButton(museum);
            thisMuseum.setActionCommand(museum);
            thisMuseum.addActionListener(this);
            north.add(thisMuseum);
        }

        north.add(new JLabel(""));
        north.add(goBack);
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
        if("back".equals(e.getActionCommand())) {
            BMTRS.focusWelcomeForm();
            this.dispose();
        } else {
            BMTRS.focusDeleteMuseumConfirmation(user, e.getActionCommand());
            this.dispose();
        }
    }
}
