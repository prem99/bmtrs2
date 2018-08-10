import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
public class NewMuseum extends JFrame implements ActionListener {

    private JLabel title;
    private JPanel titlePanel;
    private JTextField museumName;
    private JLabel museumLabel;
    private JPanel namePanel;
    private JButton submit;
    private JButton back;
    private JPanel pricePanel;
    private JLabel priceLabel;
    private JTextField price;
    private Container north;

    NewMuseum() {
        super("Make a new museum");

        title = new JLabel("New Museum Form");
        titlePanel = new JPanel();
        titlePanel.add(title);
        submit = new JButton("Submit");
        submit.addActionListener(this);
        submit.setActionCommand("submit");

        pricePanel = new JPanel();
        priceLabel = new JLabel("Price: ");
        price = new JTextField();
        price.setMargin(new Insets(4, 4, 4, 4));
        price.addActionListener(this);
        pricePanel.add(priceLabel);
        pricePanel.add(price);
        price.setPreferredSize(new Dimension(300, 50));
        back = new JButton("Back");
        back.addActionListener(this);
        back.setActionCommand("back");
        museumLabel = new JLabel("Add a Museum");
        museumName = new JTextField();
        museumName.setPreferredSize(new Dimension(300, 50));
        namePanel = new JPanel();
        namePanel.add(museumLabel);
        namePanel.add(museumName);

        north = getContentPane();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(titlePanel);
        north.add(namePanel);
        north.add(pricePanel);
        north.add(submit);
        north.add(back);

        north.setVisible(true);

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
        this.setResizable(true);
    }

    public static void main(String[] args) {
        NewMuseum a = new NewMuseum();
        a.setSize(600,600);
        a.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if ("submit".equals(e.getActionCommand())) {
            float amount = 0;
            String mName = museumName.getText();
            String strPrice = price.getText();
            try {
                amount = Float.parseFloat(strPrice);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(this, "Price must be a number",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (mName.equals("")) JOptionPane.showMessageDialog(this, "Entry cannot be empty",
                    "Error", JOptionPane.ERROR_MESSAGE);
            if(DatabaseInterfacer.getInstance().checkInjection(museumName.getText())) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            boolean added = DatabaseInterfacer.getInstance().addMuseum(mName, amount);
            if (added) {
                System.out.println("made museum");
                JOptionPane.showMessageDialog(north, "Congratulations, you made a museum");
            } else {
                System.out.println("Museum exists");
                JOptionPane.showMessageDialog(north, "This museum already exists");
            }
        }

        if ("back".equals(e.getActionCommand())) {
            System.out.println("Go back to main page");
            BMTRS.focusWelcomeForm();
            this.dispose();
        }
    }
}
