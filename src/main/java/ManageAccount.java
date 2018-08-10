//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//public class ManageAccount extends JFrame implements ActionListener{
//
//    private JLabel title;
//    private JButton logOut;
//    private JButton curatorRequest;
//    private JButton deleteAccount;
//    private JButton goBack;
//    private Container north;
//
//    ManageAccount() {
//        title = new JLabel("Manage Account");
//        logOut = new JButton("Log Out");
//        logOut.setActionCommand("logOut");
//        curatorRequest = new JButton("Curator Request");
//        curatorRequest.setActionCommand("request");
//        deleteAccount = new JButton("Delete your Account");
//        deleteAccount.setActionCommand("delete");
//        goBack = new JButton("Back");
//        goBack.setActionCommand("back");
//
//        north = getContentPane();
//        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
//        north.add(title);
//        north.add(logOut);
//        north.add(curatorRequest);
//        north.add(deleteAccount);
//        north.add(goBack);
//        north.setVisible(true);
//
//        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                System.out.print("museum page closed");
//                DatabaseInterfacer.getInstance().close();
//                System.exit(0);
//            }
//        });
//        this.setSize(600, 600);
//        this.setVisible(true);
//        this.setResizable(true);
//    }
//    public static void  main(String[] args) {
//        ManageAccount mAccount = new ManageAccount();
//        mAccount.setVisible(true);
//        mAccount.setSize(600,600);
//        mAccount.setResizable(false);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if ("logOut".equals(e.getActionCommand())) {
//            BMTRS.focusLogin();
//            this.dispose();
//        }
//
//        if ("request".equals(e.getActionCommand())) {
//            //BMTRS.focusCuratorRequestForm();
//            this.dispose();
//        }
//
//        if ("manage".equals(e.getActionCommand())) {
//            BMTRS.focusManageAccountForm();
//            this.dispose();
//        }
//
//        if ("manage".equals(e.getActionCommand())) {
//            BMTRS.focusManageAccountForm();
//            this.dispose();
//        }
//    }
//}
