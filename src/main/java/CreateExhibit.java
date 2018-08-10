import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateExhibit extends JFrame implements ActionListener {
    private String museum;

    private Container container;
    private JTextField yearField;
    private JTextArea nameField;
    private JTextArea url;

    CreateExhibit(String museumName) {
        super("Create New Exhibit");
        museum = museumName;

        container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        JButton submitExhibit = new JButton("Submit Exhibit");
        JButton goBack = new JButton("Back");
        goBack.setActionCommand("back");
        submitExhibit.setActionCommand("submit");
        submitExhibit.addActionListener(this);
        goBack.addActionListener(this);

        nameField = new JTextArea();
        nameField.setLineWrap(true);
        nameField.setWrapStyleWord(true);
        nameField.setMargin(new Insets(4,4,4,4));
        nameField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        yearField = new JTextField();
        yearField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        url = new JTextArea();
        url.setLineWrap(true);
        url.setWrapStyleWord(true);
        url.setMargin(new Insets(4,4,4,4));
        url.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        container.add(new JLabel("Create New Exhibit for " + museumName));
        container.add(new JLabel());
        container.add(new JLabel("Name*: "));
        container.add(nameField);
        container.add(new JLabel("Year*: "));
        container.add(yearField);
        container.add(new JLabel("Link to more Info: "));
        container.add(url);
        container.add(submitExhibit);
        container.add(goBack);

        container.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.print("museum page closed");
                DatabaseInterfacer.getInstance().close();
                System.exit(0);
            }
        });
        this.setSize(400, 400);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if("submit".equals(e.getActionCommand())) {
            String name = nameField.getText();
            String urlString = url.getText();
            int year;
            if(DatabaseInterfacer.getInstance().checkInjection(name)) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(DatabaseInterfacer.getInstance().checkInjection(urlString)) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Year must be an integer.",
                        "Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.length() < 1 || name.length() > 256) {
                JOptionPane.showMessageDialog(this, "Name must not be empty and must have less than 257 characters.",
                        "Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (urlString.length() > 2048) {
                    JOptionPane.showMessageDialog(this, "URL must be less than 2049 characters.",
                            "Creation Error", JOptionPane.ERROR_MESSAGE);
                    return;
            } else if (yearField.getText().length() < 1) {
                JOptionPane.showMessageDialog(this, "Year must not be empty.",
                        "Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DatabaseInterfacer.getInstance().addExhibit(museum, name, year, urlString)) {
                JOptionPane.showMessageDialog(this, "Created Exhibit: " + name,
                        "Successful Creation", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failure. Exhibit may already exist.",
                        "Creation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if ("back".equals(e.getActionCommand())) {
            System.out.println("Go back to main page.");
            BMTRS.focusExhibitForm(museum);
            this.dispose();
    }

}

}
