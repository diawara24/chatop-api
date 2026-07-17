package com.openclassroom.chatopapi.services;

import com.openclassroom.chatopapi.dto.CreateRentalDto;
import com.openclassroom.chatopapi.dto.RentalDto;
import com.openclassroom.chatopapi.dto.RentalListResponse;
import com.openclassroom.chatopapi.dto.UpdateRentalDto;
import com.openclassroom.chatopapi.record.RentalUpSertResponse;

import java.util.List;
import java.util.Map;

public interface RentalService {

    RentalListResponse getAll();

    RentalDto findById(Integer id);

    RentalUpSertResponse create(CreateRentalDto dto) throws Exception;

    RentalUpSertResponse update(Integer id, UpdateRentalDto dto);
}
