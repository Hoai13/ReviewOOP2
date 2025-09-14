package reviewOOP2;

import java.sql.Date;
import java.time.LocalDate;

public class SinhVien {
    private String maSV;
    private String hoTen;
    private java.sql.Date ngaySinh;
    private String nganh;
    private double diemTB;
    private String lop;

   
    public SinhVien(String maSV, String hoTen, Date ngaySinh, String nganh, double diemTB, String lop) {
        setMaSV(maSV);
        setHoTen(hoTen);
        setNgaySinh(ngaySinh);
        setNganh(nganh);
        setDiemTB(diemTB);
        setLop(lop);
    }

    // Getter
    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public Date getNgaySinh() { return ngaySinh; }
    public String getNganh() { return nganh; }
    public double getDiemTB() { return diemTB; }
    public String getLop() { return lop; }
    
    public void setMaSV(String maSV) {
        if (maSV.matches("^455105\\d{4}$") || maSV.matches("^455109\\d{4}$")) {
            this.maSV = maSV;

            // Kiểm tra tính khớp nếu ngành đã có
            if (this.nganh != null) {
                if (maSV.startsWith("455105") && !this.nganh.equals("CNTT")) {
                    throw new IllegalArgumentException("Mã SV 455105 phải thuộc ngành CNTT");
                }
                if (maSV.startsWith("455109") && !this.nganh.equals("KTPM")) {
                    throw new IllegalArgumentException("Mã SV 455109 phải thuộc ngành KTPM");
                }
            }
        } else {
            throw new IllegalArgumentException("Mã sinh viên phải có dạng 455105xxxx hoặc 455109xxxx (10 chữ số)");
        }
    }


    public void setHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        this.hoTen = chuanHoaTen(hoTen);
    }

    public void setNgaySinh(Date ngaySinh) {
        if (ngaySinh == null) {
            throw new IllegalArgumentException("Ngày sinh không được rỗng");
        }
        int namSinh = ngaySinh.toLocalDate().getYear();
        int yearNow = LocalDate.now().getYear();
        if (namSinh < 1900 || namSinh > yearNow) {
            throw new IllegalArgumentException("Năm sinh không hợp lệ");
        }
        this.ngaySinh = ngaySinh;
    }
    
    public void setNganh(String nganh) {
        if (nganh == null) {
            throw new IllegalArgumentException("Ngành không được để trống");
        }

        // Chuẩn hóa dữ liệu thành chữ in hoa
        String n = nganh.trim().toUpperCase();

        if (!n.equals("CNTT") && !n.equals("KTPM")) {
            throw new IllegalArgumentException("Ngành chỉ được CNTT hoặc KTPM");
        }

        // Nếu đã có mã SV thì phải kiểm tra khớp
        if (this.maSV != null) {
            if (this.maSV.startsWith("455105") && !n.equals("CNTT")) {
                throw new IllegalArgumentException("Mã SV " + this.maSV + " chỉ hợp lệ với ngành CNTT");
            }
            if (this.maSV.startsWith("455109") && !n.equals("KTPM")) {
                throw new IllegalArgumentException("Mã SV " + this.maSV + " chỉ hợp lệ với ngành KTPM");
            }
        }

        this.nganh = n;
    }



    public void setDiemTB(double diemTB) {
        if (diemTB >= 0.0 && diemTB <= 10.0) {
            this.diemTB = diemTB;
        } else {
            throw new IllegalArgumentException("Điểm trung bình phải trong đoạn [0.0, 10.0]");
        }
    }

    public void setLop(String lop) {
        if (lop == null || lop.trim().isEmpty()) {
            throw new IllegalArgumentException("Lớp không được để trống");
        }
        this.lop = lop;
    }
    
    private String chuanHoaTen(String ten) {
        ten = ten.trim().toLowerCase();
        String[] arr = ten.split("\\s+"); // Tách thành các từ
        // Viết hoa chữ cái đầu của từng từ
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1);
        }
        // Ghép lại thành 1 chuỗi
        return String.join(" ", arr);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %.1f | %s",
                maSV, hoTen, ngaySinh, nganh, diemTB, lop);
    }
}
