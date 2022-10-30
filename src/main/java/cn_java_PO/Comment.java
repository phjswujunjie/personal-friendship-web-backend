package cn_java_PO;

public class Comment {
    private Long id;

    private String content;

    private String media;

    private String createTime;

    private Long blogId;

    private String loveUser;

    private Integer loveUserNumber;

    private Long commentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media == null ? null : media.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getLoveUser() {
        return loveUser;
    }

    public void setLoveUser(String loveUser) {
        this.loveUser = loveUser == null ? null : loveUser.trim();
    }

    public Integer getLoveUserNumber() {
        return loveUserNumber;
    }

    public void setLoveUserNumber(Integer loveUserNumber) {
        this.loveUserNumber = loveUserNumber;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}