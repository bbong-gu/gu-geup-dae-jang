package com.codesmith.goojangreport.report.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyKtasResponse {
    List<Long> ktas1;
    List<Long> ktas2;
    List<Long> ktas3;
    List<Long> ktas4;
    List<Long> ktas5;
}
