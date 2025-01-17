package com.dsg.gyeonstagramapi.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "imageList")
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ColumnDefault("0")
    @Column(name = "del_flag")
    private Boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<PostImage> imageList = new ArrayList<>();
}
