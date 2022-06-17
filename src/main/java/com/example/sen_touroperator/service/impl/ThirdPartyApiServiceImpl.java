package com.example.sen_touroperator.service.impl;

import com.example.sen_touroperator.exception_handler.exceptions.LandmarkException;
import com.example.sen_touroperator.exception_handler.exceptions.UserException;
import com.example.sen_touroperator.models.DTO.BestHotelInRegionDto;
import com.example.sen_touroperator.service.ThirdPartyApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class ThirdPartyApiServiceImpl implements ThirdPartyApiService {
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public BestHotelInRegionDto bestHotelInCurrentRegion(String region) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://best-booking-com-hotel.p.rapidapi.com/booking/best-accommodation?cityName="+region+"&countryName=Bulgaria"))
                .header("X-RapidAPI-Key", "b95aa7236bmsh04fca2abba66a82p1277dajsn3ad17468676c")
                .header("X-RapidAPI-Host", "best-booking-com-hotel.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new LandmarkException("Booking Api Error");
        }
        String json = response.body();
        BestHotelInRegionDto bestHotelInRegion = null;
        try {
            bestHotelInRegion = objectMapper.readValue(json, BestHotelInRegionDto.class);
        } catch (JsonProcessingException e) {
            throw new LandmarkException("Api Request Limit Reached");
        }
        return bestHotelInRegion;
    }
}
