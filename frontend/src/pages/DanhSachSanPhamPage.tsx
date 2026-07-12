import { useParams } from "react-router-dom";

import BoLocSanPham from "../features/san-pham/components/BoLocSanPham";
import ThanhSapXepSanPham from "../features/san-pham/components/ThanhSapXepSanPham";
import ThanhBoLocDangChon from "../features/san-pham/components/ThanhBoLocDangChon";
import BangSanPham from "../features/san-pham/components/BangSanPham";
import NutXemThem from "../features/san-pham/components/NutXemThem";
import ThongBaoKhongCoSanPham from "../features/san-pham/components/ThongBaoKhongCoSanPham";
import { useSanPham } from "../features/san-pham/hooks/useSanPham";
import KhuDieuHuongDanhMuc from "../features/danh-muc-san-pham/components/KhuDieuHuongDanhMuc";
import "../features/san-pham/styles/SanPham.css";

function SanPhamPage() {
  const { maDanhMuc } = useParams();

  const maDanhMucDangChon = maDanhMuc ? Number(maDanhMuc) : undefined;

  const sanPham = useSanPham({
    maDanhMuc: maDanhMucDangChon,
  });

  return (
    <div className="page-container trang-san-pham">
      <KhuDieuHuongDanhMuc maDanhMucDangChon={maDanhMucDangChon} />

      <div className="khung-san-pham">
        <BoLocSanPham
          boLocDuoi100={sanPham.boLocDuoi100}
          boLocTu100Den300={sanPham.boLocTu100Den300}
          boLocTu300Den500={sanPham.boLocTu300Den500}
          boLocTren500={sanPham.boLocTren500}
          chonNhaSanXuat={sanPham.chonNhaSanXuat}
        />

        <div className="khu-vuc-san-pham">
          <div className="dau-trang-san-pham">
            <div>
              <h2 className="tieu-de-san-pham">Danh sách sản phẩm</h2>
            </div>

            <ThanhSapXepSanPham
              sapXep={sanPham.sapXep}
              setSapXep={sanPham.setSapXep}
            />
          </div>

          <p className="ghi-chu-san-pham">
            Lưu ý: Thuốc kê đơn và một số sản phẩm sẽ cần tư vấn từ dược sĩ
          </p>

          <ThanhBoLocDangChon
            danhSachBoLocDangChon={sanPham.danhSachBoLocDangChon}
            xoaTatCaBoLoc={sanPham.xoaTatCaBoLoc}
          />

          {sanPham.dangTaiDuLieu && (
            <div className="dang-tai-san-pham">Đang tải sản phẩm...</div>
          )}

          {sanPham.khongCoSanPham && <ThongBaoKhongCoSanPham />}

          {!sanPham.dangTaiDuLieu && !sanPham.khongCoSanPham && (
            <>
              <BangSanPham danhSachSanPham={sanPham.danhSachDangHienThi} />

              <p className="san-pham-so-luong-hien-thi">
                Đang hiển thị {sanPham.danhSachDangHienThi.length} /{" "}
                {sanPham.tongSoPhanTu} sản phẩm
              </p>

              <NutXemThem
                conSanPhamDeXemThem={sanPham.conSanPhamDeXemThem}
                xemThemSanPham={sanPham.xemThemSanPham}
                dangTaiThem={sanPham.dangTaiThem}
              />
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default SanPhamPage;