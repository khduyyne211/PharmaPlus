import { useNavigate } from "react-router-dom";
import { useDanhMucSanPham } from "../../danh-muc-san-pham/hooks/useDanhMucSanPham";
import type { DanhMucSanPham } from "../../danh-muc-san-pham/types/DanhMucSanPham";
import { taoSlug } from "../../../shared/utils/taoSlug";
import "../styles/TrangChu.css";

interface CauHinhDanhMucNoiBat {
  tenHienThi: string;
  danhSachTenTimKiem: string[];
  soLuongSanPham: number;
  icon: string;
  classMauIcon: string;
}

const danhSachCauHinhDanhMucNoiBat: CauHinhDanhMucNoiBat[] = [
  {
    tenHienThi: "Thần kinh não",
    danhSachTenTimKiem: [
      "Thần kinh não",
      "Thần kinh",
      "Thuốc hệ thần kinh",
    ],
    soLuongSanPham: 44,
    icon: "bi bi-activity",
    classMauIcon: "mau-icon-1",
  },
  {
    tenHienThi: "Vitamin & Khoáng chất",
    danhSachTenTimKiem: [
      "Vitamin & Khoáng chất",
      "Vitamin và Khoáng chất",
      "Thuốc bổ & vitamin",
    ],
    soLuongSanPham: 79,
    icon: "bi bi-capsule",
    classMauIcon: "mau-icon-2",
  },
  {
    tenHienThi: "Sinh lý - Nội tiết tố",
    danhSachTenTimKiem: [
      "Sinh lý - Nội tiết tố",
      "Sinh lý – Nội tiết tố",
      "Sinh lý",
      "Nội tiết tố",
    ],
    soLuongSanPham: 34,
    icon: "bi bi-gender-ambiguous",
    classMauIcon: "mau-icon-3",
  },
  {
    tenHienThi: "Tim mạch - Huyết áp",
    danhSachTenTimKiem: [
      "Tim mạch - Huyết áp",
      "Tim mạch – Huyết áp",
      "Tim mạch",
      "Thuốc tim mạch & máu",
    ],
    soLuongSanPham: 17,
    icon: "bi bi-heart-pulse-fill",
    classMauIcon: "mau-icon-4",
  },
  {
    tenHienThi: "Miễn dịch - Đề kháng",
    danhSachTenTimKiem: [
      "Miễn dịch - Đề kháng",
      "Miễn dịch – Đề kháng",
      "Miễn dịch",
    ],
    soLuongSanPham: 38,
    icon: "bi bi-shield-check",
    classMauIcon: "mau-icon-5",
  },
  {
    tenHienThi: "Tiêu hóa",
    danhSachTenTimKiem: [
      "Tiêu hóa",
      "Tiêu hoá",
      "Thuốc tiêu hóa & gan mật",
      "Thuốc tiêu hoá & gan mật",
    ],
    soLuongSanPham: 58,
    icon: "bi bi-bag-heart-fill",
    classMauIcon: "mau-icon-6",
  },
  {
    tenHienThi: "Giải pháp làn da",
    danhSachTenTimKiem: [
      "Giải pháp làn da",
      "Chăm sóc da",
      "Thuốc da liễu",
    ],
    soLuongSanPham: 75,
    icon: "bi bi-bandaid-fill",
    classMauIcon: "mau-icon-7",
  },
  {
    tenHienThi: "Chăm sóc da mặt",
    danhSachTenTimKiem: ["Chăm sóc da mặt"],
    soLuongSanPham: 172,
    icon: "bi bi-person-circle",
    classMauIcon: "mau-icon-8",
  },
  {
    tenHienThi: "Hỗ trợ làm đẹp",
    danhSachTenTimKiem: ["Hỗ trợ làm đẹp"],
    soLuongSanPham: 15,
    icon: "bi bi-stars",
    classMauIcon: "mau-icon-9",
  },
  {
    tenHienThi: "Hỗ trợ tình dục",
    danhSachTenTimKiem: [
      "Hỗ trợ tình dục",
      "Sức khỏe sinh sản",
      "Sinh lý nam nữ",
    ],
    soLuongSanPham: 52,
    icon: "bi bi-gender-female",
    classMauIcon: "mau-icon-10",
  },
  {
    tenHienThi: "Sữa",
    danhSachTenTimKiem: ["Sữa"],
    soLuongSanPham: 40,
    icon: "bi bi-cup-straw",
    classMauIcon: "mau-icon-11",
  },
  {
    tenHienThi: "Dụng cụ theo dõi",
    danhSachTenTimKiem: [
      "Dụng cụ theo dõi",
      "Thiết bị y tế",
      "Trang thiết bị y tế",
    ],
    soLuongSanPham: 99,
    icon: "bi bi-clipboard2-pulse-fill",
    classMauIcon: "mau-icon-12",
  },
];

