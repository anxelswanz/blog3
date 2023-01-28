package com.ansel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.ORB;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description user
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany(mappedBy = "user")
    private List<Post> blogs = new ArrayList<>();
}
