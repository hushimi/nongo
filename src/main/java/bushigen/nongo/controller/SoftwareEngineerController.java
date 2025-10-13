package bushigen.nongo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import bushigen.nongo.dto.SoftwareEngineerCreateRequest;
import bushigen.nongo.dto.SoftwareEngineerResponse;
import bushigen.nongo.entity.SoftwareEngineer;
import bushigen.nongo.service.SoftwareEngineerService;
import bushigen.nongo.util.DtoConverter;

@RestController
@RequestMapping("/api/software-engineers")
@Tag(name = "Software Engineer", description = "Software Engineer management API")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    /**
     * Constructor
     */
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    /**
     * List取得
     */
    @Operation(
        summary = "Get all software engineers",
        description = "Retrieve a list of all software engineers",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        }
    )
    @GetMapping
    public List<SoftwareEngineerResponse> getEngineers() {
        List<SoftwareEngineer> softwareEngineers = softwareEngineerService.getAllSoftwareEngineers();

        return DtoConverter.convertList(
            softwareEngineers,
            eng -> new SoftwareEngineerResponse(
                eng.getId(),
                eng.getName(),
                eng.getTechStack()
            )
        );
    }

    /**
     * 単体取得
     */
    @Operation(
        summary = "Get software engineer by ID",
        description = "Retrieve a specific software engineer by their ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved software engineer")
        }
    )
    @GetMapping("{id}")
    public SoftwareEngineerResponse getEngineersById(
        @Parameter(description = "ID of the software engineer to retrieve")
        @PathVariable Long id
    ) {
        SoftwareEngineer engineer = softwareEngineerService.getSoftwareEngineerById(id);
        return DtoConverter.convert(
            engineer,
            eng -> new SoftwareEngineerResponse(
                eng.getId(),
                eng.getName(),
                eng.getTechStack()
            )
        );
    }

     /**
     * 新規データ作成
     */
    @Operation(
        summary = "Create new software engineer",
        description = "Add a new software engineer to the system",
        responses = {
            @ApiResponse(responseCode = "200", description = "Software engineer created successfully"),
        }
    )
    @PostMapping
    public void addNewSoftwareEngineer(
        @Parameter(description = "Software engineer data to create (ID will be auto-generated)")
        @RequestBody SoftwareEngineerCreateRequest createRequest
    ) {
        // RequestをEntityに変換
        SoftwareEngineer softwareEngineer = new SoftwareEngineer();
        softwareEngineer.setName(createRequest.getName());
        softwareEngineer.setTechStack(createRequest.getTechStack());

        // 新規レコード作成
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    /**
     * ID指定で編集
     */
    @Operation(
        summary = "Update software engineer",
        description = "Update an existing software engineer",
        responses = {
            @ApiResponse(responseCode = "200", description = "Software engineer updated successfully"),
        }
    )
    @PostMapping("/edit")
    public void editSoftwareEngineer(
        @Parameter(description = "Software engineer data to update (must include ID)")
        @RequestBody SoftwareEngineer softwareEngineer
    ) {
        softwareEngineerService.updateSoftwareEngineer(softwareEngineer);
    }

    /**
     * ID指定でDelete
     */
    @Operation(
        summary = "Delete software engineer",
        description = "Delete a software engineer by ID",
        responses = {
            @ApiResponse(
                responseCode = "200", description = "ok",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"id\": 123 }")
                )
            )
        }
    )
    @PostMapping("/delete")
    public void deleteSoftwareEngineerById(
        @Parameter(description = "ID of the software engineer to delete")
        @RequestBody Long id
    ) {
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }

}
