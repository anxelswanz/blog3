package com.ansel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/23
 * @Description post
 */

@Entity
@Table(name = "t_blog")
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY) //第一次初始化 使用
    @Lob
    private String content;
    private String firstPicture;
    private String flag;
    private Integer Views;
    private String description;
    private boolean tips;
    private boolean disclaimer;
    private boolean commentabled;
    private boolean published;
    private boolean recommended;
    @Transient // 不会入库
    private String tagIds;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.REMOVE})
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }
    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    public Post(Long id, String title, String content, String firstPicture, String flag, Integer views, String description, boolean tips, boolean disclaimer, boolean commentabled, boolean published, boolean recommended, String tagIds, Date createDate, Date updateTime, Type type, List<Tag> tags, List<Comment> comments, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        Views = views;
        this.description = description;
        this.tips = tips;
        this.disclaimer = disclaimer;
        this.commentabled = commentabled;
        this.published = published;
        this.recommended = recommended;
        this.tagIds = tagIds;
        this.createDate = createDate;
        this.updateTime = updateTime;
        this.type = type;
        this.tags = tags;
        this.comments = comments;
        this.user = user;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return Views;
    }

    public void setViews(Integer views) {
        Views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTips() {
        return tips;
    }

    public void setTips(boolean tips) {
        this.tips = tips;
    }

    public boolean isDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(boolean disclaimer) {
        this.disclaimer = disclaimer;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
