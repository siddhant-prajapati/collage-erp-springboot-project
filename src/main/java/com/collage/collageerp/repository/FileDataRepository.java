package com.collage.collageerp.repository;

import com.collage.collageerp.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByName(String fileName);

    public Object deleteById(Long id);
    public FileData findById(Long id);
    public void deleteByName(String name);
    Optional<FileData> findByUserId(Integer userId);
}
