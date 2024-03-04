package com.coffee.BLL;

import com.coffee.DAL.StaffDAL;
import com.coffee.DTO.Staff;
import com.coffee.utils.VNString;
import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StaffBLL extends Manager<Staff> {
    private StaffDAL staffDAL;

    public StaffBLL() {
        staffDAL = new StaffDAL();
    }

    public StaffDAL getStaffDAL() {
        return staffDAL;
    }

    public void setStaffDAL(StaffDAL staffDAL) {
        this.staffDAL = staffDAL;
    }

    public Object[][] getData() {
        return getData(staffDAL.searchStaffs("deleted = 0"));
    }


    public Pair<Boolean, String> addStaff(Staff staff) {
        Pair<Boolean, String> result = validateStaffAll(staff);
        if (!result.getKey())
            return new Pair<>(false, result.getValue());

        if (staffDAL.addStaff(staff) == 0)
            return new Pair<>(false, "Thêm nhân viên không thành công.");

        return new Pair<>(true, "Thêm nhân viên thành công.");
    }

    public Pair<Boolean, String> updateStaff(Staff staff) {
        Pair<Boolean, String> result = validateStaffAll(staff);
        if (!result.getKey())
            return new Pair<>(false, result.getValue());
        if (staffDAL.updateStaff(staff) == 0)
            return new Pair<>(false, "Cập nhật nhân viên không thành công.");

        return new Pair<>(true, "Cập nhật nhân viên thành công.");
    }

    public Pair<Boolean, String> deleteStaff(Staff staff) {

        if (staffDAL.deleteStaff("id = " + staff.getId()) == 0)
            return new Pair<>(false, "Xoá nhân viên không thành công.");

        return new Pair<>(true, "Xoá nhân viên thành công.");
    }

    public List<Staff> searchStaffs(String... conditions) {
        return staffDAL.searchStaffs(conditions);
    }

    public List<Staff> findStaffs(String key, String value) {
        List<Staff> list = new ArrayList<>();
        List<Staff> staffList = staffDAL.searchStaffs("deleted = 0");
        for (Staff staff : staffList) {
            if (getValueByKey(staff, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(staff);
            }
        }
        return list;
    }

    public List<Staff> findStaffsBy(Map<String, Object> conditions) {
        List<Staff> staffs = staffDAL.searchStaffs("deleted = 0");
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            staffs = findObjectsBy(entry.getKey(), entry.getValue(), staffs);
        return staffs;
    }

    public Pair<Boolean, String> validateStaffAll(Staff staff) {
        Pair<Boolean, String> result;

        result = validateStaffNo(staff.getStaffNo());
        if (!result.getKey())
            return new Pair<>(false, result.getValue());

        result = validateName(staff.getName());
        if (!result.getKey())
            return new Pair<>(false, result.getValue());

        result = validatePhone(staff.getPhone());
        if (!result.getKey())
            return new Pair<>(false, result.getValue());

        result = validateEmail(staff.getEmail());
        if (!result.getKey())
            return new Pair<>(false, result.getValue());

        result = exists(staff);
        if (result.getKey())
            return new Pair<>(false, result.getValue());

        return new Pair<>(true, "");
    }

    public Pair<Boolean, String> exists(Staff newStaff) {
        List<Staff> staffs = staffDAL.searchStaffs("no = '" + newStaff.getStaffNo() + "'", "deleted = 0");
        if (!staffs.isEmpty()) {
            return new Pair<>(true, "Số căn cước công dân của nhân viên đã tồn tại.");
        }

        staffs = staffDAL.searchStaffs("phone = '" + newStaff.getPhone() + "'", "deleted = 0");
        if (!staffs.isEmpty()) {
            return new Pair<>(true, "Số điện thoại nhân viên đã tồn tại.");
        }

        staffs = staffDAL.searchStaffs("email = '" + newStaff.getEmail() + "'", "deleted = 0");
        if (!staffs.isEmpty()) {
            return new Pair<>(true, "Email nhân viên đã tồn tại.");
        }
        return new Pair<>(false, "");
    }


    private Pair<Boolean, String> validateStaffNo(String no) {
        if (no.isBlank())
            return new Pair<>(false, "Số căn cước công dân của nhân viên không được bỏ trống.");
        if (VNString.containsUnicode(no))
            return new Pair<>(false, "Số căn cước công dân của nhân viên không được chứa unicode.");
        if (VNString.containsAlphabet(no))
            return new Pair<>(false, "Số căn cước công dân của nhân viên không được chứa chữ cái.");
        if (!VNString.checkNo(no))
            return new Pair<>(false, "Số căn cước công dân của nhân viên phải bao gồm 12 số.");

        return new Pair<>(true, no);
    }


    public Pair<Boolean, String> validateName(String name) {
        if (name.isBlank())
            return new Pair<>(false, "Tên nhân viên không được bỏ trống.");
        if (VNString.containsNumber(name))
            return new Pair<>(false, "Tên nhân viên không không được chứa số.");
        if (VNString.containsSpecial(name))
            return new Pair<>(false, "Tên nhân viên không không được chứa ký tự đặc biệt.");
        return new Pair<>(true, name);
    }

    public Pair<Boolean, String> validatePhone(String phone) {
        if (phone.isBlank())
            return new Pair<>(false, "Số điện thoại nhân viên không được bỏ trống.");
        if (!VNString.checkFormatPhone(phone))
            return new Pair<>(false, "Số điện thoại nhân viên phải bắt đầu với \"0x\" hoặc \"+84x\" hoặc \"84x\" với \"x\" thuộc \\{\\\\3, 5, 7, 8, 9\\}\\\\.");
        return new Pair<>(true, phone);
    }

    public Pair<Boolean, String> validateEmail(String email) {
        if (email.isBlank())
            return new Pair<>(false, "Email nhân viên không được để trống.");
        if (VNString.containsUnicode(email))
            return new Pair<>(false, "Email nhân viên không được chứa unicode.");
        if (!VNString.checkFormatOfEmail(email))
            return new Pair<>(false, "Email nhân viên phải theo định dạng (username@domain.name).");
        return new Pair<>(true, email);
    }

    @Override
    public Object getValueByKey(Staff staff, String key) {
        return switch (key) {
            case "id" -> staff.getId();
            case "no" -> staff.getStaffNo();
            case "name" -> staff.getName();
            case "gender" -> staff.isGender();
            case "birthdate" -> staff.getBirthdate();
            case "phone" -> staff.getPhone();
            case "address" -> staff.getAddress();
            case "email" -> staff.getEmail();
            default -> null;
        };
    }

    public static void main(String[] args) {
        StaffBLL staffBLL = new StaffBLL();
        Staff staff = new Staff(14, "078203023644", "a", false, Date.valueOf("2003-08-30"), "0963333984", "4", "colung3008@gmail.com", false);

        System.out.println(staffBLL.addStaff(staff));

        staff.setAddress("514/26 Lê Đức Thọ P17 Gò Vấp TPHCM ");
//        staffBLL.updateStaff(staff);
//        staffBLL.deleteStaff(staff);
    }

}
