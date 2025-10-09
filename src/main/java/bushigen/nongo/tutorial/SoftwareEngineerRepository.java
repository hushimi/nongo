package bushigen.nongo.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Long> {

    // No custom update needed—JpaRepository.save(engineer) will update if the ID exists.
}
