import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotesListForm {

    private JPanel panel1;
    private JList noteList;
    private JButton openButton;
    private JButton deleteButton;
    private JButton newButton;
    private NotesManager notesManager; // notesManager is being used as a way to interface with the file system.
    private NotesListForm formReference = this; // Used as a callback to this class from another form in order to update the list
    private JFrame noteNameDialog = new JFrame(); // and for a new note dialogue respectively

    private List<File> fileList;
    private void createUIComponents() {
        JFrame frame = new JFrame();
        frame.add(this.panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        listUpdate();

        openButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = noteList.getSelectedValue().toString();
                createNoteTextForm(selectedFile, 0, 0);
                // Preparing the form and opening it
            }
        });

        newButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNoteNameDialog(0, 0);
            }
        });

        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesManager.removeNote(noteList.getSelectedValue().toString()); // Removing the file from the list and also deleting the file containing it
                listUpdate(); // Updating the list
            }
        });

        noteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() >= 2) {
                    String selectedFile = noteList.getSelectedValue().toString();
                    createNoteTextForm(selectedFile, 0, 0);
                }
            }
        });
    }

    public void createNoteTextForm(String selectedFile, int offsetX, int offsetY) {
        new NoteTextForm(selectedFile, notesManager, offsetX, offsetY);
    }

    public void createNoteNameDialog(int offsetX, int offsetY) {
        new NewNoteNameDialog(notesManager, formReference, offsetX, offsetY);
    }

    public NotesListForm() {
        try {
            notesManager = new NotesManager(); // Initializing notesManager
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createUIComponents();

        for (File file : notesManager.getFileList()) {
            if (file.getName().equals(".firsttime")) {
                createNoteTextForm("Добро пожаловать в приложение заметки", 500, 0);
                notesManager.removeNote(".firsttime");
                listUpdate();
            }
        }
    }

    public void listUpdate() {
        fileList = notesManager.getFileList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (File file : fileList) {
            listModel.addElement(file.getName()); // Updating the list and putting it inside a model
        }
        noteList.setModel(listModel);
    }
}