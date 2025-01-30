package br.com.microservices.microservices.sendemail.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.sendemail.models.ViewerModel;

@Repository
public interface ViewerRepository extends JpaRepository<ViewerModel, Long> {

    @Query(value = "SELECT COUNT(v) FROM Viewer v", nativeQuery = true)
    Long countViewerAccess();
}
