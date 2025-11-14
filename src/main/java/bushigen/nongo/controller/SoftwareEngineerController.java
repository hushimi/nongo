package bushigen.nongo.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import bushigen.nongo.service.SoftwareEngineerService;
import bushigen.nongo.model.SoftwareEngineer;
import bushigen.nongo.dto.request.SoftwareEngineerCreateRequest;
import bushigen.nongo.dto.request.SoftwareEngineerUpdateRequest;
import bushigen.nongo.dto.response.SoftwareEngineerResponse;
import bushigen.nongo.entitymapper.SoftwareEngineerObjMapper;

@Slf4j
@RestController
@RequestMapping("/api/software-engineers")
@Tag(name = "Software Engineer", description = "Software Engineer management API")
public class SoftwareEngineerController {

    private final SoftwareEngineerService service;
    private final SoftwareEngineerObjMapper entityMapper;

    /**
     * コンストラクタ
     */
    public SoftwareEngineerController(SoftwareEngineerService service, SoftwareEngineerObjMapper entityMapper) {
        this.service = service;
        this.entityMapper = entityMapper;
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
        // RequestをModelに変換
        SoftwareEngineer seModel = entityMapper.toModel(createRequest);

        // 新規レコード作成
        service.insertSoftwareEngineer(seModel);
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
        SoftwareEngineer seModel = entityMapper.toModel(updateRequest);

        // レコード更新
        service.updateSoftwareEngineer(seModel);
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
        List<SoftwareEngineer> softwareEngineers = service.getAllSoftwareEngineers();
        // modelリストをレスポンス型リストに変換
        return entityMapper.toResponseList(softwareEngineers);
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
        SoftwareEngineer engineer = service.getSoftwareEngineerById(id);
        // Entityをレスポンス型データに変換
        return entityMapper.toResponse(engineer);
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
        service.deleteSoftwareEngineerById(id);
    }

}
