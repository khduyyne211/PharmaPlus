import { useEffect, useState } from "react";
import {layDanhMucMenuApi} from "../api/DanhMucSanPhamApi";
import type { DanhMucSanPham } from "../types/DanhMucSanPham";

export function useDanhMucSanPham(){
    const [danhSachDanhMuc, setDanhSachDanhMuc] = useState<DanhMucSanPham[]>([]);

    useEffect(() => {
        layDanhMucMenuApi().then((response) => {
            setDanhSachDanhMuc(response.data);
        });
    }, []);

    return{
        danhSachDanhMuc,
    };
}