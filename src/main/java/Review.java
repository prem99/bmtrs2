import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Review extends JFrame implements ActionListener {

    private String museum;
    private Container container;
    private JTextArea comment;
    private JScrollPane scrollPane;
    private JComboBox score;
    private JButton backButton;
    private JButton submitButton;


    Review(String museum) {
        super("Review a Museum");
        this.museum = museum;

        container = getContentPane();
        container.setLayout(new GridLayout(0, 2));

        String[] choices = {"5", "4", "3", "2", "1"};
        score = new JComboBox(choices);

        comment = new JTextArea();
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setMargin(new Insets(4,4,4,4));
        comment.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        scrollPane = new JScrollPane(comment,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        score.setSelectedIndex(5 - DatabaseInterfacer.getInstance().getReviewScore
                (museum, CurrentUserInfo.getInstance().getUser()));

        comment.setText(DatabaseInterfacer.getInstance().getReviewText
                (museum, CurrentUserInfo.getInstance().getUser()));

        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.addActionListener(this);

        submitButton = new JButton("Submit or Modify Review");
        submitButton.setActionCommand("create");
        submitButton.addActionListener(this);

        JButton deleteButton = new JButton("Delete Review");
        deleteButton.setActionCommand("delete " + museum);
        deleteButton.addActionListener(this);

        container.add(new JLabel("Review for " + museum));
        container.add(new JLabel());
        container.add(new JLabel("Score: "));
        container.add(score);
        container.add(new JLabel("Comment: "));
        container.add(scrollPane);
        container.add(submitButton);
        container.add(deleteButton);
        container.add(backButton);

        container.setVisible(true);

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
        if ("create".equals(e.getActionCommand())) {
            int scoreNum = Integer.parseInt((String) score.getSelectedItem());
            if(DatabaseInterfacer.getInstance().checkInjection(comment.getText())) {
                JOptionPane.showMessageDialog(this, "Entry cannot contain ' or ;",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseInterfacer.getInstance().createReview(museum, scoreNum, comment.getText())) {
                JOptionPane.showMessageDialog(this, "Successfully left a review!",
                        "Successful Review", JOptionPane.PLAIN_MESSAGE);
            }
        } else if ("back".equals(e.getActionCommand())) {
            BMTRS.focusExhibitForm(museum);
            this.dispose();
        } else if (e.getActionCommand().startsWith("delete")) {
            DatabaseInterfacer.getInstance().deleteReview(
                    CurrentUserInfo.getInstance().getUser(), e.getActionCommand().substring(7));
            JOptionPane.showMessageDialog(this, "Deleted Review!",
                    "Review Deletion", JOptionPane.PLAIN_MESSAGE);
            BMTRS.focusReview(museum);
        }
    }
}
