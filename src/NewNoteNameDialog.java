import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class NewNoteNameDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private final NotesManager notesManager; // References to a notesManager
    private final NotesListForm parentForm; // and to the form that called it

    public NewNoteNameDialog(NotesManager notesManager, NotesListForm parentForm, int offsetX, int offsetY) {
        this.add(contentPane);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setLocation(this.getX() + offsetX, this.getY() + offsetY);
        this.setVisible(true);

        this.notesManager = notesManager;
        this.parentForm = parentForm;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if(!textField1.getText().matches("[\\w ]+")) { // If the entered name doesn't match regex, call an error form
            new IncorrectFormatOfNoteErrorMessage();
            return;
        }
        for (File file : notesManager.getFileList()) { // If a duplicate entry is found, call an error form
            if (textField1.getText().equals(file.getName())) {
                new MatchingNoteNamesErrorMessage();
                return;
            }
        }
        try {
            notesManager.addNote(textField1.getText()); // Otherwise, actually make a new note
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parentForm.listUpdate(); // Update the list
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    } // and then ask the parentForm to close this form properly
}