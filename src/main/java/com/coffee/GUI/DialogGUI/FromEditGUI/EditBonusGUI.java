package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.BonusBLL;
import com.coffee.DTO.Bonus;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EditBonusGUI extends DialogForm {
    private JLabel titleName;
    private java.util.List<JLabel> attributeBonus;
    private List<JTextField> jTextFieldBonus;
    private JComboBox<String> jComboBoxBonusType;

    private JButton buttonCancel;
    private JButton buttonEdit;
    private BonusBLL bonusBLL = new BonusBLL();
    private Bonus bonus;

    public EditBonusGUI(Bonus bonus) {
        super();
        super.setTitle("Cập nhật thông tin phụ cấp");
        super.setSize(new Dimension(600, 320));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.bonus = bonus;
        init(bonus);
        setVisible(true);
    }

    private void init(Bonus bonus) {
        titleName = new JLabel();
        attributeBonus = new ArrayList<>();
        jTextFieldBonus = new ArrayList<>();
        jComboBoxBonusType = new JComboBox<>();
        buttonCancel = new JButton("Huỷ");
        buttonEdit = new JButton("Cập nhật");
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Cập nhật thông tin phụ cấp");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Tên phụ cấp", "Số tiền phụ cấp", "Loại phụ cấp"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeBonus.add(label);
            content.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tên phụ cấp")) {
                textField.setText(bonus.getName());
            }
            if (string.equals("Số tiền phụ cấp")) {
                textField.setText(String.valueOf(bonus.getBonus_amount()));
                textField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });
            }
            if (string.equals("Loại phụ cấp")) {
                jComboBoxBonusType.addItem("Phụ cấp theo ngày làm");
                jComboBoxBonusType.addItem("Phụ cấp theo tháng làm");

                jComboBoxBonusType.setSelectedIndex(bonus.getBonus_type());

                jComboBoxBonusType.setPreferredSize(new Dimension(1000, 30));
                jComboBoxBonusType.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                jComboBoxBonusType.setBackground(new Color(245, 246, 250));
                content.add(jComboBoxBonusType, "wrap");

                continue;
            }

            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldBonus.add(textField);
            content.add(textField, "wrap");

        }
        buttonCancel.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonCancel.setBackground(new Color(0xD54218));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonCancel.setBackground(Color.white);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cancel();
            }
        });
        containerButton.add(buttonCancel);

        buttonEdit.setPreferredSize(new Dimension(100, 30));
        buttonEdit.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editBonus();
            }
        });
        containerButton.add(buttonEdit);
    }

    private void editBonus() {
        Pair<Boolean, String> result;
        int id, bonus_type;
        String name;
        double bonus_amount;

        id = bonus.getId();
        name = jTextFieldBonus.get(0).getText();
        bonus_amount = Double.parseDouble(jTextFieldBonus.get(1).getText());
        bonus_type = jComboBoxBonusType.getSelectedIndex();

        Bonus bonus = new Bonus(id, name, bonus_amount, bonus_type); // false là tồn tại, true là đã xoá

        result = bonusBLL.updateBonus(bonus);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
