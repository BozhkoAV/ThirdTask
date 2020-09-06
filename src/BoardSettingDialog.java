import javax.swing.*;
import java.awt.event.*;

public class BoardSettingDialog extends JDialog{
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel contentPane;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    private boolean exit = false;

    private BoardSettingDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        exit = true;
        dispose();
    }

    public static void main(String[] args) {
        BoardSettingDialog dialog = new BoardSettingDialog();
        dialog.pack();
        dialog.setSize(600, 500);
        dialog.setVisible(true);
        if (dialog.exit) System.exit(0);
        SwingUtilities.invokeLater(() -> new HexagonalMineSweeper(
                (Integer) dialog.comboBox1.getSelectedItem(),
                (Integer) dialog.comboBox2.getSelectedItem(),
                (Integer) dialog.comboBox3.getSelectedItem()
        ));
    }

    private void createUIComponents() {
        Integer[] size = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        comboBox1 = new JComboBox(size);
        comboBox2 = new JComboBox(size);
        comboBox3 = new JComboBox(new Integer[] {1});

        comboBox1.addActionListener(e -> setItems());
        comboBox2.addActionListener(e -> setItems());
    }

    private void setItems() {
        comboBox3.removeAllItems();
        int ind = (Integer) comboBox1.getSelectedItem() * (Integer) comboBox2.getSelectedItem();
        for (int i = 1; i <= ind; i++) {
            comboBox3.addItem(i);
        }
    }
}
