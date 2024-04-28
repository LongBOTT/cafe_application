package com.coffee.GUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.DAL.MySQL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.barchart.BarChart;
import com.coffee.GUI.components.barchart.ModelBarChart;
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
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class StatisticProductGUI extends JPanel {
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private MyTextField txtSearchProductName;
    private PanelSearch searchProductName;
    private JPopupMenu menuProductName;
    private MyTextField txtSearchMaterialName;
    private PanelSearch searchMaterialName;
    private JPopupMenu menuMaterialName;
    private Product selected_product = null;
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
        datePicker = new DatePicker();
        editor = new JFormattedTextField();
        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setUsePanelOption(true);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.setSelectedDateRange(LocalDate.now(), LocalDate.now()); // bao loi o day
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {
                loadData();
            }
        });

        editor.setPreferredSize(new Dimension(240, 30));
        editor.setFont(new Font("Inter", Font.BOLD, 13));

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(270, 700));
        add(scrollPane, BorderLayout.EAST);
        initRightBar();

        content = new JPanel();
        content.setLayout(new MigLayout());
        content.setPreferredSize(new Dimension(730, 700));
        content.setBackground(new Color(238, 238, 238));
        add(content, BorderLayout.CENTER);

        saleChart();
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
                loadData();
            }
        });

        if (concern == 0 || concern == 1 || concern == 3) {
            JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
            chartRadioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayType = 0;
                    loadData();
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
                loadData();
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
                loadData();
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
                loadData();
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
                loadData();
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
                loadData();
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

            selected_product = null;

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

            materialID = -1;

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
            jComboBoxCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadData();
                }
            });
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
            jComboBoxCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadData();
                }
            });
            searchPanel.add(jComboBoxRemainMaterial, "wrap");

            jPanel.add(searchPanel, "wrap");
        }
    }

    private void loadData() {
        if (concern == 0) {
            if (displayType == 0)
                saleChart();
            if (displayType == 1)
                saleReport();
        }
        if (concern == 1) {
            if (displayType == 0)
                profitChart();
            if (displayType == 1)
                profitReport();
        }
    }

    private void profitReport() {
    }

    private void profitChart() {
        Date start = null;
        Date end = null;
        if (datePicker.getDateSQL_Between() != null) {
            start = datePicker.getDateSQL_Between()[0];
            end = datePicker.getDateSQL_Between()[1];
        }
        String product_Name = selected_product == null ? null : selected_product.getName();
        String size = selected_product == null ? null : selected_product.getSize();
        String product_Category = Objects.requireNonNull(jComboBoxCategory.getSelectedItem()).toString();

        assert start != null;
        List<List<String>> dataProfitProduct = MySQL.getTop5ProfitProduct(product_Name, size, product_Category, start.toString(), end.toString());
        List<List<String>> dataCapitalizationRate = MySQL.getTop5CapitalizationRate(product_Name, size, product_Category, start.toString(), end.toString());

        content.removeAll();

        JLabel jLabelTile1 = new JLabel("Top 5 sản phẩm lợi nhuận cao nhất");
        jLabelTile1.setFont(new Font("Inter", Font.BOLD, 15));
        content.add(jLabelTile1, "center, span, wrap");

        BarChart barChartProfitProduct = new BarChart();
        barChartProfitProduct.setPreferredSize(new Dimension(1000, 350));
//        barChartProfitProduct.setFont(new java.awt.Font("sansserif", Font.BOLD, 8));
        content.add(barChartProfitProduct, "wrap");

        JLabel jLabelTile2 = new JLabel("Top 5 sản phẩm theo tỷ suất");
        jLabelTile2.setFont(new Font("Inter", Font.BOLD, 15));
        content.add(jLabelTile2, "center, span, wrap");

        BarChart barChartCapitalizationRate = new BarChart();
        barChartCapitalizationRate.setPreferredSize(new Dimension(1000, 350));
//        barChartCapitalizationRate.setFont(new java.awt.Font("sansserif", Font.BOLD, 8));
        content.add(barChartCapitalizationRate, "wrap");

        barChartProfitProduct.addLegend("Lợi nhuận", new Color(245, 189, 135));
        for (List<String> list : dataProfitProduct) {
            barChartProfitProduct.addData(new ModelBarChart(list.get(1), new double[]{Double.parseDouble(list.get(3))}));
        }
        barChartProfitProduct.start();

        barChartCapitalizationRate.addLegend("Tỷ suất (%)", new Color(245, 189, 135));
        for (List<String> list : dataCapitalizationRate) {
            barChartCapitalizationRate.addData(new ModelBarChart(list.get(1), new double[]{Double.parseDouble(list.get(3))}));
        }
        barChartCapitalizationRate.start();

//        System.out.println(Arrays.toString(dataProfitProduct.toArray()));
//        System.out.println(Arrays.toString(dataCapitalizationRate.toArray()));

        content.repaint();
        content.revalidate();
    }

    private void saleChart() {
        Date start = null;
        Date end = null;
        if (datePicker.getDateSQL_Between() != null) {
            start = datePicker.getDateSQL_Between()[0];
            end = datePicker.getDateSQL_Between()[1];
        }
        String product_Name = selected_product == null ? null : selected_product.getName();
        String size = selected_product == null ? null : selected_product.getSize();
        String product_Category = Objects.requireNonNull(jComboBoxCategory.getSelectedItem()).toString();

        assert start != null;
        List<List<String>> dataSaleProduct = MySQL.getTop5SaleProduct(product_Name, size, product_Category, start.toString(), end.toString());
        List<List<String>> dataBestSeller = MySQL.getTop5BestSellers(product_Name, size, product_Category, start.toString(), end.toString());

        content.removeAll();

        JLabel jLabelTile1 = new JLabel("Top 5 sản phẩm doanh số cao nhất");
        jLabelTile1.setFont(new Font("Inter", Font.BOLD, 15));
        content.add(jLabelTile1, "center, span, wrap");

        BarChart barChartSaleProduct = new BarChart();
        barChartSaleProduct.setPreferredSize(new Dimension(1000, 350));
//        barChartSaleProduct.setFont(new java.awt.Font("sansserif", Font.BOLD, 8));
        content.add(barChartSaleProduct, "wrap");

        JLabel jLabelTile2 = new JLabel("Top 5 sản phẩm bán chạy theo số lượng");
        jLabelTile2.setFont(new Font("Inter", Font.BOLD, 15));
        content.add(jLabelTile2, "center, span, wrap");

        BarChart barChartBestSeller = new BarChart();
        barChartBestSeller.setPreferredSize(new Dimension(1000, 350));
//        barChartBestSeller.setFont(new java.awt.Font("sansserif", Font.BOLD, 8));
        content.add(barChartBestSeller, "wrap");

        barChartSaleProduct.addLegend("Doanh thu", new Color(135, 189, 245));
        for (List<String> list : dataSaleProduct) {
            barChartSaleProduct.addData(new ModelBarChart(list.get(1), new double[]{Double.parseDouble(list.get(3))}));
        }
        barChartSaleProduct.start();

        barChartBestSeller.addLegend("Số lượng bán", new Color(135, 189, 245));
        for (List<String> list : dataBestSeller) {
            barChartBestSeller.addData(new ModelBarChart(list.get(1), new double[]{Double.parseDouble(list.get(3))}));
        }
        barChartBestSeller.start();

//        System.out.println(Arrays.toString(dataSaleProduct.toArray()));
//        System.out.println(Arrays.toString(dataBestSeller.toArray()));

        content.repaint();
        content.revalidate();
    }

    private void saleReport() {

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
                selected_product = new ProductBLL().findProductsBy(Map.of("name", data.getText())).get(0);
                loadData();
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
                loadData();
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
        selected_product = null;
        java.util.List<DataSearch> list = new ArrayList<>();
        java.util.List<String> allName = new ProductBLL().getAllName();
        allName.removeIf(s -> !s.toLowerCase().contains(text.toLowerCase()));
        for (String m : allName) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(m));
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
