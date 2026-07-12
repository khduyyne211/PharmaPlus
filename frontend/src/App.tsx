import "./App.css";

import {
  BrowserRouter,
  Outlet,
  Route,
  Routes,
} from "react-router-dom";

import CustomerLayout from "./layouts/customer/CustomerLayout";

import TrangChuPage from "./pages/TrangChuPage";
import DanhSachSanPhamPage from "./pages/DanhSachSanPhamPage";
import ChiTietSanPhamPage from "./pages/ChiTietSanPhamPage";
import GioHangPage from "./pages/GioHangPage";
import ThongTinCaNhanPage from "./pages/ThongTinCaNhanPage";
import SoDiaChiNhanHangPage from "./pages/SoDiaChiNhanHangPage";
import XacNhanDatHangPage from "./pages/XacNhanDatHangPage";

import BatBuocDangNhap from "./features/xac-thuc/components/BatBuocDangNhap";
import KhungTaiKhoan from "./features/tai-khoan/components/KhungTaiKhoan";

function App() {
  return (
    <div className="app-container">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<CustomerLayout />}>
            {/* Các trang công khai */}
            <Route index element={<TrangChuPage />} />

            <Route
              path="san-pham"
              element={<DanhSachSanPhamPage />}
            />

            <Route
              path="danh-muc/:maDanhMuc/:slug"
              element={<DanhSachSanPhamPage />}
            />

            <Route
              path="san-pham/:maSanPham/:slug"
              element={<ChiTietSanPhamPage />}
            />

            {/* Các trang bắt buộc đăng nhập */}
            <Route
              element={
                <BatBuocDangNhap>
                  <Outlet />
                </BatBuocDangNhap>
              }
            >
              <Route
                path="gio-hang"
                element={<GioHangPage />}
              />

              <Route
                path="xac-nhan-dat-hang"
                element={<XacNhanDatHangPage />}
              />

              <Route
                path="tai-khoan"
                element={<KhungTaiKhoan />}
              >
                <Route
                  index
                  element={<ThongTinCaNhanPage />}
                />

                <Route
                  path="so-dia-chi"
                  element={<SoDiaChiNhanHangPage />}
                />
              </Route>
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;