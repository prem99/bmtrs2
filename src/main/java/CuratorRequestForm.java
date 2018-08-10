import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CuratorRequestForm extends JFrame implements ActionListener {

    private String user;
    private Container north;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;
    JLabel title;
    private GridLayout curatorRequestTable;

    CuratorRequestForm(String userName) {
        super(userName);
        user = userName;

        superContainer = getContentPane();
        north = new Container();
        south = new Container();
        south.setLayout(new GridLayout(0,1));
        north.setLayout(new GridLayout(0, 1));
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));

        north.add(new JLabel("Curator Request"));
        north.add(new JLabel("Museum"));


        ArrayList<String> museums = DatabaseInterfacer.getInstance().getAllMuseums();
        for (String museum : museums) {
            JButton thisMuseumButton = new JButton(museum);
            thisMuseumButton.setActionCommand(museum);
            thisMuseumButton.addActionListener(this);
            south.add(thisMuseumButton);
        }

        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        scrollPane = new JScrollPane(south, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        superContainer.add(north);
        superContainer.add(scrollPane);
        superContainer.add(goBack);
        north.setVisible(true);
        south.setVisible(true);
        superContainer.setVisible(true);

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
        this.setResizable(true);
    }

    public void actionPerformed(ActionEvent e) {
        if("back".equals(e.getActionCommand())) {
            BMTRS.focusManageAccountForm(user);
            this.dispose();
        } else {
            if (!DatabaseInterfacer.getInstance().createCuratorRequest(user, e.getActionCommand())) {
                JOptionPane.showMessageDialog(this, "A curator already exists for this museum!",
                        "Request error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Your request for " + e.getActionCommand() + " is pending.",
                        "Request Pending", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
