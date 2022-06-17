package com.example.sen_touroperator.service;

import com.example.sen_touroperator.models.DTO.BestHotelInRegionDto;

public interface ThirdPartyApiService {
     BestHotelInRegionDto bestHotelInCurrentRegion(String region);
}
