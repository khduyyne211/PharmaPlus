import { Outlet } from "react-router-dom";
import Header from "../../shared/components/header/Header";
import Footer from "../../shared/components/footer/Footer";
import ThanhDanhMucSanPham from "../../features/danh-muc-san-pham/components/ThanhDanhMucSanPham";
import "./CustomerLayout.css";

function CustomerLayout() {
  return (
    <div className="customer-layout">
      <Header />

      <ThanhDanhMucSanPham />

      <main className="customer-main">
        <Outlet />
      </main>

      <Footer />
    </div>
  );
}

export default CustomerLayout;