import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NoteTextForm {
    private JTextArea noteText;
    private JPanel panel1;
    private JButton submitButton;
    private JLabel noteName;

    private NotesManager notesManager;

    private String fileName;

    public NoteTextForm(String fileName, NotesManager notesManager) {
        panel1.setVisible(true);
        this.notesManager = notesManager;
        this.fileName = fileName;
        List<File> fileList = this.notesManager.getFileList();
        noteName.setText(this.fileName);

        try {
            noteText.setText(notesManager.getNoteText(this.fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesManager.editNoteText(fileName, noteText.getText());
            }
        });
    }

    public JPanel getPanel() {
        return panel1;
    }
}