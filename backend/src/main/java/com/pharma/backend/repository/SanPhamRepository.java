package com.pharma.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pharma.backend.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Long>, JpaSpecificationExecutor<SanPham> {
    @Override
    @EntityGraph(attributePaths = {
            "nhaSanXuat",
            "danhMucSanPham"
    })
    Page<SanPham> findAll(Specification<SanPham> specification, Pageable pageable);
}