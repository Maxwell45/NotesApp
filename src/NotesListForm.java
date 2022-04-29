import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotesListForm {

    private JPanel panel1;
    private JList noteList;
    private JButton openButton;
    private JButton deleteButton;
    private JButton newButton;
    private final NotesManager notesManager; // notesManager is being used as a way to interface with the file system.
    private final NotesListForm formReference = this; // Used as a callback to this class from another form in order to update the list

    private List<File> fileList;
    private void createUIComponents() {
        JFrame frame = new JFrame();
        frame.setTitle("Заметки");
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
                createNoteNameDialog(0, 0); // Calling a form to create a new note
            }
        });

        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNoteDeletionConfirmationDialog(noteList.getSelectedValue().toString(), 0, 0);// Asking the user if they want to proceed
            }
        });

        noteList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() >= 2) { // Double click on an entry support
                    String selectedFile = noteList.getSelectedValue().toString();
                    createNoteTextForm(selectedFile, 0, 0);
                }
            }
        });

        noteList.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNoteDeletionConfirmationDialog(noteList.getSelectedValue().toString(), 0,0);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT); // Delete button support

        noteList.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNoteTextForm(noteList.getSelectedValue().toString(), 0, 0);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT); // Enter button support
    }

    public void createNoteTextForm(String selectedFile, int offsetX, int offsetY) {
        new NoteTextForm(selectedFile, notesManager, offsetX, offsetY); // A function to create a new text form
    }

    public void createNoteNameDialog(int offsetX, int offsetY) {
        new NewNoteNameDialog(notesManager, formReference, offsetX, offsetY); // A function to create a new name form
    }

    public void createNoteDeletionConfirmationDialog(String selectedFile, int offsetX, int offsetY) {
        new NoteDeletionConfirmationDialog(selectedFile, notesManager, formReference, offsetX, offsetY); // A function to create a deletion confirmation form
    }

    public NotesListForm() {
        try {
            notesManager = new NotesManager(); // Initializing notesManager
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createUIComponents();

        for (File file : notesManager.getFileList()) {
            if (file.getName().equals(".firsttime")) { // Testing if the application is run the first time or not, if so, open up the first note
                createNoteTextForm("Добро пожаловать в приложение Заметки", 500, 0);
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