import { Link } from "react-router-dom";
import DongChiTietGioHang from "../features/gio-hang/components/DongChiTietGioHang";
import { useGioHang } from "../features/gio-hang/hooks/useGioHang";
import "../features/gio-hang/styles/GioHang.css";

function GioHangPage() {
  const {
    gioHang,
    dangTaiDuLieu,
    dangXuLy,
    capNhatChiTietGioHang,
    xoaSanPhamKhoiGioHang,
    xoaTatCaSanPhamTrongGioHang,
  } = useGioHang();

  const dinhDangTien = (giaTri: number) => {
    return giaTri.toLocaleString("vi-VN") + "đ";
  };

  if (dangTaiDuLieu) {
    return (
      <div className="page-container trang-gio-hang">
        <p className="gio-hang-dang-tai">Đang tải giỏ hàng...</p>
      </div>
    );
  }

  if (!gioHang || gioHang.danhSachChiTietGioHang.length === 0) {
    return (
      <div className="page-container trang-gio-hang">
        <Link to="/" className="gio-hang-tiep-tuc-mua-sam">
          ← Tiếp tục mua sắm
        </Link>

        <div className="gio-hang-rong">
          <div className="gio-hang-rong-bieu-tuong">
            <i className="bi bi-cart-x"></i>
          </div>

          <h2>Chưa có sản phẩm nào trong giỏ</h2>

          <p>
            Cùng khám phá hàng ngàn sản phẩm
            <br />
            tại Pharma+ nhé!
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="page-container trang-gio-hang">
      <Link to="/" className="gio-hang-tiep-tuc-mua-sam">
        ← Tiếp tục mua sắm
      </Link>

      <div className="gio-hang-bo-cuc">
        <div className="gio-hang-ben-trai">

          <div className="gio-hang-bang">
            <div className="gio-hang-bang-tieu-de">
              <div></div>
              <div className="gio-hang-cot-tieu-de">Giá thành</div>
              <div className="gio-hang-cot-tieu-de">Số lượng</div>
              <div className="gio-hang-cot-tieu-de">Đơn vị</div>
              <div></div>
            </div>

            <div className="gio-hang-bang-noi-dung">
              {gioHang.danhSachChiTietGioHang.map((chiTiet) => (
                <DongChiTietGioHang
                  key={chiTiet.maChiTietGioHang}
                  chiTiet={chiTiet}
                  dangXuLy={dangXuLy}
                  capNhatChiTietGioHang={capNhatChiTietGioHang}
                  xoaSanPhamKhoiGioHang={xoaSanPhamKhoiGioHang}
                />
              ))}
            </div>
          </div>

          <div className="gio-hang-khu-vuc-xoa">
            <button
              className="gio-hang-nut-xoa-tat-ca"
              onClick={xoaTatCaSanPhamTrongGioHang}
              disabled={dangXuLy}
            >
              Xóa tất cả sản phẩm
            </button>
          </div>
        </div>

        <div className="gio-hang-ben-phai">
          <div className="gio-hang-tom-tat">
            <h2>Thông tin đơn hàng</h2>

            <div className="gio-hang-tom-tat-dong">
              <span>Tổng tiền</span>
              <strong>{dinhDangTien(gioHang.tongTien)}</strong>
            </div>

            <div className="gio-hang-tom-tat-dong">
              <span>Giảm giá trực tiếp</span>
              <strong>0đ</strong>
            </div>

            <div className="gio-hang-tom-tat-dong">
              <span>Giảm giá voucher</span>
              <strong>0đ</strong>
            </div>

            <div className="gio-hang-tom-tat-dong">
              <span>Tiết kiệm được</span>
              <strong>0đ</strong>
            </div>

            <div className="gio-hang-tom-tat-dong gio-hang-thanh-tien">
              <span>Thành tiền</span>
              <strong>{dinhDangTien(gioHang.tongTien)}</strong>
            </div>

            <Link
              to="/xac-nhan-dat-hang"
              className="gio-hang-nut-mua-hang"
            >
              Mua hàng
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default GioHangPage;