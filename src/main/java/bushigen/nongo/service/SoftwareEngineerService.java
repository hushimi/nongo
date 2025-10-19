package bushigen.nongo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import bushigen.nongo.dao.SoftwareEngineerMapper;
import bushigen.nongo.entity.SoftwareEngineer;
import bushigen.nongo.global.BusinessException;

/**
 * Service class for managing SoftwareEngineer entities.
 * This service provides business logic for CRUD operations on software engineers,
 * acting as an intermediary between the controller and repository layers.
 */
@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerMapper mapper;

    /**
     * Constructor
     */
    public SoftwareEngineerService (SoftwareEngineerMapper mapper)
    {
        this.mapper = mapper;
    }

    /**
     * 新規レコード作成
     */
    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer)
    {
        mapper.insert(softwareEngineer);
    }

    /**
     * データ更新
     */
    public void updateSoftwareEngineer(SoftwareEngineer engineer) {
        int updatedRows = mapper.update(engineer);

        // 更新件数が0の場合Exception
        if (updatedRows == 0) {
            throw new BusinessException("不正なリクエストです");
        }
    }

    /**
     * 全件取得
     */
    public List<SoftwareEngineer> getAllSoftwareEngineers()
    {
        return mapper.findAll();
    }

    /**
     * ID指定で取得
     */
    public SoftwareEngineer getSoftwareEngineerById(Long id)
    {
        SoftwareEngineer engineer = mapper.findById(id);
        if (engineer == null) {
            throw new IllegalStateException(id + " not found");
        }
        return engineer;
    }

    /**
     * ID指定で削除
     */
    public void deleteSoftwareEngineerById(Long id) {
        mapper.deleteById(id);
    }
}
