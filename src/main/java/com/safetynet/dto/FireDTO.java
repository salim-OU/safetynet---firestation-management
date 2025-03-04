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
public class FireDTO {
    private String stationNumber;
    private List<PersonInfoDTO> residents;
}