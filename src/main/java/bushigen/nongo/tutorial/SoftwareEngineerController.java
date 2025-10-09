package bushigen.nongo.tutorial;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    /**
     * SoftwareEngineerの配列を返す
     */
    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    /**
     * 新規でSoftwareEngineerを追加
     * @param softwareEngineer
     */
    @PostMapping
    public void addNewSoftwareEngineer(
        @RequestBody SoftwareEngineer softwareEngineer
    )
    {
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    /**
     * ID指定でEngineer情報取得
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public SoftwareEngineer getEngineersById(
        @PathVariable Long id
    )
    {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    /**
     * ID指定でSoftwareEngineerを削除
     *
     * @param id
     */
    @PostMapping("/delete")
    public void deleteSoftwareEngineerById(@RequestBody Long id) {
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }

    /**
     * ID指定でSoftwareEngineerを編集
     * @param softwareEngineer 編集するエンジニア（ID含む）
     */
    @PostMapping("/edit")
    public void editSoftwareEngineer(@RequestBody SoftwareEngineer softwareEngineer) {
        softwareEngineerService.updateSoftwareEngineer(softwareEngineer);
    }
}
