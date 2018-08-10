import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MyTicketsForm extends JFrame implements ActionListener {

    private String user;
    private Container north;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;
    JLabel title;
    private GridLayout ticketsTable;

    MyTicketsForm(String userName) {
        super(userName);
        user = userName;
        title = new JLabel("My Tickets");
        ticketsTable = new GridLayout(0, 3);

        superContainer = getContentPane();
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));
        north = new Container();
        north.setLayout(ticketsTable);
        north.add(new JLabel(""));
        north.add(title);
        north.add(new JLabel(("")));
        north.add(new JLabel("Museum Ticket"));
        north.add(new JLabel("Purchase Timestamp"));
        north.add(new JLabel("Amount"));

        south = new Container();
        south.setLayout(new GridLayout(0, 3));

        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        ArrayList[] combinedList = DatabaseInterfacer.getInstance().myTicketsList(userName);
        for (int i = 0; i < combinedList[0].size(); i++) {
            JTextArea museumPane = new JTextArea((String) combinedList[0].get(i));
            museumPane.setLineWrap(true);
            museumPane.setEditable(false);
            museumPane.setWrapStyleWord(true);
            museumPane.setMargin(new Insets(4,4,4,4));
            museumPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            south.add(museumPane);
            south.add(new JLabel(((Timestamp) combinedList[1].get(i)).toString()));
            south.add(new JLabel("€" + ((BigDecimal) combinedList[2].get(i)).toString()));
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
                System.out.print("museum page closed");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });
        this.setSize(600, 400);
        this.setVisible(true);
        this.setResizable(false);

    }

    public void actionPerformed(ActionEvent e) {
        if("back".equals(e.getActionCommand())) {
            BMTRS.focusWelcomeForm();
            this.dispose();
        }
    }

}
