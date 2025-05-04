package com.example.main.repository;

import com.example.main.entity.ProformaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProformaItemRepository extends JpaRepository<ProformaItem, String> {
}
