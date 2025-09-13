package com.sinaukoding.librarymanagementsystem.controller.master;

import com.sinaukoding.librarymanagementsystem.model.filter.BukuFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.BukuRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.response.BaseResponse;
import com.sinaukoding.librarymanagementsystem.service.master.BukuService;
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
@RequestMapping("buku")
@RequiredArgsConstructor
@Tag(name = "Buku API")
public class BukuController {
    private final BukuService bukuService;

    @PostMapping("add")
    public BaseResponse<?> create(@RequestBody BukuRequestRecord request) {
        bukuService.create(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PutMapping("edit")
    public BaseResponse<?> edit(@RequestBody BukuRequestRecord request) {
        if (request.lokasiId() == null){
            System.out.println("MANTAP NULL");
        }
        bukuService.edit(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @GetMapping("find-all")
    @Parameters({
            @Parameter(name = "page", description = "Page Number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"), required = true),
            @Parameter(name = "size", description = "Size Per Page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"), required = true),
            @Parameter(name = "sort", description = "Sorting Data", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "judul,desc"), required = true),
            @Parameter(name = "judul", description = "Search by judul", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "pengarang", description = "Search by pengarang", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "penerbit", description = "Search by penerbit", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "tahunPublikasi", description = "Search by tahunPublikasi", in = ParameterIn.QUERY, schema = @Schema(type = "integer"), required = false),
            @Parameter(name = "kategori", description = "Search by status", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "tags", description = "Search by status", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false)
    })
    public BaseResponse<?> findAll(@Parameter(hidden = true) @PageableDefault(direction = Sort.Direction.DESC, sort = "judul") Pageable pageable,
                                   @Parameter(hidden = true) @ModelAttribute BukuFilterRecord filterRequest) {
        return BaseResponse.ok(null, bukuService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, bukuService.findById(id));
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasRole('PUSTAKAWAN')")
    public BaseResponse<?> deleteById(@PathVariable String id) {
        bukuService.delete(id);
        return BaseResponse.ok("Data berhasil didelete", null);
    }
}
