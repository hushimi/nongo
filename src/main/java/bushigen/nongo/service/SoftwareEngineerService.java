package bushigen.nongo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import bushigen.nongo.dao.generated.SoftwareEngineerMapper;
import bushigen.nongo.model.SoftwareEngineer;
import bushigen.nongo.model.SoftwareEngineerExample;

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
        mapper.insertSelective(softwareEngineer);
    }

    /**
     * データ更新
     */
    public void updateSoftwareEngineer(SoftwareEngineer engineer)
    {
        mapper.updateByPrimaryKeySelective(engineer);
    }

    /**
     * 全件取得
     */
    public List<SoftwareEngineer> getAllSoftwareEngineers()
    {
        SoftwareEngineerExample example = new SoftwareEngineerExample();
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }

    /**
     * ID指定で取得
     */
    public SoftwareEngineer getSoftwareEngineerById(Long id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * ID指定で削除
     */
    public void deleteSoftwareEngineerById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
