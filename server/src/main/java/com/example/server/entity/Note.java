package com.example.server.entity;

import com.example.server.annotation.NoExtraFields;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "notes")
@NoExtraFields
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Название обязательно!")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "Дата обязательна!")
    @Column(nullable = false)
    private String date;
    @NotBlank(message = "Время обязательно!")
    @Column(nullable = false)
    private String time;
}
