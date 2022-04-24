import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new NotesListForm().getPanel());
        frame.setSize(250, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}