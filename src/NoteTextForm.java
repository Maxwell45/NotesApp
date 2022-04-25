import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NoteTextForm {
    private final JFrame frame;
    private JTextArea noteText;
    private JPanel panel1;
    private JButton submitButton;
    private JLabel noteName;

    public NoteTextForm(String fileName, NotesManager notesManager, int offsetX, int offsetY) {
        frame = new JFrame();
        frame.add(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLocation(frame.getX() + offsetX, frame.getY() + offsetY);
        frame.setVisible(true);

        noteName.setText(fileName);
        try {
            noteText.setText(notesManager.getNoteText(fileName)); // Setting the TextArea's text to whatever is written in the file;
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