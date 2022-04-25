import javax.swing.*;
import java.awt.event.*;

public class NoteDeletionConfirmationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel warning;

    private final String selectedFile;
    private final NotesManager notesManager;
    private final NotesListForm parentForm;

    public NoteDeletionConfirmationDialog(String selectedFile, NotesManager notesManager, NotesListForm parentForm, int offsetX, int offsetY) {
        this.add(contentPane);
        this.setTitle("Удаление заметки");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(400, 150);
        this.setLocationRelativeTo(null);
        this.setLocation(this.getX() + offsetX, this.getY() + offsetY);
        this.warning.setText("Вы уверены что вы хотите удалить заметку " + selectedFile + "?"); // Asking the user if they really want to proceed with the deletion
        this.setVisible(true);

        this.selectedFile = selectedFile;
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
        notesManager.removeNote(selectedFile); // Removing the note
        parentForm.listUpdate(); // And updating the list
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
