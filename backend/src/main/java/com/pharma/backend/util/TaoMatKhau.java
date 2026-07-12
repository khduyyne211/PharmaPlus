package com.pharma.backend.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class TaoMatKhau {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder();

        String matKhauGoc = "123456";

        String matKhauDaMaHoa =
                passwordEncoder.encode(matKhauGoc);

        System.out.println("Mật khẩu gốc: " + matKhauGoc);
        System.out.println("Mật khẩu đã mã hóa: " + matKhauDaMaHoa);

        boolean ketQuaKiemTra = passwordEncoder.matches(
                matKhauGoc,
                matKhauDaMaHoa
        );

        System.out.println("Kiểm tra mật khẩu: " + ketQuaKiemTra);
    }
}
