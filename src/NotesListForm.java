import javax.swing.*;
import java.awt.event.ActionEvent;
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
    private NotesListForm formReference = this; // Used as a callback to this class from another form in order to update the list and also to close the child form properly

    private JFrame noteTextForm = new JFrame(); //Containers for the text inside a note
    private JFrame noteNameDialog = new JFrame(); // and for a new note dialogue respectively

    private List<File> fileList;
    private void createUIComponents() {
    }

    public NotesListForm() {
        try {
            notesManager = new NotesManager(); // Initializing notesManager
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listUpdate(); // Bringing the list up to date

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (File file : fileList) {
            listModel.addElement(file.getName()); // Creating the model, filling it up
        }
        noteList.setModel(listModel); // and initializing the list in the form

        openButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = noteList.getSelectedValue().toString();
                noteTextForm.add(new NoteTextForm(selectedFile, notesManager).getPanel());
                noteTextForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                noteTextForm.setSize(250, 250);
                noteTextForm.setLocationRelativeTo(null);
                noteTextForm.setVisible(true); // Preparing the form and opening it
            }
        });

        newButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noteNameDialog.add(new NewNoteNameDialog(notesManager, formReference).getContentPane()); // Passing the formReference here to make the NewNoteNameDialog able to update this form
                noteNameDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                noteNameDialog.setSize(300, 150);
                noteNameDialog.setLocationRelativeTo(null);
                noteNameDialog.setVisible(true); // Preparing the form and opening it
            }
        });

        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notesManager.removeNote(noteList.getSelectedValue().toString()); // Removing the file from the list and also deleting the file containing it
                listUpdate(); // Updating the list
            }
        });
    }

    public void listUpdate() {
        fileList = notesManager.getFileList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (File file : fileList) {
            listModel.addElement(file.getName()); // Updating the list and putting it inside a model
        }
        noteList.setModel(listModel);
    }

    public JPanel getPanel() {
        return panel1;
    }

    public void closeDialog() {
        noteNameDialog.setVisible(false); // Made specifically to make the noteNameDialog close properly
        noteNameDialog.dispose();
    }
}