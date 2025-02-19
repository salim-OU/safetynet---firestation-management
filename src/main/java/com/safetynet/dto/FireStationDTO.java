package com.safetynet.dto;


import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireStationDTO {
    private String address;
    private String station;
    private List<PersonDTO> residents;
    private int adultCount;
    private int childCount;
}