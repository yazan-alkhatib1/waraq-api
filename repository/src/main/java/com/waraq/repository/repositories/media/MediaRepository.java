package com.waraq.repository.repositories.media;


import com.waraq.repository.entities.media.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {


}
