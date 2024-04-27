package com.coffee.GUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.GUI.components.DatePicker;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticProductGUI extends JPanel {
    //    private RoundedPanel displayTypePanel;
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private MyTextField txtSearchProductName;
    private PanelSearch searchProductName;
    private JPopupMenu menuProductName;
    private MyTextField txtSearchMaterialName;
    private PanelSearch searchMaterialName;
    private JPopupMenu menuMaterialName;
    private int productID = -1;
    private int materialID = -1;
    private JComboBox<String> jComboBoxCategory;
    private JComboBox<String> jComboBoxRemainMaterial;
    private JPanel content;
    private int concern = 0;
    private int displayType = 0;
    private JScrollPane scrollPane;

    public StatisticProductGUI() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        init();
        initTxtSearchName();
        setVisible(true);
    }

    private void init() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(270, 700));
        add(scrollPane, BorderLayout.EAST);
        initRightBar();

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setPreferredSize(new Dimension(730, 700));
        content.setBackground(new Color(238, 238, 238));
        add(content, BorderLayout.CENTER);
    }

    private void initRightBar() {
        JPanel jPanel = new JPanel(new MigLayout("", "5[]5", "10[]10"));
        jPanel.setBackground(new Color(238, 238, 238));
        scrollPane.setViewportView(jPanel);

        // chon hien thi
        RoundedPanel displayTypePanel = new RoundedPanel();
        displayTypePanel.setBackground(new Color(255, 255, 255));
        displayTypePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        displayTypePanel.setPreferredSize(new Dimension(247, 100));
        jPanel.add(displayTypePanel, "wrap");

        ButtonGroup btnGroupDisplayType = new ButtonGroup();
        JLabel labelDisplayType = new JLabel("Chọn hiển thị");
        labelDisplayType.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(labelDisplayType, "wrap");

        JRadioButton reportRadioButton = createRadioButton("Báo cáo");
        reportRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayType = 1;
                // xu ly load bieu do
            }
        });

        if (concern == 0 || concern == 1 || concern == 3) {
            JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
            chartRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayType = 0;
                    // xu ly load bieu do
                }
            });
            chartRadioButton.setSelected(true);
            btnGroupDisplayType.add(chartRadioButton);
            displayTypePanel.add(chartRadioButton, "wrap");
        } else {
            reportRadioButton.setSelected(true);
        }
        btnGroupDisplayType.add(reportRadioButton);
        displayTypePanel.add(reportRadioButton, "wrap");


        // chon mau quan tam
        RoundedPanel concernsPanel = new RoundedPanel();
        concernsPanel.setBackground(new Color(255, 255, 255));
        concernsPanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        concernsPanel.setPreferredSize(new Dimension(247, 220));
        jPanel.add(concernsPanel, "wrap");

        JLabel labelConcerns = new JLabel("Mối quan tâm");
        labelConcerns.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(labelConcerns, "wrap");

        JRadioButton radio1 = createRadioButton("Bán hàng");
        if (concern == 0)
            radio1.setSelected(true);
        radio1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 0;
                initRightBar();
                // xu ly load bieu do
            }
        });

        JRadioButton radio2 = createRadioButton("Lợi nhuận");
        if (concern == 1)
            radio2.setSelected(true);
        radio2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 1;
                initRightBar();
                // xu ly load bieu do
            }
        });

        JRadioButton radio3 = createRadioButton("Xuất nhập tồn");
        if (concern == 2)
            radio3.setSelected(true);
        radio3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 2;
                initRightBar();
                // xu ly load bieu do
            }
        });

        JRadioButton radio4 = createRadioButton("Xuất nhập tồn chi tiết");
        if (concern == 3)
            radio4.setSelected(true);
        radio4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 3;
                initRightBar();
                // xu ly load bieu do
            }
        });

        JRadioButton radio5 = createRadioButton("Xuất huỷ");
        if (concern == 4)
            radio5.setSelected(true);
        radio5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 4;
                initRightBar();
                // xu ly load bieu do
            }
        });

        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(radio1);
        btnGroupConcerns.add(radio2);
        btnGroupConcerns.add(radio3);
        btnGroupConcerns.add(radio4);
        btnGroupConcerns.add(radio5);

        concernsPanel.add(radio1, "wrap");
        concernsPanel.add(radio2, "wrap");
        concernsPanel.add(radio3, "wrap");
        concernsPanel.add(radio4, "wrap");
        concernsPanel.add(radio5, "wrap");

        // thoi gian

        RoundedPanel timePanel = new RoundedPanel();
        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        timePanel.setPreferredSize(new Dimension(247, 50));
        jPanel.add(timePanel, "wrap");

        JLabel labelTime = new JLabel("Thời gian");
        labelTime.setFont(new Font("Inter", Font.BOLD, 14));

        datePicker = new DatePicker();
        editor = new JFormattedTextField();

        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.setSelectedDateRange(LocalDate.now(), LocalDate.now()); // bao loi o day
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

        editor.setPreferredSize(new Dimension(240, 30));
        editor.setFont(new Font("Inter", Font.BOLD, 13));

        timePanel.add(labelTime, "wrap");
        timePanel.add(editor);


        // ten san pham

        RoundedPanel searchNamePanel = new RoundedPanel();
        searchNamePanel.setBackground(new Color(255, 255, 255));
        searchNamePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        searchNamePanel.setPreferredSize(new Dimension(247, 60));
        jPanel.add(searchNamePanel, "wrap");
        if (concern == 0 || concern == 1) {
            JLabel labelName = new JLabel("Tên Sản Phẩm");
            labelName.setFont(new Font("Inter", Font.BOLD, 14));
            searchNamePanel.add(labelName, "wrap");

            txtSearchProductName = new MyTextField();
            txtSearchProductName.setPreferredSize(new Dimension(200, 40));
            txtSearchProductName.putClientProperty("JTextField.placeholderText", "Nhập tên sản phẩm tìm kiếm");
            txtSearchProductName.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    txtSearchProductNameMouseClicked(evt);
                }
            });
            txtSearchProductName.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    txtSearchProductNameKeyPressed(evt);
                }

                public void keyReleased(java.awt.event.KeyEvent evt) {
                    txtSearchProductNameKeyReleased(evt);
                }
            });
            searchNamePanel.add(txtSearchProductName, "wrap");
        } else {
            JLabel labelName = new JLabel("Tên Nguyên Liệu");
            labelName.setFont(new Font("Inter", Font.BOLD, 14));
            searchNamePanel.add(labelName, "wrap");

            txtSearchMaterialName = new MyTextField();
            txtSearchMaterialName.setPreferredSize(new Dimension(200, 40));
            txtSearchMaterialName.putClientProperty("JTextField.placeholderText", "Nhập tên nguyên liệu tìm kiếm");
            txtSearchMaterialName.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    txtSearchMaterialNameMouseClicked(evt);
                }
            });
            txtSearchMaterialName.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    txtSearchMaterialNameKeyPressed(evt);
                }

                public void keyReleased(java.awt.event.KeyEvent evt) {
                    txtSearchMaterialNameKeyReleased(evt);
                }
            });
            searchNamePanel.add(txtSearchMaterialName, "wrap");
        }

        // the loai hoac ton kho

        RoundedPanel searchPanel = new RoundedPanel();
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        searchPanel.setPreferredSize(new Dimension(247, 50));
        if (concern == 0 || concern == 1) {
            JLabel labelCategory = new JLabel("Thể Loại Sản Phẩm");
            labelCategory.setFont(new Font("Inter", Font.BOLD, 14));
            searchPanel.add(labelCategory, "wrap");

            jComboBoxCategory = new JComboBox<>();
            jComboBoxCategory.setPreferredSize(new Dimension(200, 30));
            jComboBoxCategory.setBackground(new Color(1, 120, 220));
            jComboBoxCategory.setForeground(Color.white);

            jComboBoxCategory.addItem("Tất cả");
            jComboBoxCategory.setSelectedIndex(0);
            for (String category : new ProductBLL().getCategories())
                jComboBoxCategory.addItem(category);
            searchPanel.add(jComboBoxCategory, "wrap");

            jPanel.add(searchPanel, "wrap");
        }
        if (concern == 2 || concern == 3) {
            JLabel labelRemain = new JLabel("Tồn Kho");
            labelRemain.setFont(new Font("Inter", Font.BOLD, 14));
            searchPanel.add(labelRemain, "wrap");

            jComboBoxRemainMaterial = new JComboBox<>();
            jComboBoxRemainMaterial.setPreferredSize(new Dimension(200, 30));
            jComboBoxRemainMaterial.setBackground(new Color(1, 120, 220));
            jComboBoxRemainMaterial.setForeground(Color.white);

            jComboBoxRemainMaterial.addItem("Tất cả");
            jComboBoxRemainMaterial.setSelectedIndex(0);
            jComboBoxRemainMaterial.addItem("Dưới định mức tồn");
            jComboBoxRemainMaterial.addItem("Vượt định mức tồn");
            jComboBoxRemainMaterial.addItem("Còn hàng trong kho");
            jComboBoxRemainMaterial.addItem("Hết hàng trong kho");
            searchPanel.add(jComboBoxRemainMaterial, "wrap");

            jPanel.add(searchPanel, "wrap");
        }
    }

    private void initTxtSearchName() {
        menuProductName = new JPopupMenu();
        searchProductName = new PanelSearch();
        menuProductName.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menuProductName.add(searchProductName);
        menuProductName.setFocusable(false);
        searchProductName.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menuProductName.setVisible(false);
                txtSearchProductName.setText(data.getText());
                Product product = new ProductBLL().findProductsBy(Map.of("name", data.getText())).get(0);
                productID = product.getId();
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                searchProductName.remove(com);
                menuProductName.setPopupSize(200, (searchProductName.getItemSize() * 35) + 2);
                if (searchProductName.getItemSize() == 0) {
                    menuProductName.setVisible(false);
                }
            }
        });

        menuMaterialName = new JPopupMenu();
        searchMaterialName = new PanelSearch();
        menuMaterialName.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menuMaterialName.add(searchMaterialName);
        menuMaterialName.setFocusable(false);
        searchMaterialName.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menuMaterialName.setVisible(false);
                txtSearchMaterialName.setText(data.getText());
                Material material = new MaterialBLL().findMaterialsBy(Map.of("name", data.getText())).get(0);
                materialID = material.getId();
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                searchMaterialName.remove(com);
                menuMaterialName.setPopupSize(200, (searchMaterialName.getItemSize() * 35) + 2);
                if (searchMaterialName.getItemSize() == 0) {
                    menuMaterialName.setVisible(false);
                }
            }
        });
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Inter", Font.PLAIN, 14));
        return radioButton;
    }

    private void txtSearchProductNameMouseClicked(java.awt.event.MouseEvent evt) {
        if (searchProductName.getItemSize() > 0 && !txtSearchProductName.getText().isEmpty()) {
            menuProductName.show(txtSearchProductName, 0, txtSearchProductName.getHeight());
            searchProductName.clearSelected();
        }
    }


    private void txtSearchProductNameKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearchProductName.getText().trim().toLowerCase();
            searchProductName.setData(searchProductName(text));
            if (searchProductName.getItemSize() > 0 && !txtSearchProductName.getText().isEmpty()) {
                menuProductName.show(txtSearchProductName, 0, txtSearchProductName.getHeight());
                menuProductName.setPopupSize(200, (searchProductName.getItemSize() * 35) + 2);
            } else {
                menuProductName.setVisible(false);
            }
        }
    }

    private java.util.List<DataSearch> searchProductName(String text) {
        productID = -1;
        java.util.List<DataSearch> list = new ArrayList<>();
        java.util.List<Product> products = new ProductBLL().findProducts("name", text);
        for (Product m : products) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchProductNameKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            searchProductName.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            searchProductName.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = searchProductName.getSelectedText();
            txtSearchProductName.setText(text);

        }
        menuProductName.setVisible(false);
    }

    private void txtSearchMaterialNameMouseClicked(java.awt.event.MouseEvent evt) {
        if (searchMaterialName.getItemSize() > 0 && !txtSearchMaterialName.getText().isEmpty()) {
            menuMaterialName.show(txtSearchMaterialName, 0, txtSearchMaterialName.getHeight());
            searchMaterialName.clearSelected();
        }
    }


    private void txtSearchMaterialNameKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearchMaterialName.getText().trim().toLowerCase();
            searchMaterialName.setData(searchMaterialName(text));
            if (searchMaterialName.getItemSize() > 0 && !txtSearchMaterialName.getText().isEmpty()) {
                menuMaterialName.show(txtSearchMaterialName, 0, txtSearchMaterialName.getHeight());
                menuMaterialName.setPopupSize(200, (searchMaterialName.getItemSize() * 35) + 2);
            } else {
                menuMaterialName.setVisible(false);
            }
        }
    }

    private java.util.List<DataSearch> searchMaterialName(String text) {
        materialID = -1;
        java.util.List<DataSearch> list = new ArrayList<>();
        List<Material> materials = new MaterialBLL().findMaterials("name", text);
        for (Material m : materials) {
            if (list.size() == 7)
                break;
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchMaterialNameKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            searchMaterialName.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            searchMaterialName.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = searchMaterialName.getSelectedText();
            txtSearchMaterialName.setText(text);

        }
        menuMaterialName.setVisible(false);
    }
}
