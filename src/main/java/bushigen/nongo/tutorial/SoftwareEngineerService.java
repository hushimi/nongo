package bushigen.nongo.tutorial;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public SoftwareEngineerService (SoftwareEngineerRepository softwareEngineerRepository)
    {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers()
    {
        return softwareEngineerRepository.findAll();
    }

    public SoftwareEngineer getSoftwareEngineerById(Long id)
    {
        return softwareEngineerRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException(
                id + " not found"
            ));
    }

    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer)
    {
        softwareEngineerRepository.save(softwareEngineer);
    }

}
