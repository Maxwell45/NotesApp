import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NoteTextForm {
    private JFrame frame;
    private JTextArea noteText;
    private JPanel panel1;
    private JButton submitButton;
    private JLabel noteName;

    private NotesManager notesManager;

    private String fileName;

    public NoteTextForm(String fileName, NotesManager notesManager, int offsetX, int offsetY) {
        frame = new JFrame();
        frame.add(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLocation(frame.getX() + offsetX, frame.getY() + offsetY);
        frame.setVisible(true);

        this.notesManager = notesManager;
        this.fileName = fileName;

        noteName.setText(this.fileName);
        try {
            noteText.setText(notesManager.getNoteText(this.fileName)); // Setting the TextArea's text to whatever is written in the file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesManager.editNoteText(fileName, noteText.getText()); // Changing the file's contents to the contents of a TextArea
                frame.dispose();
            }
        });
    }
}