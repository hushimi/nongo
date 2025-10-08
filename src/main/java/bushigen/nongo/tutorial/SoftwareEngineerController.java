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
     * Retrieves a list of all software engineers.
     *
     * @return a list of SoftwareEngineer objects
     */
    @GetMapping
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    /**
     * Adds a new software engineer to the system.
     *
     * @param softwareEngineer the SoftwareEngineer object to add (from request body)
     */
    @PostMapping
    public void addNewSoftwareEngineer(
        @RequestBody SoftwareEngineer softwareEngineer
    )
    {
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    /**
     * Retrieves a software engineer by their unique ID.
     *
     * @param id the ID of the software engineer to retrieve
     * @return the SoftwareEngineer object with the specified ID
     */
    @GetMapping("{id}")
    public SoftwareEngineer getEngineersById(
        @PathVariable Long id
    )
    {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    /**
     * Deletes a software engineer by their unique ID (POST request, id in body).
     *
     * @param id the ID of the software engineer to delete (from request body)
     */
    @PostMapping("/delete")
    public void deleteSoftwareEngineerById(@RequestBody Long id) {
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }
}
