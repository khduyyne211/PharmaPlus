import { Link } from "react-router-dom";
import DanhSachSanPhamXacNhan from "../features/xac-nhan-dat-hang/components/DanhSachSanPhamXacNhan";
import DiaChiNhanHangXacNhan from "../features/xac-nhan-dat-hang/components/DiaChiNhanHangXacNhan";
import PhuongThucThanhToan from "../features/xac-nhan-dat-hang/components/PhuongThucThanhToan";
import TongKetXacNhanDatHang from "../features/xac-nhan-dat-hang/components/TongKetXacNhanDatHang";
import { useXacNhanDatHang } from "../features/xac-nhan-dat-hang/hooks/useXacNhanDatHang";
import "../features/xac-nhan-dat-hang/styles/XacNhanDatHang.css";

function XacNhanDatHangPage() {
  const xacNhanDatHang = useXacNhanDatHang();

  const hoanTatMuaHang = () => {
    // Tạm thời chưa gọi API tạo đơn hàng.
    window.alert(
      "Chức năng tạo đơn hàng sẽ được thực hiện sau khi chốt nghiệp vụ tồn kho."
    );
  };

  if (xacNhanDatHang.dangTaiDuLieu) {
    return (
      <main className="xac-nhan-trang">
        <div className="page-container">
          <div className="xac-nhan-trang-thong-bao">
            Đang tải thông tin đặt hàng...
          </div>
        </div>
      </main>
    );
  }

  if (xacNhanDatHang.thongBaoLoi) {
    return (
      <main className="xac-nhan-trang">
        <div className="page-container">
          <div className="xac-nhan-trang-thong-bao xac-nhan-trang-loi">
            {xacNhanDatHang.thongBaoLoi}
          </div>
        </div>
      </main>
    );
  }

  if (
    !xacNhanDatHang.gioHang ||
    xacNhanDatHang.gioHangRong
  ) {
    return (
      <main className="xac-nhan-trang">
        <div className="page-container">
          <div className="xac-nhan-gio-hang-rong">
            <i className="bi bi-cart-x"></i>

            <h1>Giỏ hàng đang trống</h1>

            <p>
              Vui lòng thêm sản phẩm trước khi tiến hành đặt hàng.
            </p>

            <Link to="/san-pham">
              Tiếp tục mua sắm
            </Link>
          </div>
        </div>
      </main>
    );
  }

  return (
    <main className="xac-nhan-trang">
      <div className="page-container">
        <Link
            to="/gio-hang"
            className="xac-nhan-quay-lai-gio-hang"
            >
            ← Quay lại giỏ hàng
        </Link>

        <div className="xac-nhan-bo-cuc">
          <div className="xac-nhan-ben-trai">
            <DanhSachSanPhamXacNhan
              danhSachChiTietGioHang={
                xacNhanDatHang.gioHang
                  .danhSachChiTietGioHang
              }
            />

            <DiaChiNhanHangXacNhan
              danhSachDiaChi={
                xacNhanDatHang.danhSachDiaChi
              }
              diaChiDangChon={
                xacNhanDatHang.diaChiDangChon
              }
              dangMoDanhSachDiaChi={
                xacNhanDatHang.dangMoDanhSachDiaChi
              }
              ghiChu={xacNhanDatHang.ghiChu}
              moDanhSachDiaChi={() =>
                xacNhanDatHang.setDangMoDanhSachDiaChi(
                  true
                )
              }
              dongDanhSachDiaChi={() =>
                xacNhanDatHang.setDangMoDanhSachDiaChi(
                  false
                )
              }
              chonDiaChi={
                xacNhanDatHang.chonDiaChi
              }
              thayDoiGhiChu={xacNhanDatHang.setGhiChu}
            />

            <PhuongThucThanhToan
              phuongThucDangChon={
                xacNhanDatHang.phuongThucThanhToan
              }
              thayDoiPhuongThuc={
                xacNhanDatHang.setPhuongThucThanhToan
              }
            />
          </div>

          <TongKetXacNhanDatHang
            tongTienHang={
              xacNhanDatHang.gioHang.tongTien
            }
            coTheHoanTat={
              xacNhanDatHang.coTheHoanTat
            }
            hoanTatMuaHang={hoanTatMuaHang}
          />
        </div>
      </div>
    </main>
  );
}

export default XacNhanDatHangPage;