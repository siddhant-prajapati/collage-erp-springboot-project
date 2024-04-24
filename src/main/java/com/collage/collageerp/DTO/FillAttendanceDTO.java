package com.collage.collageerp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FillAttendanceDTO {
  private String date;

  private String role;

  private List<Integer> userIds;
}
