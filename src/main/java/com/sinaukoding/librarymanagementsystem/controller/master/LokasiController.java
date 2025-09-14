package com.sinaukoding.librarymanagementsystem.controller.master;

import com.sinaukoding.librarymanagementsystem.model.filter.BukuFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.filter.LokasiFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.LokasiRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.response.BaseResponse;
import com.sinaukoding.librarymanagementsystem.service.master.BukuService;
import com.sinaukoding.librarymanagementsystem.service.master.LokasiService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lokasi")
@RequiredArgsConstructor
@Tag(name = "Lokasi API")
public class LokasiController {
    private final LokasiService lokasiService;

    @PostMapping("add")
    public BaseResponse<?> create(@RequestBody LokasiRequestRecord request) {
        lokasiService.create(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PutMapping("edit")
    public BaseResponse<?> edit(@RequestBody LokasiRequestRecord request) {
        lokasiService.edit(request);
        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @GetMapping("find-all")
    @Parameters({
            @Parameter(name = "page", description = "Page Number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"), required = true),
            @Parameter(name = "size", description = "Size Per Page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"), required = true),
            @Parameter(name = "sort", description = "Sorting Data", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "kodeLokasi,desc"), required = true),
            @Parameter(name = "kodeLokasi", description = "Search by kode lokasi", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "kapasitas", description = "Search by kapasitas", in = ParameterIn.QUERY, schema = @Schema(type = "integer"), required = false),
            @Parameter(name = "kapasitasTersedia", description = "Search by kapasitas tersedia", in = ParameterIn.QUERY, schema = @Schema(type = "integer"), required = false)
    })
    public BaseResponse<?> findAll(@Parameter(hidden = true) @PageableDefault(direction = Sort.Direction.DESC, sort = "kodeLokasi") Pageable pageable,
                                   @Parameter(hidden = true) @ModelAttribute LokasiFilterRecord filterRequest) {
        return BaseResponse.ok(null, lokasiService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, lokasiService.findById(id));
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasRole('PUSTAKAWAN')")
    public BaseResponse<?> deleteById(@PathVariable String id) {
        lokasiService.delete(id);
        return BaseResponse.ok("Data berhasil didelete", null);
    }
}