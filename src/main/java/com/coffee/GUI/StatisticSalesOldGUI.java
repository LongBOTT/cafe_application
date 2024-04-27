package com.coffee.GUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.LayoutStatistic;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticSalesOldGUI extends LayoutStatistic {
    private DatePicker datePicker;
    private JFormattedTextField editor;

    // bien dung trong productPanel
    private DatePicker datePickerProductPanel;
    private JFormattedTextField editorProductPanel;
    private ButtonGroup btnGroupDisplayTypeProductPanel;
    private ButtonGroup btnGroupConcernsProductPanel;
    private MyTextField txtSearchProductNameProductPanel;
    private PanelSearch searchProductNameProductPanel;
    private JPopupMenu menuProductNameProductPanel;
    private MyTextField txtSearchMaterialNameProductPanel;
    private PanelSearch searchMaterialNameProductPanel;
    private JPopupMenu menuMaterialNameProductPanel;
    private int productID = -1;
    private int materialID = -1;
    private JComboBox<String> jComboBoxCategoryProductPanel;
    private JComboBox<String> jComboBoxRemainMaterialPanel;

    //
    public StatisticSalesOldGUI() {
        super();
        initUIComponents();
        initTxtSearchNameProductPanel();
        setVisible(true);
    }

    private void initUIComponents() {
//        setupDisplayType();
//        setupConcerns();
//        setupTime();

        initProductPanel();
    }

    private void initProductPanel() {
        initTopBarProductPanel();

    }

    private void initTopBarProductPanel() {
        top.removeAll();

        RoundedPanel displayTypePanel = new RoundedPanel();
        displayTypePanel.setBackground(Color.white);
        displayTypePanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        displayTypePanel.setPreferredSize(new Dimension(200, 50));
        top.add(displayTypePanel);

        RoundedPanel concernsPanel = new RoundedPanel();
        concernsPanel.setBackground(Color.white);
        concernsPanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        concernsPanel.setPreferredSize(new Dimension(615, 50));
        top.add(concernsPanel);

        RoundedPanel timePanel = new RoundedPanel();
        timePanel.setBackground(Color.white);
        timePanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        timePanel.setPreferredSize(new Dimension(300, 50));
        top.add(timePanel);

        RoundedPanel searchPanel = new RoundedPanel();
        searchPanel.setBackground(new Color(191, 198, 208));
        searchPanel.setLayout(new MigLayout("", "[]10[]10[]10[]10[]10[]", "0[]0"));
        searchPanel.setPreferredSize(new Dimension(1120, 35));
        top.add(searchPanel);

        JLabel labelDisplayType = new JLabel("Chọn hiển thị");
        labelDisplayType.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(labelDisplayType, "wrap");

        JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
        chartRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JRadioButton reportRadioButton = createRadioButton("Báo cáo");
        reportRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnGroupDisplayTypeProductPanel = new ButtonGroup();
        btnGroupDisplayTypeProductPanel.add(chartRadioButton);
        btnGroupDisplayTypeProductPanel.add(reportRadioButton);

        displayTypePanel.add(chartRadioButton);
        displayTypePanel.add(reportRadioButton);

        JLabel labelConcerns = new JLabel("Mối quan tâm");
        labelConcerns.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(labelConcerns, "wrap");

        JRadioButton radio1 = createRadioButton("Bán hàng");
        radio1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSearchPanel();
            }

            private void loadSearchPanel() {
                searchPanel.removeAll();

                JLabel labelName = new JLabel("Tên Sản Phẩm");
                labelName.setFont(new Font("Inter", Font.BOLD, 14));
                labelName.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelName);

                txtSearchProductNameProductPanel = new MyTextField();
                txtSearchProductNameProductPanel.setPreferredSize(new Dimension(200, 40));
                txtSearchProductNameProductPanel.putClientProperty("JTextField.placeholderText", "Nhập tên sản phẩm tìm kiếm");
                txtSearchProductNameProductPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchProductNameProductPanelMouseClicked(evt);
                    }
                });
                txtSearchProductNameProductPanel.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchProductNameProductPanelKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchProductNameProductPanelKeyReleased(evt);
                    }
                });
                searchPanel.add(txtSearchProductNameProductPanel);

                JLabel labelCategory = new JLabel("Thể Loại Sản Phẩm");
                labelCategory.setFont(new Font("Inter", Font.BOLD, 14));
                labelCategory.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelCategory);

                jComboBoxCategoryProductPanel = new JComboBox<>();
                jComboBoxCategoryProductPanel.setPreferredSize(new Dimension(200, 30));
                jComboBoxCategoryProductPanel.setBackground(new Color(1, 120, 220));
                jComboBoxCategoryProductPanel.setForeground(Color.white);

                jComboBoxCategoryProductPanel.addItem("Tất cả");
                jComboBoxCategoryProductPanel.setSelectedIndex(0);
                for (String category : new ProductBLL().getCategories())
                    jComboBoxCategoryProductPanel.addItem(category);
                searchPanel.add(jComboBoxCategoryProductPanel);

                searchPanel.repaint();
                searchPanel.revalidate();
            }
        });

        JRadioButton radio2 = createRadioButton("Lợi nhuận");
        radio2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSearchPanel();
            }

            private void loadSearchPanel() {
                searchPanel.removeAll();

                JLabel labelName = new JLabel("Tên Sản Phẩm");
                labelName.setFont(new Font("Inter", Font.BOLD, 14));
                labelName.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelName);

                txtSearchProductNameProductPanel = new MyTextField();
                txtSearchProductNameProductPanel.setOpaque(true);
                txtSearchProductNameProductPanel.setPreferredSize(new Dimension(200, 40));
                txtSearchProductNameProductPanel.putClientProperty("JTextField.placeholderText", "Nhập tên sản phẩm tìm kiếm");
                txtSearchProductNameProductPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchProductNameProductPanelMouseClicked(evt);
                    }
                });
                txtSearchProductNameProductPanel.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchProductNameProductPanelKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchProductNameProductPanelKeyReleased(evt);
                    }
                });
                searchPanel.add(txtSearchProductNameProductPanel);

                JLabel labelCategory = new JLabel("Thể Loại Sản Phẩm");
                labelCategory.setFont(new Font("Inter", Font.BOLD, 14));
                labelCategory.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelCategory);

                jComboBoxCategoryProductPanel = new JComboBox<>();
                jComboBoxCategoryProductPanel.setPreferredSize(new Dimension(200, 30));
                jComboBoxCategoryProductPanel.setBackground(new Color(1, 120, 220));
                jComboBoxCategoryProductPanel.setForeground(Color.white);

                jComboBoxCategoryProductPanel.addItem("Tất cả");
                jComboBoxCategoryProductPanel.setSelectedIndex(0);
                for (String category : new ProductBLL().getCategories())
                    jComboBoxCategoryProductPanel.addItem(category);
                searchPanel.add(jComboBoxCategoryProductPanel);

                searchPanel.repaint();
                searchPanel.revalidate();
            }
        });

        JRadioButton radio3 = createRadioButton("Xuất nhập tồn");
        radio3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSearchPanel();
            }

            private void loadSearchPanel() {
                searchPanel.removeAll();

                JLabel labelName = new JLabel("Tên Nguyên liệu");
                labelName.setFont(new Font("Inter", Font.BOLD, 14));
                labelName.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelName);

                txtSearchMaterialNameProductPanel = new MyTextField();
                txtSearchMaterialNameProductPanel.setPreferredSize(new Dimension(200, 40));
                txtSearchMaterialNameProductPanel.putClientProperty("JTextField.placeholderText", "Nhập tên nguyên liệu tìm kiếm");
                txtSearchMaterialNameProductPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchMaterialNameProductPanelMouseClicked(evt);
                    }
                });
                txtSearchMaterialNameProductPanel.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyReleased(evt);
                    }
                });
                searchPanel.add(txtSearchMaterialNameProductPanel);

                JLabel labelRemain = new JLabel("Tồn Kho");
                labelRemain.setFont(new Font("Inter", Font.BOLD, 14));
                labelRemain.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelRemain);

                jComboBoxRemainMaterialPanel = new JComboBox<>();
                jComboBoxRemainMaterialPanel.setPreferredSize(new Dimension(200, 30));
                jComboBoxRemainMaterialPanel.setBackground(new Color(1, 120, 220));
                jComboBoxRemainMaterialPanel.setForeground(Color.white);

                jComboBoxRemainMaterialPanel.addItem("Tất cả");
                jComboBoxRemainMaterialPanel.setSelectedIndex(0);
                jComboBoxRemainMaterialPanel.addItem("Dưới định mức tồn");
                jComboBoxRemainMaterialPanel.addItem("Vượt định mức tồn");
                jComboBoxRemainMaterialPanel.addItem("Còn hàng trong kho");
                jComboBoxRemainMaterialPanel.addItem("Hết hàng trong kho");
                searchPanel.add(jComboBoxRemainMaterialPanel);

                searchPanel.repaint();
                searchPanel.revalidate();
            }
        });

        JRadioButton radio4 = createRadioButton("Xuất nhập tồn chi tiết");
        radio4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSearchPanel();
            }

            private void loadSearchPanel() {
                searchPanel.removeAll();

                JLabel labelName = new JLabel("Tên Nguyên liệu");
                labelName.setFont(new Font("Inter", Font.BOLD, 14));
                labelName.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelName);

                txtSearchMaterialNameProductPanel = new MyTextField();
                txtSearchMaterialNameProductPanel.setPreferredSize(new Dimension(200, 40));
                txtSearchMaterialNameProductPanel.putClientProperty("JTextField.placeholderText", "Nhập tên nguyên liệu tìm kiếm");
                txtSearchMaterialNameProductPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchMaterialNameProductPanelMouseClicked(evt);
                    }
                });
                txtSearchMaterialNameProductPanel.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyReleased(evt);
                    }
                });
                searchPanel.add(txtSearchMaterialNameProductPanel);

                JLabel labelRemain = new JLabel("Tồn Kho");
                labelRemain.setFont(new Font("Inter", Font.BOLD, 14));
                labelRemain.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelRemain);

                jComboBoxRemainMaterialPanel = new JComboBox<>();
                jComboBoxRemainMaterialPanel.setPreferredSize(new Dimension(200, 30));
                jComboBoxRemainMaterialPanel.setBackground(new Color(1, 120, 220));
                jComboBoxRemainMaterialPanel.setForeground(Color.white);

                jComboBoxRemainMaterialPanel.addItem("Tất cả");
                jComboBoxRemainMaterialPanel.setSelectedIndex(0);
                jComboBoxRemainMaterialPanel.addItem("Dưới định mức tồn");
                jComboBoxRemainMaterialPanel.addItem("Vượt định mức tồn");
                jComboBoxRemainMaterialPanel.addItem("Còn hàng trong kho");
                jComboBoxRemainMaterialPanel.addItem("Hết hàng trong kho");
                searchPanel.add(jComboBoxRemainMaterialPanel);

                searchPanel.repaint();
                searchPanel.revalidate();
            }
        });

        JRadioButton radio5 = createRadioButton("Xuất huỷ");
        radio5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSearchPanel();
            }

            private void loadSearchPanel() {
                searchPanel.removeAll();

                JLabel labelName = new JLabel("Tên Nguyên liệu");
                labelName.setFont(new Font("Inter", Font.BOLD, 14));
                labelName.setPreferredSize(new Dimension(150, 40));
                searchPanel.add(labelName);

                txtSearchMaterialNameProductPanel = new MyTextField();
                txtSearchMaterialNameProductPanel.setPreferredSize(new Dimension(200, 40));
                txtSearchMaterialNameProductPanel.putClientProperty("JTextField.placeholderText", "Nhập tên nguyên liệu tìm kiếm");
                txtSearchMaterialNameProductPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchMaterialNameProductPanelMouseClicked(evt);
                    }
                });
                txtSearchMaterialNameProductPanel.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchMaterialNameProductPanelKeyReleased(evt);
                    }
                });
                searchPanel.add(txtSearchMaterialNameProductPanel);

                searchPanel.repaint();
                searchPanel.revalidate();
            }
        });

        btnGroupConcernsProductPanel = new ButtonGroup();
        btnGroupConcernsProductPanel.add(radio1);
        btnGroupConcernsProductPanel.add(radio2);
        btnGroupConcernsProductPanel.add(radio3);
        btnGroupConcernsProductPanel.add(radio4);
        btnGroupConcernsProductPanel.add(radio5);

        concernsPanel.add(radio1);
        concernsPanel.add(radio2);
        concernsPanel.add(radio3);
        concernsPanel.add(radio4);
        concernsPanel.add(radio5);

        JLabel labelTime = new JLabel("Thời gian");
        labelTime.setFont(new Font("Inter", Font.BOLD, 14));

        datePickerProductPanel = new DatePicker();
        editorProductPanel = new JFormattedTextField();

        datePickerProductPanel.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePickerProductPanel.setEditor(editorProductPanel);
        datePickerProductPanel.setCloseAfterSelected(true);
        datePickerProductPanel.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

