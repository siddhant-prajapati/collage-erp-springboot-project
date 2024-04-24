package com.collage.collageerp.entity;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
// Class
public class EmailDetails {

    // Class data members
    private String recipient;

    private String msgBody;

    private String subject;

    private String attachment;
}