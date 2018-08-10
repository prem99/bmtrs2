import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AcceptCuratorRequest extends JFrame implements ActionListener {
    private Container north;
    private Container superContainer;
    private Container south;
    private JScrollPane scrollPane;

    private JLabel title;
    private GridLayout reviewsTable;

    private ArrayList<String>[] requestList;

    AcceptCuratorRequest() {
        super("Active Curator Requests");
        title = new JLabel("Accept/Deny Active Curator Requests");
        reviewsTable = new GridLayout(0, 5);

        superContainer = getContentPane();
        north = new Container();
        south = new Container();
        south.setLayout(new GridLayout(0,5));
        north.setLayout(reviewsTable);
        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));

        north.add(new JLabel(""));
        north.add(new JLabel(""));
        north.add(title);
        north.add(new JLabel(("")));
        north.add(new JLabel(""));
        north.add(new JLabel("Visitor"));
        north.add(new JLabel("Museum"));
        north.add(new JLabel("Date"));
        north.add(new JLabel("Approve"));
        north.add(new JLabel("Reject"));

        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        goBack.addActionListener(this);

        requestList = DatabaseInterfacer.getInstance().activeRequestList();
        for (int i = 0; i < requestList[0].size(); i++) {
            JButton thisMuseum = new JButton(requestList[1].get(i));
            thisMuseum.setActionCommand(requestList[1].get(i));
            thisMuseum.addActionListener(this);

            JButton accept = new JButton("Approve");
            accept.setActionCommand("accept " + i);
            accept.addActionListener(this);

            JButton reject = new JButton("Reject");
            reject.setActionCommand("reject " + i);
            reject.addActionListener(this);

            JTextArea visitorPane = new JTextArea((String) requestList[0].get(i));
            visitorPane.setLineWrap(true);
            visitorPane.setEditable(false);
            visitorPane.setMargin(new Insets(4,4,4,4));
            visitorPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            south.add(visitorPane);
            south.add(thisMuseum); //museum
            south.add(new JLabel(requestList[2].get(i))); //date
            south.add(accept);
            south.add(reject);
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
        this.setSize(1200, 400);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main (String[] args) {
        new AcceptCuratorRequest();
    }

    public void actionPerformed(ActionEvent e) {
        if("back".equals(e.getActionCommand())) {
            BMTRS.focusWelcomeForm();
        } else if (DatabaseInterfacer.getInstance().getAllMuseums().contains(e.getActionCommand())) {
            BMTRS.focusExhibitForm(e.getActionCommand());
        } else if (e.getActionCommand().startsWith("accept")) {
            int index = Integer.parseInt(e.getActionCommand().substring(7));
            DatabaseInterfacer.getInstance().acceptRequest(
                    requestList[0].get(index), requestList[1].get(index));
            JOptionPane.showMessageDialog(this, "Approved Request!",
                    "Request Approval", JOptionPane.PLAIN_MESSAGE);
            BMTRS.focusAcceptCuratorRequest();
        } else if (e.getActionCommand().startsWith("reject")) {
            int index = Integer.parseInt(e.getActionCommand().substring(7));
            DatabaseInterfacer.getInstance().rejectRequest(
                    requestList[0].get(index), requestList[1].get(index));
            JOptionPane.showMessageDialog(this, "Denied Request!",
                    "Request Rejection", JOptionPane.PLAIN_MESSAGE);
            BMTRS.focusAcceptCuratorRequest();
        }
        this.dispose();
    }
}
