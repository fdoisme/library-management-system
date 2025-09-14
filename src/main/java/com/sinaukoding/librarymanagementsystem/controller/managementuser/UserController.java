package com.sinaukoding.librarymanagementsystem.controller.managementuser;

import com.sinaukoding.librarymanagementsystem.model.enums.Role;
import com.sinaukoding.librarymanagementsystem.model.filter.UserFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.UserRequestRecord;
import com.sinaukoding.librarymanagementsystem.model.response.BaseResponse;
import com.sinaukoding.librarymanagementsystem.service.managementuser.UserService;
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
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public BaseResponse<?> register(@RequestParam(required = false) boolean pustakawan, @RequestBody UserRequestRecord request) {
        System.out.println(pustakawan);
        if (pustakawan) {
            userService.register(request, Role.PUSTAKAWAN);
            return BaseResponse.ok("Data berhasil disimpan", null);
        }
        userService.register(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("edit")
    public BaseResponse<?> edit(@RequestBody UserRequestRecord request) {
        userService.edit(request);
        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @GetMapping("find-all")
//    @PreAuthorize("hasRole('PUSTAKAWAN')")
    @Parameters({
            @Parameter(name = "page", description = "Page Number", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"), required = true),
            @Parameter(name = "size", description = "Size Per Page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"), required = true),
            @Parameter(name = "sort", description = "Sorting Data", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "modifiedDate,desc"), required = true),
            @Parameter(name = "nama", description = "Search by name", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "username", description = "Search by username", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "email", description = "Search by email", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""), required = false),
            @Parameter(name = "role", description = "Search by role", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false),
            @Parameter(name = "status", description = "Search by status", in = ParameterIn.QUERY, schema = @Schema(type = "string"), required = false)
    })
    public BaseResponse<?> findAll(@Parameter(hidden = true) @PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @Parameter(hidden = true) @ModelAttribute UserFilterRecord filterRequest) {
        return BaseResponse.ok(null, userService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, userService.findById(id));
    }

    @DeleteMapping("delete/{id}")
//    @PreAuthorize("hasRole('PUSTAKAWAN')")
    public BaseResponse<?> deleteById(@PathVariable String id) {
        userService.delete(id);
        return BaseResponse.ok("Data berhasil didelete", null);
    }

}