//        editorProductPanel.setPreferredSize(new Dimension(280, 30));
        editorProductPanel.setFont(new Font("Inter", Font.BOLD, 15));

        timePanel.add(labelTime, "wrap");
        timePanel.add(editorProductPanel);

        top.repaint();
        top.revalidate();
    }

    private void initTxtSearchNameProductPanel() {
        menuProductNameProductPanel = new JPopupMenu();
        searchProductNameProductPanel = new PanelSearch();
        menuProductNameProductPanel.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menuProductNameProductPanel.add(searchProductNameProductPanel);
        menuProductNameProductPanel.setFocusable(false);
        searchProductNameProductPanel.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menuProductNameProductPanel.setVisible(false);
                txtSearchProductNameProductPanel.setText(data.getText());
                Product product = new ProductBLL().findProductsBy(Map.of("name", data.getText())).get(0);
                productID = product.getId();
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                searchProductNameProductPanel.remove(com);
                menuProductNameProductPanel.setPopupSize(200, (searchProductNameProductPanel.getItemSize() * 35) + 2);
                if (searchProductNameProductPanel.getItemSize() == 0) {
                    menuProductNameProductPanel.setVisible(false);
                }
            }
        });

        menuMaterialNameProductPanel = new JPopupMenu();
        searchMaterialNameProductPanel = new PanelSearch();
        menuMaterialNameProductPanel.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menuMaterialNameProductPanel.add(searchMaterialNameProductPanel);
        menuMaterialNameProductPanel.setFocusable(false);
        searchMaterialNameProductPanel.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menuMaterialNameProductPanel.setVisible(false);
                txtSearchMaterialNameProductPanel.setText(data.getText());
                Material material = new MaterialBLL().findMaterialsBy(Map.of("name", data.getText())).get(0);
                materialID = material.getId();
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                searchMaterialNameProductPanel.remove(com);
                menuMaterialNameProductPanel.setPopupSize(200, (searchMaterialNameProductPanel.getItemSize() * 35) + 2);
                if (searchMaterialNameProductPanel.getItemSize() == 0) {
                    menuMaterialNameProductPanel.setVisible(false);
                }
            }
        });
    }

    private void setupDisplayType() {
        JLabel label = new JLabel("Kiểu hiển thị");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(label, "wrap");

        JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
        JRadioButton reportRadioButton = createRadioButton("Báo cáo");

        ButtonGroup btnGroupDisplayType = new ButtonGroup();
        btnGroupDisplayType.add(chartRadioButton);
        btnGroupDisplayType.add(reportRadioButton);

        displayTypePanel.add(chartRadioButton);
        displayTypePanel.add(reportRadioButton);
    }

    private void setupConcerns() {
        JLabel label = new JLabel("Mối quan tâm");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(label, "wrap");

        JRadioButton timeRadioButton = createRadioButton("Thời gian");
        JRadioButton profitRadioButton = createRadioButton("Lợi nhuận");
        JRadioButton discountRadioButton = createRadioButton("Giảm giá HĐ");
        JRadioButton categoryRadioButton = createRadioButton("Thể loại");

        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(timeRadioButton);
        btnGroupConcerns.add(profitRadioButton);
        btnGroupConcerns.add(discountRadioButton);
        btnGroupConcerns.add(categoryRadioButton);

        concernsPanel.add(timeRadioButton);
        concernsPanel.add(profitRadioButton);
        concernsPanel.add(discountRadioButton);
        concernsPanel.add(categoryRadioButton);
    }

    private void setupTime() {
        JLabel label = new JLabel("Thời gian");
        label.setFont(new Font("Inter", Font.BOLD, 14));

        datePicker = new DatePicker();
        editor = new JFormattedTextField();


        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

        editor.setPreferredSize(new Dimension(280, 40));
        editor.setFont(new Font("Inter", Font.BOLD, 15));

        timePanel.add(label, "wrap");
        timePanel.add(editor);
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Inter", Font.PLAIN, 14));
        return radioButton;
    }

    private void txtSearchProductNameProductPanelMouseClicked(java.awt.event.MouseEvent evt) {
        if (searchProductNameProductPanel.getItemSize() > 0 && !txtSearchProductNameProductPanel.getText().isEmpty()) {
            menuProductNameProductPanel.show(txtSearchProductNameProductPanel, 0, txtSearchProductNameProductPanel.getHeight());
            searchProductNameProductPanel.clearSelected();
        }
    }


    private void txtSearchProductNameProductPanelKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearchProductNameProductPanel.getText().trim().toLowerCase();
            searchProductNameProductPanel.setData(searchProductNameProductPanel(text));
            if (searchProductNameProductPanel.getItemSize() > 0 && !txtSearchProductNameProductPanel.getText().isEmpty()) {
                menuProductNameProductPanel.show(txtSearchProductNameProductPanel, 0, txtSearchProductNameProductPanel.getHeight());
                menuProductNameProductPanel.setPopupSize(200, (searchProductNameProductPanel.getItemSize() * 35) + 2);
            } else {
                menuProductNameProductPanel.setVisible(false);
            }
        }
    }

    private java.util.List<DataSearch> searchProductNameProductPanel(String text) {
        productID = -1;
        java.util.List<DataSearch> list = new ArrayList<>();
        List<Product> products = new ProductBLL().findProducts("name", text);
        for (Product m : products) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchProductNameProductPanelKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            searchProductNameProductPanel.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            searchProductNameProductPanel.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = searchProductNameProductPanel.getSelectedText();
            txtSearchProductNameProductPanel.setText(text);

        }
        menuProductNameProductPanel.setVisible(false);
    }

    private void txtSearchMaterialNameProductPanelMouseClicked(java.awt.event.MouseEvent evt) {
        if (searchMaterialNameProductPanel.getItemSize() > 0 && !txtSearchMaterialNameProductPanel.getText().isEmpty()) {
            menuMaterialNameProductPanel.show(txtSearchMaterialNameProductPanel, 0, txtSearchMaterialNameProductPanel.getHeight());
            searchMaterialNameProductPanel.clearSelected();
        }
    }


    private void txtSearchMaterialNameProductPanelKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearchMaterialNameProductPanel.getText().trim().toLowerCase();
            searchMaterialNameProductPanel.setData(searchMaterialNameProductPanel(text));
            if (searchMaterialNameProductPanel.getItemSize() > 0 && !txtSearchMaterialNameProductPanel.getText().isEmpty()) {
                menuMaterialNameProductPanel.show(txtSearchMaterialNameProductPanel, 0, txtSearchMaterialNameProductPanel.getHeight());
                menuMaterialNameProductPanel.setPopupSize(200, (searchMaterialNameProductPanel.getItemSize() * 35) + 2);
            } else {
                menuMaterialNameProductPanel.setVisible(false);
            }
        }
    }

    private java.util.List<DataSearch> searchMaterialNameProductPanel(String text) {
        materialID = -1;
        List<DataSearch> list = new ArrayList<>();
        List<Material> materials = new MaterialBLL().findMaterials("name", text);
        for (Material m : materials) {
            if (list.size() == 7)
                break;
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchMaterialNameProductPanelKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            searchMaterialNameProductPanel.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            searchMaterialNameProductPanel.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = searchMaterialNameProductPanel.getSelectedText();
            txtSearchMaterialNameProductPanel.setText(text);

        }
        menuMaterialNameProductPanel.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(1165, 733);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new StatisticSalesOldGUI());
            frame.setVisible(true);
        });
    }
}
