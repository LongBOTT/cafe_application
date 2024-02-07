package com.coffee.BLL;

import com.coffee.DAL.ReceiptDAL;
import com.coffee.DTO.Import_Note;
import com.coffee.DTO.Receipt;
import javafx.util.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReceiptBLL extends Manager<Receipt>{
    private ReceiptDAL receiptDAL;

    public ReceiptBLL() {
        receiptDAL = new ReceiptDAL();
    }

    public ReceiptDAL getReceiptDAL() {
        return receiptDAL;
    }

    public void setReceiptDAL(ReceiptDAL receiptDAL) {
        this.receiptDAL = receiptDAL;
    }

    public Object[][] getData() {
        return getData(receiptDAL.searchReceipts());
    }

    public Pair<Boolean, String> addReceipt(Receipt receipt) {
        if (receiptDAL.addReceipt(receipt) == 0)
            return new Pair<>(false, "Thêm hoá đơn không thành công.");

        return new Pair<>(true, "Thêm hoá đơn thành công.");
    }

    public List<Receipt> searchReceipts(String... conditions) {
        return receiptDAL.searchReceipts(conditions);
    }

    public List<Receipt> findReceipts(String key, String value) {
        List<Receipt> list = new ArrayList<>();
        List<Receipt> receiptList = receiptDAL.searchReceipts();
        for (Receipt receipt : receiptList) {
            if (getValueByKey(receipt, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(receipt);
            }
        }
        return list;
    }

    public List<Receipt> findReceiptsBy(Map<String, Object> conditions) {
        List<Receipt> receipts = receiptDAL.searchReceipts();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            receipts = findObjectsBy(entry.getKey(), entry.getValue(), receipts);
        return receipts;
    }

    @Override
    public Object getValueByKey(Receipt receipt, String key) {
        return switch (key) {
            case "id" -> receipt.getId();
            case "staff_id" -> receipt.getStaff_id();
            case "total" -> receipt.getTotal();
            case "invoice_date" -> receipt.getInvoice_date();
            case "received" -> receipt.getReceived();
            case "excess" -> receipt.getExcess();
            default -> null;
        };
    }

    public static void main(String[] args) {
        ReceiptBLL receiptNoteBLL = new ReceiptBLL();
        Receipt receiptNote = new Receipt(receiptNoteBLL.getAutoID(receiptNoteBLL.searchReceipts()), 1, 0, Date.valueOf("2024-02-07"), 0, 0);
        receiptNoteBLL.addReceipt(receiptNote);
    }
}
