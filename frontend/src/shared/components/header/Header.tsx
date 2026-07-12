import "./Header.css";
import logoPharma from "../../../assets/image/logo/pharma+.png";
import { Link } from "react-router-dom";
import NutGioHang from "../../../features/gio-hang/components/NutGioHang";
import NutTaiKhoan from "../../../features/xac-thuc/components/NutTaiKhoan";
function Header() {
  return (
    <header className="customer-header">
      <div className="header-top">

        <div className="header-top-left">
          <span>Tư vấn ngay</span>
          <strong>0809.111.222</strong>
        </div>

        <div className="header-top-right">
          <span>Hệ thống nhà thuốc Pharma+</span>
        </div>

      </div>

      <div className="header-main">
        <Link to="/" className="header-logo-image-wrap">
          <img
            className="header-logo-image"
            src={logoPharma}
            alt="Pharma+"
          />
        </Link>

        <div className="header-search">
          <input
            type="text"
            placeholder="Tìm tên thuốc, bệnh lý, thực phẩm chức năng..."
          />

          <button>Tìm kiếm</button>
        </div>

        <div className="header-actions">
          <NutTaiKhoan />
          <NutGioHang />
        </div>
      </div>
    </header>
  );
}

export default Header;