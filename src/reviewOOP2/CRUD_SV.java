package reviewOOP2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_SV {
    private final String url = "jdbc:mysql://localhost:3306/dssinhvien";
    private final String user = "root"; 
    private final String password = "1234"; 

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private SinhVien mapResultSetToSinhVien(ResultSet rs) throws SQLException {
        return new SinhVien(
            rs.getString("maSV"),
            rs.getString("hoTen"),
            rs.getDate("ngaySinh"),
            rs.getString("nganh"),
            rs.getDouble("diemTB"),
            rs.getString("lop")
        );
    }

    // Thêm sinh viên
    public boolean addSinhVien(SinhVien sv) {
        String sql = "INSERT INTO SinhVien(maSV, hoTen, ngaySinh, nganh, diemTB, lop) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());
            ps.setDate(3, sv.getNgaySinh());
            ps.setString(4, sv.getNganh());
            ps.setDouble(5, sv.getDiemTB());
            ps.setString(6, sv.getLop());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sinh viên
    public boolean deleteSinhVien(String maSV) {
        String sql = "DELETE FROM SinhVien WHERE maSV=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa sinh viên
    public boolean updateSinhVien(SinhVien sv) {
        String sql = "UPDATE SinhVien SET hoTen=?, ngaySinh=?, nganh=?, diemTB=?, lop=? WHERE maSV=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getHoTen());
            ps.setDate(2, sv.getNgaySinh());
            ps.setString(3, sv.getNganh());
            ps.setDouble(4, sv.getDiemTB());
            ps.setString(5, sv.getLop());
            ps.setString(6, sv.getMaSV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả sinh viên
    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien";
        try (Connection conn = getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sinh viên theo lớp
    public List<SinhVien> getSinhVienByLop(String lop) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien WHERE lop=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sinh viên theo ngành
    public List<SinhVien> getSinhVienByNganh(String nganh) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien WHERE nganh=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nganh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sinh viên theo tháng sinh
    public List<SinhVien> getSinhVienByMonth(int month) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien WHERE MONTH(ngaySinh)=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sinh viên sắp xếp theo điểm trung bình
    public List<SinhVien> getSinhVienSortedByDiemTB() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien ORDER BY diemTB DESC";
        try (Connection conn = getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(mapResultSetToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
