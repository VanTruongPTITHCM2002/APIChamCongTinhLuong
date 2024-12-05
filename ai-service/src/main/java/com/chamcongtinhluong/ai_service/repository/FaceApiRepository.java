package com.chamcongtinhluong.ai_service.repository;

import com.chamcongtinhluong.ai_service.entity.FaceApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceApiRepository extends JpaRepository<FaceApi,Integer> {
    FaceApi findByIdEmployee(String idEmployee);
}
