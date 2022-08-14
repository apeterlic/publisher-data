package com.publishers.repository;

import com.publishers.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Represents repository interface for communication with database.
 *
 * @author Ana Peterlic
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Finds publisher by given code.
     *
     * @param code :: the code
     * @return result :: optional of {@link Publisher}
     */
    Optional<Publisher> findByCode(String code);

    /**
     * Updates publisher's file by given code.
     *
     * @param code :: the code
     * @param file :: the name of the file
     */
    @Transactional
    @Modifying
    @Query("UPDATE publisher SET file = :file WHERE code = :code")
    void updateFile(@Param("code") String code, @Param("file") String file);
}
