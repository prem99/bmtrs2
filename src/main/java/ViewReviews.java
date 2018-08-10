import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewReviews extends JFrame implements ActionListener {

    private Container north;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;
    private JLabel title;
    private GridLayout reviews;

    private String museumName;

    ViewReviews(String museumName) {
        super("Reviews for" + museumName);
        this.museumName = museumName;

        title = new JLabel(museumName);
        reviews = new GridLayout(0,2);
        JButton goBack = new JButton("Back");
        goBack.addActionListener(this);
        goBack.setActionCommand("back");

        superContainer = getContentPane();
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));
        north = new Container();
        south = new Container();
        south.setLayout(reviews);
        north.setLayout(reviews);
        north.add(new JLabel("Review"));
        north.add(new JLabel("Score"));

        ArrayList[] combinedList = DatabaseInterfacer.getInstance().currentReviewList(museumName);
        for (int i = 0; i < combinedList[0].size(); i++) {
            JTextArea reviewPane = new JTextArea((String) combinedList[0].get(i));
            reviewPane.setLineWrap(true);
            reviewPane.setEditable(false);
            reviewPane.setWrapStyleWord(true);
            reviewPane.setMargin(new Insets(4,4,4,4));
            reviewPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            south.add(reviewPane);
            south.add(new JLabel(((Integer) combinedList[1].get(i)).toString()));
        }

        superContainer.add(new JLabel("Average rating: " + DatabaseInterfacer.getInstance().averageScore(museumName)));
        superContainer.add(goBack);
        superContainer.setVisible(true);

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
                System.out.print("all review page closed");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });
        this.setSize(400, 600);
        this.setVisible(true);
        this.setResizable(true);
    }

    public static void main(String[] args) {
        ViewReviews boi = new ViewReviews("MACBA");
        boi.setVisible(true);
        boi.setSize(400,600);
        boi.setResizable(true);
    }

    public void actionPerformed(ActionEvent e) {
        if ("back".equals(e.getActionCommand())) {
            System.out.println("Go back to the main page");
            BMTRS.focusExhibitForm(museumName);
            this.dispose();
        }
    }
}
