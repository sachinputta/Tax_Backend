package com.example.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.ItemsList;

public interface ItemsListRepository extends JpaRepository<ItemsList, Long> {
}
