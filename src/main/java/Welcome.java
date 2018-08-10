import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Welcome extends JFrame implements ActionListener {

    private Container north;
    private JLabel title;
    private JPanel titlePanel;

    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;

    private JButton viewAll;
    private JButton myTickets;
    private JButton myReviews;
    private JButton manageAccount;
    private JButton viewSpecific;
    private JButton myMuseums;

    Welcome() {
        super("Welcome to BMTRS!");

        titlePanel = new JPanel();
        title = new JLabel("Welcome, " + CurrentUserInfo.getInstance().getUser());
        titlePanel.add(title);
        superContainer = getContentPane();
        south = new Container();
        north = new Container();
        north.setLayout(new GridLayout(0, 2));
        south.setLayout(new GridLayout(0,2));
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));

        north.add(new JLabel("Museum"));
        north.add(new JLabel("Average Score"));
        ArrayList<String> museums = DatabaseInterfacer.getInstance().getAllMuseums();
        for (String museum : museums) {
            JButton thisMuseumButton = new JButton(museum);
            thisMuseumButton.setActionCommand(museum);
            thisMuseumButton.addActionListener(this);
            south.add(thisMuseumButton);
            south.add(new JLabel("" +
                    ((DatabaseInterfacer.getInstance().averageScore(museum) > 0)
                            ? DatabaseInterfacer.getInstance().averageScore(museum) :
                            "N/A")));
        }

        scrollPane = new JScrollPane(south, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewSpecific = new JButton("View Museum");
        viewSpecific.setActionCommand("specific");
        viewSpecific.addActionListener(this);
        viewAll = new JButton("View All Museums");
        viewAll.setActionCommand("museums");
        viewAll.addActionListener(this);
        myTickets = new JButton("My Tickets");
        myTickets.setActionCommand("tickets");
        myTickets.addActionListener(this);
        myReviews = new JButton("My Reviews");
        myReviews.setActionCommand("reviews");
        myReviews.addActionListener(this);

        if (CurrentUserInfo.getInstance().getCuratorStatus()) {
            myMuseums = new JButton("My Museums");
            myMuseums.setActionCommand("myMuseums");
            myMuseums.addActionListener(this);
        }
        manageAccount = new JButton("Manage Account");
        manageAccount.setActionCommand("manage");
        manageAccount.addActionListener(this);

        superContainer.add(titlePanel);
        superContainer.add(north);
        superContainer.add(scrollPane);
        superContainer.add(myTickets);
        superContainer.add(myReviews);
        if (CurrentUserInfo.getInstance().getCuratorStatus()) {
            superContainer.add(myMuseums);
        }
        superContainer.add(manageAccount);
        superContainer.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        if ("tickets".equals(e.getActionCommand())) {
            BMTRS.focusMyTicketsForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        } else if ("manage".equals(e.getActionCommand())) {
            BMTRS.focusManageAccountForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        } else if ("reviews".equals(e.getActionCommand())) {
            BMTRS.focusMyReviewsForm(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        } else if ("myMuseums".equals(e.getActionCommand())) {
            BMTRS.focusMyMuseums(CurrentUserInfo.getInstance().getUser());
            this.dispose();
        } else {
            BMTRS.focusExhibitForm(e.getActionCommand());
            this.dispose();
        }
    }
}
