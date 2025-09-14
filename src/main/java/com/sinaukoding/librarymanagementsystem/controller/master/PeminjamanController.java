package com.sinaukoding.librarymanagementsystem.controller.master;

import com.sinaukoding.librarymanagementsystem.model.filter.PeminjamanFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.PeminjamanRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.response.BaseResponse;
import com.sinaukoding.librarymanagementsystem.service.master.BukuService;
import com.sinaukoding.librarymanagementsystem.service.master.PeminjamanService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("peminjaman")
@RequiredArgsConstructor
@Tag(name = "Peminjaman API")
public class PeminjamanController {
    private final PeminjamanService peminjamanService;

    @PostMapping("pinjam-buku")
    public BaseResponse<?> create(@RequestBody PeminjamanRequestRecord request) {
        peminjamanService.create(request);
        return BaseResponse.ok("Buku berhasil dipinjam", null);
    }

    @PutMapping("pengembalian-buku")
    public BaseResponse<?> edit(@RequestBody PeminjamanRequestRecord request) {
        peminjamanService.edit(request);
        return BaseResponse.ok("Buku berhasil dikembalikan", null);
    }

    @GetMapping("find-all")
    @PreAuthorize("hasRole('PUSTAKAWAN')")
    @Parameters({
            @Parameter(name = "page", description = "Page Number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"), required = true),
            @Parameter(name = "size", description = "Size Per Page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"), required = true),
            @Parameter(name = "sort", description = "Sorting Data", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "returnDate,desc"), required = true),
            @Parameter(name = "dueDate", description = "Search by due date", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "returnDate", description = "Search by return date", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "createdDate", description = "Search by created date", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "bukuId", description = "Search by buku id", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "userId", description = "Search by user id", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false)
    })
    public BaseResponse<?> findAll(@Parameter(hidden = true) @PageableDefault(direction = Sort.Direction.DESC, sort = "judul") Pageable pageable,
                                   @Parameter(hidden = true) @ModelAttribute PeminjamanFilterRecord filterRequest) {
        return BaseResponse.ok(null, peminjamanService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, peminjamanService.findById(id));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('PUSTAKAWAN')")
    public BaseResponse<?> deleteById(@PathVariable String id) {
        peminjamanService.delete(id);
        return BaseResponse.ok("Data berhasil didelete", null);
    }
}
