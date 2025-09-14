package reviewOOP2;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    static CRUD_SV crud = new CRUD_SV();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            menu();
            int chon = nhapSo("Chọn: ");
            switch (chon) {
                case 1 -> themSinhVien();
                case 2 -> xoaSinhVien();
                case 3 -> suaSinhVien();
                case 4 -> hienThiTatCa();
                case 5 -> hienThiTheoLop();
                case 6 -> hienThiTheoNganh();
                case 7 -> hienThiTheoThang();
                case 8 -> hienThiTheoDiem();
                case 0 -> {
                    System.out.println("Thoát chương trình.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void menu() {
        System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
        System.out.println("1. Thêm sinh viên");
        System.out.println("2. Xóa sinh viên");
        System.out.println("3. Sửa sinh viên");
        System.out.println("4. Hiển thị tất cả SV");
        System.out.println("5. Hiển thị SV theo lớp");
        System.out.println("6. Hiển thị SV theo ngành");
        System.out.println("7. Hiển thị SV theo tháng sinh");
        System.out.println("8. Hiển thị SV sắp xếp theo điểm TB");
        System.out.println("0. Thoát");
    }

    private static void themSinhVien() {
        SinhVien sv = nhapSinhVien();
        if (crud.addSinhVien(sv)) System.out.println("Thêm thành công!");
        else System.out.println("Thêm thất bại!");
    }

    private static void xoaSinhVien() {
        System.out.print("Nhập mã SV cần xóa: ");
        String ma = sc.nextLine();
        if (crud.deleteSinhVien(ma)) System.out.println("Xóa thành công!");
        else System.out.println("Không tìm thấy SV!");
    }

    private static void suaSinhVien() {
        System.out.print("Nhập mã SV cần sửa: ");
        String ma = sc.nextLine();
        SinhVien sv = nhapSinhVien(ma);
        if (crud.updateSinhVien(sv)) System.out.println("Sửa thành công!");
        else System.out.println("Không tìm thấy SV!");
    }

    private static void hienThiTatCa() {
        hienThiDanhSach(crud.getAllSinhVien());
    }

    private static void hienThiTheoLop() {
        System.out.print("Nhập lớp: ");
        hienThiDanhSach(crud.getSinhVienByLop(sc.nextLine()));
    }

    private static void hienThiTheoNganh() {
        System.out.print("Nhập ngành: ");
        hienThiDanhSach(crud.getSinhVienByNganh(sc.nextLine()));
    }

    private static void hienThiTheoThang() {
        int month = nhapSo("Nhập tháng sinh (1-12): ");
        hienThiDanhSach(crud.getSinhVienByMonth(month));
    }

    private static void hienThiTheoDiem() {
        hienThiDanhSach(crud.getSinhVienSortedByDiemTB());
    }

    private static SinhVien nhapSinhVien() {
        return nhapSinhVien(null);
    }

    private static SinhVien nhapSinhVien(String maCu) {
        while (true) {
            try {
                String maSV = (maCu != null) ? maCu : nhapChuoi("Nhập mã SV: ");
                String hoTen = nhapChuoi("Nhập họ tên: ");
                Date ngaySinh = Date.valueOf(nhapChuoi("Nhập ngày sinh (yyyy-MM-dd): "));
                String nganh = nhapChuoi("Nhập ngành: ");
                double diemTB = Double.parseDouble(nhapChuoi("Nhập điểm TB: "));
                String lop = nhapChuoi("Nhập lớp: ");

                return new SinhVien(maSV, hoTen, ngaySinh, nganh, diemTB, lop);
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                System.out.println("Vui lòng nhập lại!\n");
            }
        }
    }

    private static void hienThiDanhSach(List<SinhVien> ds) {
        if (ds.isEmpty()) {
            System.out.println("Không có dữ liệu!");
            return;
        }
        System.out.printf("%-12s %-20s %-12s %-6s %-6s %-8s\n",
                "Mã SV", "Họ Tên", "Ngày Sinh", "Ngành", "Điểm", "Lớp");
        for (SinhVien sv : ds) {
            System.out.printf("%-12s %-20s %-12s %-6s %-6.2f %-8s\n",
                    sv.getMaSV(), sv.getHoTen(), sv.getNgaySinh(),
                    sv.getNganh(), sv.getDiemTB(), sv.getLop());
        }
    }

    private static int nhapSo(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
            }
        }
    }

    private static String nhapChuoi(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}
