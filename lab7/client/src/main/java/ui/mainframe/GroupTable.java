package ui.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import collection_manager.AbstractManager;
import models.Person;
import models.Semester;
import models.StudyGroup;

public class GroupTable extends JPanel {
    private AbstractManager manager;
    private JTable table;
    private JScrollPane scrollPane;
    private Object[][] data;
    private JTextField filterField;

    private final String[] columnNames = {
            "id", "Name", "Pos X", "Pos Y",
            "Creation Date", "Count of Students",
            "Expelled students", "Transferred students",
            "Semester", "Admin Name", "Admin BD",
            "Admin passport", "Admin hair" };

    private Boolean sortAscending = true;

    public GroupTable(AbstractManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Add padding/margins
        setOpaque(false);

        // Create the table and scroll pane
        table = initTable();
        table.setOpaque(false);
        JTableHeader tableHeader = table.getTableHeader();

        // Add a mouse listener to each header cell
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tableHeader.columnAtPoint(e.getPoint());
                if (column >= 0) {
                    // Get the name of the clicked header cell
                    String columnName = table.getColumnName(column);

                    // Filter the data based on the clicked column name
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    String filterText = filterField.getText();

                    data = formTableFromCollection(data, columnName, columnNames, filterText);
                    for (int i = 0; i < data.length; i++) {
                        model.addRow(data[i]);
                    }
                    model.fireTableDataChanged();
                }
            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // scrollPane.getVerticalScrollBar().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        JPanel filteringPanel = new JPanel(new BorderLayout());
        filteringPanel.setOpaque(false);
        filteringPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Try to filter: ");
        filterField = new JTextField(10);

        filterPanel.add(filterLabel);
        filterPanel.add(filterField);

        filteringPanel.add(filterPanel, BorderLayout.EAST);

        add(filteringPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // Add the container panel to the center of the panel

        setVisible(true);
    }

    private JTable initTable() {
        TreeMap<String, StudyGroup> collection = manager.getCollection();
        data = new Object[collection.size()][columnNames.length];
        data = formTableFromCollection(data, "null", columnNames, null);

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        table.getTableHeader().setBackground(Color.white);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // to
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                if (row % 2 == 0) {
                    component.setBackground(new Color(20, 20, 20, 50));
                } else {
                    component.setBackground(new Color(0, 0, 0, 0));
                }
                // Remove the border
                Border emptyBorder = BorderFactory.createEmptyBorder();
                ((JComponent) component).setBorder(emptyBorder);
                return component;
            }
        };

        // Apply the custom cell renderer to all columns
        for (int j = 0; j < table.getColumnCount(); j++) {
            table.getColumnModel().getColumn(j).setCellRenderer(renderer);
        }

