package com.sinaukoding.librarymanagementsystem.service.managementuser.impl;

import com.sinaukoding.librarymanagementsystem.builder.CustomBuilder;
import com.sinaukoding.librarymanagementsystem.entity.managementuser.User;
import com.sinaukoding.librarymanagementsystem.mapper.managementuser.UserMapper;
import com.sinaukoding.librarymanagementsystem.model.app.AppPage;
import com.sinaukoding.librarymanagementsystem.model.app.SimpleMap;
import com.sinaukoding.librarymanagementsystem.model.enums.Role;
import com.sinaukoding.librarymanagementsystem.model.enums.Status;
import com.sinaukoding.librarymanagementsystem.model.filter.UserFilterRecord;
import com.sinaukoding.librarymanagementsystem.model.request.UserRequestRecord;
import com.sinaukoding.librarymanagementsystem.repository.managementuser.UserRepository;
import com.sinaukoding.librarymanagementsystem.service.app.ValidatorService;
import com.sinaukoding.librarymanagementsystem.service.managementuser.UserService;
import com.sinaukoding.librarymanagementsystem.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRequestRecord request) {
        register(request, Role.ANGGOTA);
    }

    @Override
    public void register(UserRequestRecord request, Role role) {
//        System.out.println("MASUKK ADD 2 PARAMETER");
//        System.out.println(role.getLabel());
        // validasi mandatory
        validatorService.validator(request);

        // validasi data existing
        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }
        if (userRepository.existsByUsername(request.username().toLowerCase())) {
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);
        user.setRole(role);
        user.setOnLoan(false);
        user.setStatus(Status.AKTIF);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    @Override
    public void edit(UserRequestRecord request) {
        // validasi mandatory
        validatorService.validator(request);

        var userExisting = userRepository.findById(request.id()).orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));

        // validasi data existing
        if (userRepository.existsByEmailAndIdNot(request.email().toLowerCase(), request.id())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }
        if (userRepository.existsByUsernameAndIdNot(request.username().toLowerCase(), request.id())) {
            throw new RuntimeException("Username [" + request.username() + "] sudah digunakan");
        }

        var user = userMapper.requestToEntity(request);
        user.setId(userExisting.getId());
        user.setRole(userExisting.getRole());
        user.setOnLoan(userExisting.isOnLoan());
        user.setStatus(userExisting.getStatus());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    @Override
    public Page<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable) {
        System.out.println("MASUKK FINDALL 1");
        CustomBuilder<User> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotBlankLike("email", filterRequest.email(), builder);
        FilterUtil.builderConditionNotBlankLike("username", filterRequest.username(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("role", filterRequest.role(), builder);

        Page<User> listUser = userRepository.findAll(builder.build(), pageable);
        List<SimpleMap> listData = listUser.stream().map(user -> {
            SimpleMap data = new SimpleMap();
            data.put("id", user.getId());
            data.put("nama", user.getNama());
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());
            data.put("role", user.getStatus().getLabel());
            data.put("status", user.getRole().getLabel());
            return data;
        }).toList();

        return AppPage.create(listData, pageable, listUser.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));
        SimpleMap data = new SimpleMap();
        data.put("id", user.getId());
        data.put("nama", user.getNama());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("status", user.getStatus().getLabel());
        data.put("role", user.getRole().getLabel());
        return data;
    }

    @Override
    public void delete(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));
        userRepository.delete(user);
    }

}