function chuanHoaTenDanhMuc(tenDanhMuc: string) {
  return tenDanhMuc
    .normalize("NFD")
    .replace(/[\u0300-\u036f]/g, "")
    .replace(/[đĐ]/g, "d")
    .replace(/[–—]/g, "-")
    .replace(/&/g, "va")
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, " ")
    .trim();
}

function lamPhangDanhSachDanhMuc(
  danhSachDanhMuc: DanhMucSanPham[]
): DanhMucSanPham[] {
  const ketQua: DanhMucSanPham[] = [];

  const themDanhMuc = (danhSach: DanhMucSanPham[]) => {
    for (const danhMuc of danhSach) {
      ketQua.push(danhMuc);

      if (danhMuc.danhSachDanhMucCon?.length > 0) {
        themDanhMuc(danhMuc.danhSachDanhMucCon);
      }
    }
  };

  themDanhMuc(danhSachDanhMuc);

  return ketQua;
}

function timDanhMucPhuHop(
  tatCaDanhMuc: DanhMucSanPham[],
  danhSachTenTimKiem: string[]
): DanhMucSanPham | undefined {
  const danhSachTenDaChuanHoa = danhSachTenTimKiem.map((tenDanhMuc) =>
    chuanHoaTenDanhMuc(tenDanhMuc)
  );

  return tatCaDanhMuc.find((danhMuc) =>
    danhSachTenDaChuanHoa.includes(
      chuanHoaTenDanhMuc(danhMuc.tenDanhMuc)
    )
  );
}

function DanhMucNoiBat() {
  const navigate = useNavigate();
  const { danhSachDanhMuc } = useDanhMucSanPham();

  const tatCaDanhMuc = lamPhangDanhSachDanhMuc(danhSachDanhMuc);

  const chuyenDenDanhMuc = (
    cauHinhDanhMuc: CauHinhDanhMucNoiBat
  ) => {
    const danhMucPhuHop = timDanhMucPhuHop(
      tatCaDanhMuc,
      cauHinhDanhMuc.danhSachTenTimKiem
    );

    if (!danhMucPhuHop) {
      navigate("/san-pham");
      return;
    }

    navigate(
      `/danh-muc/${danhMucPhuHop.maDanhMuc}/${taoSlug(
        danhMucPhuHop.tenDanhMuc
      )}`
    );
  };

  return (
    <section className="danh-muc-noi-bat">
      <div className="danh-muc-noi-bat-tieu-de">
        <i className="bi bi-trophy-fill"></i>
        <h2>Danh mục nổi bật</h2>
      </div>

      <div className="danh-muc-noi-bat-danh-sach">
        {danhSachCauHinhDanhMucNoiBat.map((danhMuc) => (
          <button
            key={danhMuc.tenHienThi}
            type="button"
            className="danh-muc-noi-bat-the"
            onClick={() => chuyenDenDanhMuc(danhMuc)}
          >
            <div
              className={`danh-muc-noi-bat-icon ${danhMuc.classMauIcon}`}
            >
              <i className={danhMuc.icon}></i>
            </div>

            <strong>{danhMuc.tenHienThi}</strong>

            <span>{danhMuc.soLuongSanPham} sản phẩm</span>
          </button>
        ))}
      </div>
    </section>
  );
}

export default DanhMucNoiBat;