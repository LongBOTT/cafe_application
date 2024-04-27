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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticProductGUI extends JPanel {
    private RoundedPanel displayTypePanel;
    private RoundedPanel searchPanel;
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private ButtonGroup btnGroupDisplayType;
    private ButtonGroup btnGroupConcerns;
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

    public StatisticProductGUI() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        init();
        initTxtSearchName();
        setVisible(true);
    }

    private void init() {
        initTopBar();

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setPreferredSize(new Dimension(1165, 670));
        content.setBackground(Color.white);
        add(content, BorderLayout.CENTER);
    }

    private void initTopBar() {
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setPreferredSize(new Dimension(1165, 110));
//        top.setBackground(new Color(191, 198, 208));
        top.setBackground(Color.white);
        top.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(top, BorderLayout.NORTH);

        displayTypePanel = new RoundedPanel();
        displayTypePanel.setBackground(new Color(227, 242, 250));
        displayTypePanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        displayTypePanel.setPreferredSize(new Dimension(200, 50));
        top.add(displayTypePanel);

        RoundedPanel concernsPanel = new RoundedPanel();
        concernsPanel.setBackground(new Color(227, 242, 250));
        concernsPanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        concernsPanel.setPreferredSize(new Dimension(615, 50));
        top.add(concernsPanel);

        RoundedPanel timePanel = new RoundedPanel();
        timePanel.setBackground(new Color(227, 242, 250));
        timePanel.setLayout(new MigLayout("", "[]", "0[]0[]0[]0"));
        timePanel.setPreferredSize(new Dimension(330, 50));
        top.add(timePanel);

        searchPanel = new RoundedPanel();
        searchPanel.setBackground(new Color(227, 242, 250));
        searchPanel.setLayout(new MigLayout("", "[]20[]20[]20[]", "0[]0"));
        searchPanel.setPreferredSize(new Dimension(1155, 45));
        top.add(searchPanel);

        JLabel labelConcerns = new JLabel("Mối quan tâm");
        labelConcerns.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(labelConcerns, "wrap");

        JRadioButton radio1 = createRadioButton("Bán hàng");
        radio1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 0;
                loadSearchAndViewType();
            }
        });

        JRadioButton radio2 = createRadioButton("Lợi nhuận");
        radio2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 1;
                loadSearchAndViewType();
            }
        });

        JRadioButton radio3 = createRadioButton("Xuất nhập tồn");
        radio3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 2;
                loadSearchAndViewType();
            }
        });

        JRadioButton radio4 = createRadioButton("Xuất nhập tồn chi tiết");
        radio4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 3;
                loadSearchAndViewType();
            }
        });

        JRadioButton radio5 = createRadioButton("Xuất huỷ");
        radio5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 4;
                loadSearchAndViewType();
            }
        });

        btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(radio1);
        btnGroupConcerns.add(radio2);
        btnGroupConcerns.add(radio3);
        btnGroupConcerns.add(radio4);
        btnGroupConcerns.add(radio5);

        concernsPanel.add(radio1);
        concernsPanel.add(radio2);
        concernsPanel.add(radio3);
        concernsPanel.add(radio4);
        concernsPanel.add(radio5);

        JLabel labelTime = new JLabel("Thời gian");
        labelTime.setFont(new Font("Inter", Font.BOLD, 14));

        datePicker = new DatePicker();
        editor = new JFormattedTextField();

        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.now();
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

        editor.setPreferredSize(new Dimension(280, 30));
        editor.setFont(new Font("Inter", Font.BOLD, 15));

        timePanel.add(labelTime, "wrap");
        timePanel.add(editor);

        radio1.setSelected(true);
        loadSearchAndViewType();
    }

    private void loadSearchAndViewType() {
        if (concern == 0 || concern == 1) {
            displayTypePanel.removeAll();

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

            chartRadioButton.setSelected(true);

            btnGroupDisplayType = new ButtonGroup();
            btnGroupDisplayType.add(chartRadioButton);
            btnGroupDisplayType.add(reportRadioButton);

            displayTypePanel.add(chartRadioButton);
            displayTypePanel.add(reportRadioButton);

            displayTypePanel.repaint();
            displayTypePanel.revalidate();

            searchPanel.removeAll();

            JLabel labelName = new JLabel("Tên Sản Phẩm");
            labelName.setFont(new Font("Inter", Font.BOLD, 14));
            searchPanel.add(labelName);

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
            searchPanel.add(txtSearchProductName);

            JLabel labelCategory = new JLabel("Thể Loại Sản Phẩm");
            labelCategory.setFont(new Font("Inter", Font.BOLD, 14));
            searchPanel.add(labelCategory);

            jComboBoxCategory = new JComboBox<>();
            jComboBoxCategory.setPreferredSize(new Dimension(200, 30));
            jComboBoxCategory.setBackground(new Color(1, 120, 220));
            jComboBoxCategory.setForeground(Color.white);

            jComboBoxCategory.addItem("Tất cả");
            jComboBoxCategory.setSelectedIndex(0);
            for (String category : new ProductBLL().getCategories())
                jComboBoxCategory.addItem(category);
            searchPanel.add(jComboBoxCategory);

            searchPanel.repaint();
            searchPanel.revalidate();
        } else {
            btnGroupDisplayType = new ButtonGroup();
            displayTypePanel.removeAll();

            JLabel labelDisplayType = new JLabel("Chọn hiển thị");
            labelDisplayType.setFont(new Font("Inter", Font.BOLD, 14));
            displayTypePanel.add(labelDisplayType, "wrap");

            JRadioButton reportRadioButton = createRadioButton("Báo cáo");
            reportRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

            if (concern == 3) {
                JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
                chartRadioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                chartRadioButton.setSelected(true);
                btnGroupDisplayType.add(chartRadioButton);
                displayTypePanel.add(chartRadioButton);
            } else {
                reportRadioButton.setSelected(true);
            }
            btnGroupDisplayType.add(reportRadioButton);
            displayTypePanel.add(reportRadioButton);


            displayTypePanel.repaint();
            displayTypePanel.revalidate();

            searchPanel.removeAll();

            JLabel labelName = new JLabel("Tên Nguyên Liệu");
            labelName.setFont(new Font("Inter", Font.BOLD, 14));
            searchPanel.add(labelName);

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
            searchPanel.add(txtSearchMaterialName);

            if (concern != 4) {
                JLabel labelRemain = new JLabel("Tồn Kho");
                labelRemain.setFont(new Font("Inter", Font.BOLD, 14));
                searchPanel.add(labelRemain);

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
                searchPanel.add(jComboBoxRemainMaterial);
            }
            searchPanel.repaint();
            searchPanel.revalidate();
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
