package application;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

import connectDB.ConnectDB;


public class QuanLyDichVu_UI extends JFrame implements ActionListener, MouseListener, KeyListener {

    public JPanel pnMain;
    private JTable table;
    private DefaultTableModel modelTable;
    private JTextField txtTim, txtMaDV, txtTenDV, txtDonGia;
    private JButton btnTim, btnThem, btnSua, btnXoa, btnLamLai, btnXemTatCa;
    private JLabel lbShowMessages;
    private final int SUCCESS = 1, ERROR = 0;
    ImageIcon blueAddIcon = new ImageIcon("data/images/blueAdd_16.png");
    ImageIcon editIcon = new ImageIcon("data/images/edit2_16.png");
    ImageIcon deleteIcon = new ImageIcon("data/images/trash2_16.png");
    ImageIcon refreshIcon = new ImageIcon("data/images/refresh_16.png");
    ImageIcon searchIcon = new ImageIcon("data/images/search_16.png");
    ImageIcon checkIcon = new ImageIcon("data/images/check2_16.png");
    ImageIcon errorIcon = new ImageIcon("data/images/cancel_16.png");

    public QuanLyDichVu_UI() {
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSize(1000, 670);
        setTitle("Quản Lý Dịch Vụ");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pnMain = new JPanel();
        pnMain.setLayout(null);
        pnMain.setBounds(0, 0, 1000, 670);

        getContentPane().add(pnMain);

        JLabel lbTitle = new JLabel("Danh Mục Dịch Vụ");
        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle.setFont(new Font("Dialog", Font.BOLD, 20));
        lbTitle.setBounds(10, 11, 972, 30);
        pnMain.add(lbTitle);

        JPanel pnInfoDV = new JPanel();
        pnInfoDV.setBorder(
                new TitledBorder(null, "Thông tin dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnInfoDV.setBounds(10, 46, 391, 582);
        pnMain.add(pnInfoDV);
        pnInfoDV.setLayout(null);

        JLabel lbMaDV = new JLabel("Mã dịch vụ: ");
        lbMaDV.setBounds(12, 23, 80, 20);
        pnInfoDV.add(lbMaDV);

        txtMaDV = new JTextField();
        txtMaDV.setBounds(100, 23, 279, 20);
        txtMaDV.setEditable(false);
        txtMaDV.setColumns(10);
        pnInfoDV.add(txtMaDV);

        JLabel lbTenDV = new JLabel("Tên dịch vụ:");
        lbTenDV.setBounds(12, 55, 80, 20);
        pnInfoDV.add(lbTenDV);

        txtTenDV = new JTextField();
        txtTenDV.setBounds(100, 55, 279, 20);
        pnInfoDV.add(txtTenDV);
        txtTenDV.setColumns(10);

        JLabel lbDonGia = new JLabel("Đơn giá:");
        lbDonGia.setBounds(12, 87, 80, 16);
        pnInfoDV.add(lbDonGia);

        txtDonGia = new JTextField();
        txtDonGia.setText("0.0");
        txtDonGia.setBounds(100, 85, 279, 20);
        pnInfoDV.add(txtDonGia);
        txtDonGia.setColumns(10);

        btnThem = new JButton("Thêm", blueAddIcon);
        btnThem.setBounds(12, 143, 108, 26);
        pnInfoDV.add(btnThem);

        btnSua = new JButton("Sửa", editIcon);
        btnSua.setBounds(132, 143, 120, 26);
        pnInfoDV.add(btnSua);

        btnXoa = new JButton("Xóa", deleteIcon);
        btnXoa.setBounds(264, 143, 115, 26);
        pnInfoDV.add(btnXoa);

        btnLamLai = new JButton("Làm lại", refreshIcon);
        btnLamLai.setBounds(132, 185, 120, 26);
        pnInfoDV.add(btnLamLai);

        lbShowMessages = new JLabel("");
        lbShowMessages.setBounds(12, 115, 367, 16);
        lbShowMessages.setForeground(Color.RED);
        pnInfoDV.add(lbShowMessages);

        String[] cols = { "Mã DV", "Tên DV", "Đơn Giá" };
        modelTable = new DefaultTableModel(cols, 0);

        JPanel pnShowDV = new JPanel();
        pnShowDV.setBorder(
                new TitledBorder(null, "Danh sách dịch vụ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnShowDV.setBounds(402, 46, 580, 582);
        pnShowDV.setLayout(null);
        pnMain.add(pnShowDV);

        JLabel lbTimKiem = new JLabel("Tên dịch vụ:");
        lbTimKiem.setBounds(12, 23, 75, 20);
        pnShowDV.add(lbTimKiem);

        txtTim = new JTextField();
        txtTim.setBounds(85, 23, 225, 20);
        pnShowDV.add(txtTim);
        txtTim.setColumns(10);

        btnTim = new JButton("Tìm", searchIcon);
        btnTim.setBounds(322, 20, 90, 26);
        pnShowDV.add(btnTim);

        JPanel pnTableDV = new JPanel();
        pnTableDV.setBounds(12, 55, 556, 515);
        pnShowDV.add(pnTableDV);
        pnTableDV.setLayout(new BorderLayout(0, 0));

        table = new JTable(modelTable) {
            // khóa sửa dữ liệu trực tiếp trên table
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        JScrollPane scpTableDV = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnTableDV.add(scpTableDV, BorderLayout.CENTER);

        btnXemTatCa = new JButton("Xem tất cả");
        btnXemTatCa.setBounds(426, 20, 121, 26);
        pnShowDV.add(btnXemTatCa);

        // Sự kiện Action
        btnLamLai.addActionListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnTim.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        // sự kiện chuột
        table.addMouseListener(this);
        txtTenDV.addMouseListener(this);
        txtDonGia.addMouseListener(this);
        // sự kiện phím enter
        txtTenDV.addKeyListener(this);
        txtDonGia.addKeyListener(this);
        txtTim.addKeyListener(this);
        DocDuLieuVaoTable(dvDAO.getListDichVu());
        reSizeColumnTable();
    }

    public static void main(String[] args) {
        new QuanLyDichVu_UI().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLamLai)) {
            showMessage("", 2);
            txtMaDV.setText("");
            txtTenDV.setText("");
            txtDonGia.setText("0.0");
            lbShowMessages.setText("");
        } else if (o.equals(btnThem)) {
            showMessage("", 2);
            if (validData()) {
                DichVu dv = getDataInTable();
                try {
                    boolean result = dvDAO.insert(dv);
                    int maDV = dvDAO.getLatestID();
                    if (result == true) {
                        txtMaDV.setText(String.valueOf(maDV));
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        String donGia = df.format(dv.getDonGia());
                        modelTable.addRow(new Object[] { maDV, dv.getTenDV(), donGia });
                        showMessage("Thêm dịch vụ thành công", SUCCESS);
                        modelTable.fireTableDataChanged();
                    } else {
                        showMessage("Lỗi: Thêm dịch vụ thất bại", ERROR);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } else if (o.equals(btnSua)) {
            showMessage("", 2);
            if (validData()) {
                DichVu dv = getDataInTable();
                int row = table.getSelectedRow();
                try {
                    boolean result = dvDAO.update(dv);
                    if (result == true) {
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        String donGia = df.format(dv.getDonGia());
                        modelTable.setValueAt(dv.getTenDV(), row, 1);
                        modelTable.setValueAt(donGia, row, 2);
                        showMessage("Cập nhật dịch vụ thành công", SUCCESS);
                        modelTable.fireTableDataChanged();
                    } else {
                        showMessage("Lỗi: Cập nhật dịch vụ thất bại", ERROR);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else if (o.equals(btnXoa)) {
            showMessage("", 2);
            int row = table.getSelectedRow();
            try {
                if (row == -1) {
                    showMessage("Lỗi: Bạn cần chọn dòng cần xóa", ERROR);
                } else {
                    DichVu dv = getDataInTable();
                    int select = JOptionPane.NO_OPTION;
                    String tenDV = dv.getTenDV();
                    select = JOptionPane.showConfirmDialog(this, "<html>"
                            + "<p style='text-align: center; font-size: 18px; color:red'>Cảnh báo</p>"
                            + "<p style='text-align: center;'>Xóa dịch vụ " + "<span style='color: blue'> " + tenDV
                            + "</span>" + " sẽ dẫn đến xóa toàn bộ hóa đơn dịch vụ có liên quan.</p>"
                            + "<p style='text-align: left;'>Hãy suy nghĩ thật kỹ trước khi quyết định.</p>" + "</html>",
                            "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (select == JOptionPane.YES_OPTION) {
                        dvDAO.delete(dv);
                        modelTable.removeRow(row);
                        showMessage("Xóa thành công", SUCCESS);
                        modelTable.fireTableDataChanged();
                    }
                }
            } catch (Exception e3) {
                showMessage("Xóa thất bại", ERROR);
            }
        } else if (o.equals(btnTim)) {
            showMessage("", 2);
            if (validDataTim()) {
                String tenDV = txtTim.getText().trim();
                modelTable.getDataVector().removeAllElements();
                modelTable.fireTableDataChanged();
                ArrayList<DichVu> ds = dvDAO.getListDichVuByName(tenDV);
                if (ds.size() <= 0) {
                    showMessage("Không tìm thấy dịch vụ", ERROR);
                } else
                    DocDuLieuVaoTable(ds);
            }
        } else if (o.equals(btnXemTatCa)) {
            showMessage("", 2);
            modelTable.getDataVector().removeAllElements();
            modelTable.fireTableDataChanged();
            ArrayList<DichVu> ds = dvDAO.getListDichVu();
            if (ds.size() <= 0) {
                showMessage("Không tìm thấy dịch vụ", ERROR);
            } else
                DocDuLieuVaoTable(ds);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o.equals(table)) {
            int row = table.getSelectedRow();
            txtMaDV.setText(modelTable.getValueAt(row, 0).toString());
            txtTenDV.setText(modelTable.getValueAt(row, 1).toString());
            String donGia = modelTable.getValueAt(row, 2).toString().replace(",", "");
            txtDonGia.setText(donGia);
        } else if (o.equals(txtTenDV)) {
            txtTenDV.selectAll();
        } else if (o.equals(txtDonGia)) {
            txtDonGia.selectAll();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Object o = e.getSource();
        Object key = e.getKeyCode();
        // bắt sự kiện nhấn phím enter tự nhấn btnLogin
        if (o.equals(txtTenDV) || o.equals(txtDonGia)) {
            if (key.equals(KeyEvent.VK_ENTER)) {
                btnThem.doClick();
            }
        } else if (o.equals(txtTim)) {
            if (key.equals(KeyEvent.VK_ENTER)) {
                btnTim.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void showMessage(String message, JTextField txt) {
        lbShowMessages.setForeground(Color.RED);
        txt.requestFocus();
        txt.selectAll();
        lbShowMessages.setText(message);
        lbShowMessages.setIcon(errorIcon);
    }

    private void showMessage(String message, int type) {
        if (type == SUCCESS) {
            lbShowMessages.setForeground(Color.GREEN);
            lbShowMessages.setIcon(checkIcon);
        } else if (type == ERROR) {
            lbShowMessages.setForeground(Color.RED);
            lbShowMessages.setIcon(errorIcon);
        } else {
            lbShowMessages.setForeground(Color.BLACK);
            lbShowMessages.setIcon(null);
        }
        lbShowMessages.setText(message);
    }

    private boolean validData() {
        String tenDV = txtTenDV.getText().trim();
        String donGia = txtDonGia.getText().trim();
        if (!(tenDV.length() > 0)) {
            showMessage("Lỗi: Tên không được để trống", txtTenDV);
            return false;
        }
        if (donGia.length() > 0) {
            try {
                Double x = Double.parseDouble(donGia);
                if (x < 0) {
                    showMessage("Lỗi: Đơn giá >= 0", txtDonGia);
                    return false;
                }
            } catch (NumberFormatException ex) {
                showMessage("Lỗi: Đơn giá phải nhập số.", txtDonGia);
                return false;
            }
        }
        return true;
    }

    private boolean validDataTim() {
        String tenDV = txtTim.getText().trim();
        if (!(tenDV.length() > 0)) {
            showMessage("Lỗi: Tên không được để trống", txtTim);
            return false;
        }
        return true;
    }

    private DichVu getDataInTable() {
        String ma = txtMaDV.getText().trim().equals("") ? "0" : txtMaDV.getText().trim();
        int maDV = Integer.parseInt(ma);
        String tenDV = txtTenDV.getText().trim();
        Double donGia = Double.parseDouble(txtDonGia.getText().trim());

        DichVu dv = new DichVu(maDV, tenDV, donGia);
        return dv;
    }

    private void DocDuLieuVaoTable(ArrayList<DichVu> dataList) {
        if (dataList == null || dataList.size() <= 0)
            return;
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (DichVu item : dataList) {
            String donGia = df.format(item.getDonGia());
            modelTable.addRow(new Object[] { item.getMaDV(), item.getTenDV(), donGia });
        }
    }

    private void reSizeColumnTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(83);
        table.getColumnModel().getColumn(1).setPreferredWidth(350);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
    }
}
