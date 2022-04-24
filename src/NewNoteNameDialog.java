import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class NewNoteNameDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private NotesManager notesManager; // References to a notesManager
    private NotesListForm parentForm; // and to the form that called it

    private JFrame errorMessage = new JFrame(); // and an error message that shows up in case if the input is incorrect

    public NewNoteNameDialog(NotesManager notesManager, NotesListForm parentForm) {
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
            errorMessage.add(new IncorrectFormatOfNoteErrorMessage(this).getPanel());
            errorMessage.setSize(500, 150);
            errorMessage.setVisible(true);
            return;
        }
        for (File file : notesManager.getFileList()) { // If a duplicate entry is found, call an error form
            if (textField1.getText().equals(file.getName())) {
                errorMessage.add(new MatchingNoteNamesErrorMessage(this).getPanel());
                errorMessage.setSize(500, 150);
                errorMessage.setVisible(true);
                return;
            }
        }
        try {
            notesManager.addNote(textField1.getText()); // Otherwise, actually make a new note
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parentForm.listUpdate(); // Update the list
        onCancel();
    }

    private void onCancel() {
        parentForm.closeDialog();
    } // and then ask the parentForm to close this form properly

    public void closeErrorMessage() {
        errorMessage.setVisible(false); // This triggers whenever an error message closes to close it properly
        errorMessage.dispose();
    }
}