        return table;
        // scrollPane = new JScrollPane(table);
    }

    Function<StudyGroup, String> unaryOperator;

    private Object[][] formTableFromCollection(Object[][] data, String columnName, String[] columnNames,
            String filterText) {
        TreeMap<String, StudyGroup> collection = manager.getCollection();

        Comparator<StudyGroup> sortingComparator;
        Predicate<StudyGroup> filteringPredicate;
        if (columnName.equals("id")) {
            sortingComparator = Comparator.comparing(StudyGroup::getId);
            filteringPredicate = o -> String.valueOf(o.getId()).contains(filterText);
        } else if (columnName.equals("Name")) {
            sortingComparator = Comparator.comparing(StudyGroup::getName);
            filteringPredicate = o -> String.valueOf(o.getName()).contains(filterText);
        } else if (columnName.equals("Pos X")) {
            sortingComparator = Comparator.comparing(o -> ((StudyGroup) o).getCoordinates().getX());
            filteringPredicate = o -> String.valueOf(o.getCoordinates().getX()).contains(filterText);
        } else if (columnName.equals("Pos Y")) {
            sortingComparator = Comparator.comparing(o -> ((StudyGroup) o).getCoordinates().getY());
            filteringPredicate = o -> String.valueOf(o.getCoordinates().getY()).contains(filterText);
        } else if (columnName.equals("Creation Date")) {
            sortingComparator = Comparator.comparing(StudyGroup::getCreationDate);
            filteringPredicate = o -> String.valueOf(o.getCreationDate()).contains(filterText);
        } else if (columnName.equals("Count of Students")) {
            sortingComparator = Comparator.comparing(StudyGroup::getStudentsCount,
                    Comparator.nullsLast(Long::compareTo));
            filteringPredicate = o -> String.valueOf(o.getStudentsCount()).contains(filterText);
        } else if (columnName.equals("Expelled students")) {
            sortingComparator = Comparator.comparing(StudyGroup::getExpelledStudents);
            filteringPredicate = o -> String.valueOf(o.getExpelledStudents()).contains(filterText);
        } else if (columnName.equals("Transferred students")) {
            sortingComparator = Comparator.comparing(StudyGroup::getTransferredStudents);
            filteringPredicate = o -> String.valueOf(o.getTransferredStudents()).contains(filterText);
        } else if (columnName.equals("Semester")) {
            sortingComparator = Comparator.comparing(StudyGroup::getSemesterEnum,
                    Comparator.nullsLast(Semester::compareTo));
            filteringPredicate = o -> String.valueOf(o.getSemesterEnum()).contains(filterText);
        } else if (columnName.equals("Admin Name")) {
            sortingComparator = (f, s) -> {
                if (f.getGroupAdmin() == null) {
                    return 1;
                } else if (s.getGroupAdmin() == null) {
                    return -1;
                }
                return f.getGroupAdmin().getName().compareTo(s.getGroupAdmin().getName());
            };
            filteringPredicate = o -> {
                Person person = o.getGroupAdmin();
                if (person == null) {
                    return false;
                }
                return String.valueOf(person.getName()).contains(filterText);
            };
        } else if (columnName.equals("Admin BD")) {
            sortingComparator = (f, s) -> {
                if (f.getGroupAdmin() == null) {
                    return 1;
                } else if (s.getGroupAdmin() == null) {
                    return -1;
                }
                return f.getGroupAdmin().getBirthday().compareTo(s.getGroupAdmin().getBirthday());
            };
            filteringPredicate = o -> {
                Person person = o.getGroupAdmin();
                if (person == null) {
                    return false;
                }
                return String.valueOf(person.getBirthday()).contains(filterText);
            };
        } else if (columnName.equals("Admin passport")) {
            sortingComparator = (f, s) -> {
                if (f.getGroupAdmin() == null) {
                    return 1;
                } else if (s.getGroupAdmin() == null) {
                    return -1;
                }
                return f.getGroupAdmin().getPassportID().compareTo(s.getGroupAdmin().getPassportID());
            };
            filteringPredicate = o -> {
                Person person = o.getGroupAdmin();
                if (person == null) {
                    return false;
                }
                return String.valueOf(person.getPassportID()).contains(filterText);
            };
        } else if (columnName.equals("Admin hair")) {
            sortingComparator = (f, s) -> {
                if (f.getGroupAdmin() == null) {
                    return 1;
                } else if (s.getGroupAdmin() == null) {
                    return -1;
                }
                return f.getGroupAdmin().getHairColor().compareTo(s.getGroupAdmin().getHairColor());
            };
            filteringPredicate = o -> {
                Person person = o.getGroupAdmin();
                if (person == null) {
                    return false;
                }
                return String.valueOf(person.getHairColor()).contains(filterText);
            };
        } else {
            sortingComparator = Comparator.comparing(StudyGroup::getId);
            filteringPredicate = o -> true;
        }
        sortingComparator = Comparator.nullsLast(sortingComparator);
        if (filterText == null || filterText.equals("")) {
            filteringPredicate = o -> true;
        }

        List<StudyGroup> sortedList = collection.values().stream()
                .filter(filteringPredicate)
                .sorted(sortingComparator)
                .collect(Collectors.toList());

        // Apply sorting order and update sorting mode for next time
        if (sortAscending) {
            Collections.reverse(sortedList);
        }
        sortAscending = !sortAscending;

        data = new Object[sortedList.size()][columnNames.length];
        int i = 0;
        for (StudyGroup group : sortedList) {
            data[i][0] = group.getId();
            data[i][1] = group.getName();
            data[i][2] = group.getCoordinates().getX();
            data[i][3] = group.getCoordinates().getY();
            data[i][4] = group.getCreationDate();
            data[i][5] = group.getStudentsCount();
            data[i][6] = group.getExpelledStudents();
            data[i][7] = group.getTransferredStudents();
            data[i][8] = group.getSemesterEnum();
            if (group.getGroupAdmin() == null) {
                data[i][9] = null;
                data[i][10] = null;
                data[i][11] = null;
                data[i][12] = null;
            } else {
                data[i][9] = group.getGroupAdmin().getName();
                data[i][10] = group.getGroupAdmin().getBirthday();
                data[i][11] = group.getGroupAdmin().getPassportID();
                data[i][12] = group.getGroupAdmin().getHairColor();
            }
            i++;
        }
        return data;
    }

    class CustomScrollBarUI extends BasicScrollBarUI {
        // Override methods to customize the appearance and behavior of the scroll pane
        // Here's an example of customizing the scroll bar color

        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setPaint(new Color(70, 70, 70, 100));
            g2.fillRoundRect(thumbBounds.x + 5, thumbBounds.y, thumbBounds.width - 10, thumbBounds.height, 10, 10);
            g2.dispose();
        }

    }

    public void updateTable() {
        manager.loadCollectionRecord();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        data = formTableFromCollection(data, "null", columnNames, null);
        for (int i = 0; i < data.length; i++) {
            model.addRow(data[i]);
        }
        model.fireTableDataChanged();
    }

}
