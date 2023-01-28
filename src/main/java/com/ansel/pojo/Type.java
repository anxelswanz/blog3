package com.ansel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "The title cannot be empty")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Post> posts = new ArrayList<>();
}
