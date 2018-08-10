//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//public class AllMuseums extends JFrame implements ActionListener {
//
//    private Container north;
//    private Container superContainer;
//    private Container south;
//    private JScrollPane scrollPane;
//
//    AllMuseums() {
//        super("View All Museums");
//
//        superContainer = getContentPane();
//        north = new Container();
//        south = new Container();
//        south.setLayout(new GridLayout(0,2));
//        north.setLayout(new GridLayout(0, 2));
//        superContainer.setLayout(new BoxLayout(superContainer, BoxLayout.PAGE_AXIS));
//
//        superContainer.add(new JLabel("All Museums"));
//        north.add(new JLabel("Museum"));
//        north.add(new JLabel("Average Score"));
//        ArrayList<String> museums = DatabaseInterfacer.getInstance().getAllMuseums();
//        for (String museum : museums) {
//            JButton thisMuseumButton = new JButton(museum);
//            thisMuseumButton.setActionCommand(museum);
//            thisMuseumButton.addActionListener(this);
//            south.add(thisMuseumButton);
//            south.add(new JLabel("" +
//                    ((DatabaseInterfacer.getInstance().averageScore(museum) > 0)
//                    ? DatabaseInterfacer.getInstance().averageScore(museum) :
//                    "N/A")));
//        }
//
//        JButton goBack = new JButton("Back");
//        goBack.setActionCommand("back");
//        goBack.addActionListener(this);
//
//        scrollPane = new JScrollPane(south, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//
//        superContainer.add(new JLabel());
//        superContainer.add(north);
//        superContainer.add(scrollPane);
//        superContainer.add(goBack);
//        north.setVisible(true);
//        south.setVisible(true);
//        superContainer.setVisible(true);
//
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                System.out.print("all museum page closed");
//                DatabaseInterfacer.getInstance().close();
//                System.exit(0);
//            }
//        });
//        this.setSize(600, 600);
//        this.setVisible(true);
//        this.setResizable(true);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if("back".equals(e.getActionCommand())) {
//            System.out.println("Go back to main page.");
//            BMTRS.focusWelcomeForm();
//        } else {
//            BMTRS.focusExhibitForm(e.getActionCommand());
//        }
//        this.dispose();
//    }
//}
