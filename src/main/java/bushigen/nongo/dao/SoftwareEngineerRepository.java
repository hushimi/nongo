package bushigen.nongo.dao;

import org.apache.ibatis.annotations.*;
import java.util.List;
import bushigen.nongo.entity.SoftwareEngineer;

/**
 * Repository interface for SoftwareEngineer data access operations.
 * Uses MyBatis annotations for SQL mapping.
 */
@Mapper
public interface SoftwareEngineerRepository {

    @Select("""
        SELECT
            id, name, tech_stack
        FROM software_engineer
        ORDER BY id
    """)
    List<SoftwareEngineer> findAll();

    @Select("""
        SELECT
            id, name, tech_stack
        FROM software_engineer
        WHERE id = #{id}
    """)
    SoftwareEngineer findById(Long id);

    @Insert("""
        INSERT INTO software_engineer
            (name, tech_stack)
        VALUES
            (#{name}, #{techStack})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SoftwareEngineer softwareEngineer);

    @Update("""
        UPDATE software_engineer
        SET
            name = #{name},
            tech_stack = #{techStack}
        WHERE id = #{id}
    """)
    void update(SoftwareEngineer softwareEngineer);

    @Delete("""
        DELETE FROM software_engineer
        WHERE id = #{id}
    """)
    void deleteById(Long id);
}
