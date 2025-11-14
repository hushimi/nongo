package bushigen.nongo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bushigen.nongo.dao.generated.SoftwareEngineerMapper;
import bushigen.nongo.model.SoftwareEngineer;
import bushigen.nongo.model.SoftwareEngineerExample;

/**
 * SoftwareEngineerエンティティを管理するサービスクラス
 * ソフトウェアエンジニアのCRUD操作のビジネスロジックを提供し、
 * コントローラーとリポジトリ層の間の仲介役を果たします
 */
@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerMapper mapper;

    /**
     * コンストラクタ
     */
    public SoftwareEngineerService (SoftwareEngineerMapper mapper)
    {
        this.mapper = mapper;
    }

    /**
     * 新規レコード作成
     */
    @Transactional
    public void insertSoftwareEngineer(SoftwareEngineer softwareEngineer)
    {
        mapper.insertSelective(softwareEngineer);
    }

    /**
     * データ更新
     */
    @Transactional
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
    @Transactional
    public void deleteSoftwareEngineerById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
