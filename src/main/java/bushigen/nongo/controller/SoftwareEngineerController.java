package bushigen.nongo.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import bushigen.nongo.util.DtoConverter;
import bushigen.nongo.dto.request.SoftwareEngineer.SoftwareEngineerCreateRequest;
import bushigen.nongo.dto.request.SoftwareEngineer.SoftwareEngineerUpdateRequest;
import bushigen.nongo.service.SoftwareEngineerService;
import bushigen.nongo.entity.SoftwareEngineer;
import bushigen.nongo.dto.response.SoftwareEngineerResponse;

@Slf4j
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
     * 新規データ作成
     */
    @Operation(
        summary = "Create new software engineer",
        description = "Add a new software engineer to the system"
    )
    @PostMapping
    public void addNewSoftwareEngineer(
        @Valid @RequestBody SoftwareEngineerCreateRequest createRequest
    ) {
        // RequestをEntityに変換
        SoftwareEngineer softwareEngineer = DtoConverter.convert(
            createRequest,
            req -> new SoftwareEngineer(req.name(), req.techStack())
        );

        // 新規レコード作成
        softwareEngineerService.insertSoftwareEngineer(softwareEngineer);
    }

    /**
     * ID指定で編集
     */
    @Operation(
        summary = "Update software engineer",
        description = "Update an existing software engineer"
    )
    @PostMapping("/edit")
    public void editSoftwareEngineer(
        @Valid @RequestBody SoftwareEngineerUpdateRequest updateRequest
    ) {
        // RequestをEntityに変換
        SoftwareEngineer softwareEngineer = DtoConverter.convert(
            updateRequest,
            req -> new SoftwareEngineer(req.id(), req.name(), req.techStack())
        );

        // レコード更新
        softwareEngineerService.updateSoftwareEngineer(softwareEngineer);
    }

    /**
     * List取得
     */
    @Operation(
        summary = "Get all software engineers",
        description = "Retrieve a list of all software engineers"
    )
    @GetMapping
    public List<SoftwareEngineerResponse> getEngineers() {
        List<SoftwareEngineer> softwareEngineers = softwareEngineerService.getAllSoftwareEngineers();
        // Entityリストをレスポンス型リストに変換
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
        description = "Retrieve a specific software engineer by their ID"
    )
    @GetMapping("{id}")
    public SoftwareEngineerResponse getEngineersById(
        @Parameter(description = "ID of the software engineer to retrieve")
        @PathVariable Long id
    ) {
        SoftwareEngineer engineer = softwareEngineerService.getSoftwareEngineerById(id);
        // Entityをレスポンス型データに変換
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
        @RequestBody Long id
    ) {
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }

}
