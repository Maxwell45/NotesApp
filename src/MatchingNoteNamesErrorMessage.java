import javax.swing.*;
import java.awt.event.*;

public class MatchingNoteNamesErrorMessage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public MatchingNoteNamesErrorMessage() {
        this.add(contentPane);
        this.setTitle("Ошибка");
        this.setSize(500, 150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onOK() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        //call onOK() on escape
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        this.dispose();
    }
}