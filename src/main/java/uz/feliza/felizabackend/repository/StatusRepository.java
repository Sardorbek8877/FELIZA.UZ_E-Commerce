package uz.feliza.felizabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.feliza.felizabackend.entity.Status;
import uz.feliza.felizabackend.projection.CustomStatus;

@RepositoryRestResource(path = "status", collectionResourceRel = "StatusList", excerptProjection = CustomStatus.class)
public interface StatusRepository extends JpaRepository<Status, Long> {

}
