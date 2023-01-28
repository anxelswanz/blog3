package com.ansel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description tag
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "The tag name cannot be empty")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();
}