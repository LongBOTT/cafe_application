package com.coffee.BLL;

import com.coffee.DAL.SupplierDAL;
import com.coffee.DTO.Supplier;
import com.coffee.utils.VNString;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SupplierBLL extends Manager<Supplier> {
    private SupplierDAL supplierDAL;

    public SupplierBLL() {
        supplierDAL = new SupplierDAL();
    }

    public SupplierDAL getSupplierDAL() {
        return supplierDAL;
    }

    public void setSupplierDAL(SupplierDAL supplierDAL) {
        this.supplierDAL = supplierDAL;
    }

    public Object[][] getData() {
        return getData(supplierDAL.searchSuppliers("deleted = 0"));
    }

    public Pair<Boolean, String> addSupplier(Supplier supplier) {
        Pair<Boolean, String> result = validateSupplierAll(supplier);
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (supplierDAL.addSupplier(supplier) == 0)
            return new Pair<>(false, "Thêm nhà cung cấp không thành công.");

        return new Pair<>(true, "Thêm nhà cung cấp thành công.");

    }

    public Pair<Boolean, String> updateSupplier(Supplier supplier) {
        Pair<Boolean, String> result = validateSupplierAll(supplier);

        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (supplierDAL.updateSupplier(supplier) == 0)
            return new Pair<>(false, "Cập nhật nhà cung cấp không thành công.");

        return new Pair<>(true, "Cập nhật nhà cung cấp thành công.");
    }

    public Pair<Boolean, String> deleteSupplier(Supplier supplier) {

        if (supplierDAL.deleteSupplier("id = " + supplier.getId()) == 0)
            return new Pair<>(false, "Xoá nhà cung cấp không thành công.");

        return new Pair<>(true, "Xoá nhà cung cấp thành công.");
    }

    public List<Supplier> searchSuppliers(String... conditions) {
        return supplierDAL.searchSuppliers(conditions);
    }

    public List<Supplier> findSuppliers(String key, String value) {
        List<Supplier> list = new ArrayList<>();
        List<Supplier> supplierList = searchSuppliers("deleted = 0");
        for (Supplier supplier : supplierList) {
            if (getValueByKey(supplier, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(supplier);
            }
        }
        return list;
    }

    public List<Supplier> findSuppliersBy(Map<String, Object> conditions) {
        List<Supplier> suppliers = searchSuppliers("deleted = 0");
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            suppliers = findObjectsBy(entry.getKey(), entry.getValue(), suppliers);
        return suppliers;
    }

    public Pair<Boolean, String> validateSupplierAll(Supplier supplier) {
        Pair<Boolean, String> result;

        result = validateName(supplier.getName());
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        result = validatePhone(supplier.getPhone());
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        result = validateEmail(supplier.getEmail());
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }
        result = exists(supplier);
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }
        return new Pair<>(true, "");
    }

    public Pair<Boolean, String> exists(Supplier newSupplier) {
        List<Supplier> suppliers = supplierDAL.searchSuppliers("phone = '" + newSupplier.getPhone() + "'", "deleted = 0");
        if (!suppliers.isEmpty()) {
            return new Pair<>(true, "Số điện thoại nhà cung cấp đã tồn tại.");
        }
        suppliers = supplierDAL.searchSuppliers("email = '" + newSupplier.getEmail() + "'", "deleted = 0");
        if (!suppliers.isEmpty()) {
            return new Pair<>(true, "Email nhà cung cấp đã tồn tại.");
        }
        return new Pair<>(false, "");
    }

    private static Pair<Boolean, String> validateName(String name) {
        if (name.isBlank())
            return new Pair<>(false, "Tên nhà cung cấp không được để trống.");
//        if (VNString.containsSpecial(name))
//            return new Pair<>(false, "Tên nhà cung cấp không được chứa ký tự đặc biệt.");
        if (VNString.containsNumber(name))
            return new Pair<>(false, "Tên nhà cung cấp không được chứa số.");
        return new Pair<>(true, name);
    }

    public Pair<Boolean, String> validatePhone(String phone) {
        if (phone.isBlank())
            return new Pair<>(false, "Số điện thoại nhà cung cấp không được bỏ trống.");
        if (!VNString.checkFormatPhone(phone))
            return new Pair<>(false, "Số điện thoại nhà cung cấp phải bắt đầu với \"0x\" hoặc \"+84x\" hoặc \"84x\" với \"x\" thuộc \\{\\\\3, 5, 7, 8, 9\\}\\\\.");
        return new Pair<>(true, phone);
    }


    public Pair<Boolean, String> validateEmail(String email) {
        if (email.isBlank())
            return new Pair<>(false, "Email nhà cung cấp không được để trống.");
        if (VNString.containsUnicode(email))
            return new Pair<>(false, "Email nhà cung cấp không được chứa unicode.");
        if (!VNString.checkFormatOfEmail(email))
            return new Pair<>(false, "Email nhà cung cấp phải theo định dạng (username@domain.name).");
        return new Pair<>(true, email);
    }


    @Override
    public Object getValueByKey(Supplier supplier, String key) {
        return switch (key) {
            case "id" -> supplier.getId();
            case "name" -> supplier.getName();
            case "phone" -> supplier.getPhone();
            case "address" -> supplier.getAddress();
            case "email" -> supplier.getEmail();
            default -> null;
        };
    }

    public static void main(String[] args) {
        SupplierBLL supplierBLL = new SupplierBLL();
        Supplier supplier = new Supplier(15, "abc", "0963333946", "514", "a@gmail.com", false);
        supplierBLL.addSupplier(supplier);
//        supplier.setName("xyz");
//        supplierBLL.updateSupplier(supplier);
//        supplierBLL.deleteSupplier(supplier);
    }
}
