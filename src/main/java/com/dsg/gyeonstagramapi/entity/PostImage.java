package com.dsg.gyeonstagramapi.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImage {

    private String imageName;

    private Integer ord;
}
