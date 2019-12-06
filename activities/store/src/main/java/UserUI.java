import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;


    DataAdapter dataAccess;

    StoreTableModel tableModel;
    JTable table;

    JButton addUserButton = new JButton("Add User");


    public UserUI() {
        this.setTitle("Users");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();

        JPanel addButton = new JPanel();
        addButton.add(addUserButton);
        this.getContentPane().add(addButton, Component.CENTER_ALIGNMENT);

        tableModel = new StoreTableModel(dataAccess.loadAllUsers(), UserModel.COL_NAMES);

        tableModel.addTableModelListener(new TableUpdateListener(dataAccess));

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        table.addMouseListener(new UserDeleteMouseListener());

        JPanel tableFrame = new JPanel();
        tableFrame.add(scrollPane);
        this.getContentPane().add(tableFrame);


        addUserButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                AddUserUI child = new AddUserUI();
                child.parent = UserUI.this;
                child.run();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserUI.this.dispose();
            }
        });

    }

    class UserDeleteMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (table == null)
                return;
            int r = table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < table.getRowCount()) {
                table.setRowSelectionInterval(r, r);
            } else {
                table.clearSelection();
            }
            int rowIndex = table.getSelectedRow();
            if (rowIndex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                UserModel user = UserModel.getUserFromStringArray(tableModel.getRowAt(rowIndex));
                PopUpDelete deleteMenu = new PopUpDelete(user);
                deleteMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        public void mouseReleased(MouseEvent e) {
            // do nothing
        }
        public void mouseClicked(MouseEvent e) {
            // nop
        }
    }

    class PopUpDelete extends JPopupMenu {
        JMenuItem menuDelete;
        PopUpDelete(UserModel user) {
            menuDelete = new JMenuItem("Delete");
            menuDelete.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   dataAccess.removeUser(user);
                   updateTable();
               }
            });
            add(menuDelete);
        }
    }

    public void run() {
        Dimension screen = Application.getInstance().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
        this.pack();
        this.setVisible(true);
    }

    public void updateTable() {
        tableModel.setData(dataAccess.loadAllUsers());
        tableModel.fireTableDataChanged();
    }
}

