package bushigen.nongo.tutorial;

import java.util.List;

import org.springframework.stereotype.Service;

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
