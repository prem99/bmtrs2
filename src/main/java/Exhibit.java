import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Exhibit extends JFrame implements ActionListener {

    private String museum;

    private Container north;
    private JLabel title;
    private GridLayout museumTable;

    private Container central;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;

    private ArrayList[] combinedList;

    Exhibit(String museumName) {
        super(museumName);
        museum = museumName;
        title = new JLabel(museumName);

        boolean curator = DatabaseInterfacer.getInstance().checkCuratorStatus(museum,
                CurrentUserInfo.getInstance().getUser());

        museumTable = new GridLayout(0,(curator ? 4 : 3));
        JButton tixPurchase = new JButton("Purchase ticket!");
        tixPurchase.setActionCommand("purchase");
        JButton review = new JButton("Write a review!");
        review.setActionCommand("review");
        JButton viewReviews = new JButton("View all reviews");
        viewReviews.setActionCommand("viewReviews");
        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        tixPurchase.addActionListener(this);
        review.addActionListener(this);
        viewReviews.addActionListener(this);
        goBack.addActionListener(this);

        superContainer = getContentPane();
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));
        north = new Container();
        south = new Container();
        central = new Container();
        central.setLayout(museumTable);
        north.setLayout(museumTable);

        north.add(title);
        north.add(new JLabel("Average Score: " +
                ((DatabaseInterfacer.getInstance().averageScore(museum) > 0)
                        ? DatabaseInterfacer.getInstance().averageScore(museum) :
                        "N/A")));
        north.add(new JLabel("Current Price: €" +
                DatabaseInterfacer.getInstance().currentPrice(museum)));
        if (curator) north.add(new JLabel(""));
        north.add(new JLabel("Exhibit"));
        north.add(new JLabel("Year"));
        north.add(new JLabel("Link to Image/Information"));
        if (curator) north.add(new JLabel(""));

        combinedList = DatabaseInterfacer.getInstance().currentExhibitList(museumName);
        for (int i = 0; i < combinedList[0].size(); i++) {
            String name = (String) combinedList[0].get(i);
            JTextArea exhibitPane = new JTextArea((String) combinedList[0].get(i));
            exhibitPane.setLineWrap(true);
            exhibitPane.setWrapStyleWord(true);
            exhibitPane.setEditable(false);
            exhibitPane.setMargin(new Insets(4,4,4,4));
            exhibitPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            central.add(exhibitPane);
            central.add(new JLabel("" + combinedList[1].get(i)));
            JTextArea urlPane = new JTextArea((String) combinedList[2].get(i));
            urlPane.setLineWrap(true);
            urlPane.setEditable(false);
            urlPane.setMargin(new Insets(4,4,4,4));
            urlPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            central.add(urlPane);
            if (curator) {
                JButton delete = new JButton("Delete Exhibit");
                delete.setActionCommand(name);
                delete.addActionListener(this);
                central.add(delete);
            }
        }

        south.setLayout(museumTable);

        south.add(tixPurchase);
        south.add(review);
        south.add(viewReviews);

        JButton addExhibit;

        //for curators
        if (curator) {
            addExhibit = new JButton("Add Exhibit");
            addExhibit.setActionCommand("addExhibit");
            addExhibit.addActionListener(this);
            south.add(addExhibit);
        } else {
            south.add(new JLabel(("")));
        }

        south.add(goBack);
        south.setVisible(true);

        scrollPane = new JScrollPane(central, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        superContainer.add(north);
        superContainer.add(scrollPane);
        superContainer.add(south);
        superContainer.setVisible(true);

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
        if("purchase".equals(e.getActionCommand())) {
            if(!DatabaseInterfacer.getInstance().purchaseTicket(museum)) {
                JOptionPane.showMessageDialog(this, "You have already purchased a ticket!",
                        "Purchase Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thank you for purchasing a €"
                                + DatabaseInterfacer.getInstance().currentPrice(museum) + " ticket to " + museum + "!",
                        "Successful Purchase", JOptionPane.PLAIN_MESSAGE);
            }
        } else if ("review".equals(e.getActionCommand())) {
            if (DatabaseInterfacer.getInstance().hasPurchasedTicket(CurrentUserInfo.getInstance().getUser(), museum)) {
                BMTRS.focusReview(museum);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "You must purchase a ticket to review this museum.",
                        "Review Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("viewReviews".equals(e.getActionCommand())) {
            System.out.println("viewing all reviews of "+museum);
            BMTRS.focusViewReviews(museum);
            this.dispose();
        } else if ("back".equals(e.getActionCommand())) {
            System.out.println("Go back to main page.");
            BMTRS.focusWelcomeForm();
            this.dispose();
        } else if ("addExhibit".equals(e.getActionCommand())) {
                BMTRS.focusCreateExhibit(museum);
                this.dispose();
        } else if (combinedList[0].contains(e.getActionCommand())) {
            if (DatabaseInterfacer.getInstance().removeExhibit(museum, e.getActionCommand())) {
                JOptionPane.showMessageDialog(this, "Deleted exhibit: " + e.getActionCommand(),
                        "Successful Deletion", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                BMTRS.focusExhibitForm(museum);
            } else {
                JOptionPane.showMessageDialog(this, "Could not delete exhibit: " + e.getActionCommand(),
                        "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
