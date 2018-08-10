import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyMuseums extends JFrame implements ActionListener {
    private Container north;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;
    JLabel title;
    private GridLayout reviewsTable;

    MyMuseums(String userName) {
        super("My Museums");
        title = new JLabel("My Museums");
        reviewsTable = new GridLayout(0, 3);

        superContainer = getContentPane();
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));
        north = new Container();
        south = new Container();
        south.setLayout(reviewsTable);

        north.setLayout(reviewsTable);
        north.add(new JLabel(""));
        north.add(title);
        north.add(new JLabel(("")));
        north.add(new JLabel("Museum"));
        north.add(new JLabel("No. of Exhibits"));
        north.add(new JLabel("Average Rating"));

        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        ArrayList<String> museumsList = DatabaseInterfacer.getInstance().myMuseumsList(userName);
        for (String museum : museumsList) {
            JButton thisMuseum = new JButton(museum);
            thisMuseum.setActionCommand(museum);
            thisMuseum.addActionListener(this);
            south.add(thisMuseum);
            south.add(new JLabel(((Integer) DatabaseInterfacer.getInstance().numExhibits(museum)).toString()));
            south.add(new JLabel(((Float)DatabaseInterfacer.getInstance().averageScore(museum)).toString()));
        }

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
        this.setResizable(false);
    }

    public static void main (String[] args) {
        new MyMuseums("CCCB");
    }

    public void actionPerformed(ActionEvent e) {
        if("back".equals(e.getActionCommand())) {
            BMTRS.focusWelcomeForm();
            this.dispose();
        } else {
            BMTRS.focusExhibitForm(e.getActionCommand());
            this.dispose();
        }
    }
}
