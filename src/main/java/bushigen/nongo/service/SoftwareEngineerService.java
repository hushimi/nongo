package bushigen.nongo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import bushigen.nongo.dao.SoftwareEngineerRepository;
import bushigen.nongo.entity.SoftwareEngineer;

/**
 * Service class for managing SoftwareEngineer entities.
 * This service provides business logic for CRUD operations on software engineers,
 * acting as an intermediary between the controller and repository layers.
 */
@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository softwareEngineerRepository;

    /**
     * Constructor
     */
    public SoftwareEngineerService (SoftwareEngineerRepository softwareEngineerRepository)
    {
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    /**
     * 全件取得
     */
    public List<SoftwareEngineer> getAllSoftwareEngineers()
    {
        return softwareEngineerRepository.findAll();
    }

    /**
     * ID指定で取得
     */
    public SoftwareEngineer getSoftwareEngineerById(Long id)
    {
        SoftwareEngineer engineer = softwareEngineerRepository.findById(id);
        if (engineer == null) {
            throw new IllegalStateException(id + " not found");
        }
        return engineer;
    }

    /**
     * 新規レコード作成
     */
    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer)
    {
        softwareEngineerRepository.insert(softwareEngineer);
    }

    /**
     * データ更新
     */
    public void updateSoftwareEngineer(SoftwareEngineer engineer) {
        softwareEngineerRepository.update(engineer);
    }

    /**
     * ID指定で削除
     */
    public void deleteSoftwareEngineerById(Long id) {
        softwareEngineerRepository.deleteById(id);
    }
}
