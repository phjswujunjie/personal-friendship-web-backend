package cn_java_PO;

import java.util.ArrayList;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andMediaIsNull() {
            addCriterion("media is null");
            return (Criteria) this;
        }

        public Criteria andMediaIsNotNull() {
            addCriterion("media is not null");
            return (Criteria) this;
        }

        public Criteria andMediaEqualTo(String value) {
            addCriterion("media =", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaNotEqualTo(String value) {
            addCriterion("media <>", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaGreaterThan(String value) {
            addCriterion("media >", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaGreaterThanOrEqualTo(String value) {
            addCriterion("media >=", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaLessThan(String value) {
            addCriterion("media <", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaLessThanOrEqualTo(String value) {
            addCriterion("media <=", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaLike(String value) {
            addCriterion("media like", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaNotLike(String value) {
            addCriterion("media not like", value, "media");
            return (Criteria) this;
        }

        public Criteria andMediaIn(List<String> values) {
            addCriterion("media in", values, "media");
            return (Criteria) this;
        }

        public Criteria andMediaNotIn(List<String> values) {
            addCriterion("media not in", values, "media");
            return (Criteria) this;
        }

        public Criteria andMediaBetween(String value1, String value2) {
            addCriterion("media between", value1, value2, "media");
            return (Criteria) this;
        }

        public Criteria andMediaNotBetween(String value1, String value2) {
            addCriterion("media not between", value1, value2, "media");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andBlogIdIsNull() {
            addCriterion("blog_id is null");
            return (Criteria) this;
        }

        public Criteria andBlogIdIsNotNull() {
            addCriterion("blog_id is not null");
            return (Criteria) this;
        }

        public Criteria andBlogIdEqualTo(Long value) {
            addCriterion("blog_id =", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotEqualTo(Long value) {
            addCriterion("blog_id <>", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdGreaterThan(Long value) {
            addCriterion("blog_id >", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("blog_id >=", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdLessThan(Long value) {
            addCriterion("blog_id <", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdLessThanOrEqualTo(Long value) {
            addCriterion("blog_id <=", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdIn(List<Long> values) {
            addCriterion("blog_id in", values, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotIn(List<Long> values) {
            addCriterion("blog_id not in", values, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdBetween(Long value1, Long value2) {
            addCriterion("blog_id between", value1, value2, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotBetween(Long value1, Long value2) {
            addCriterion("blog_id not between", value1, value2, "blogId");
            return (Criteria) this;
        }

        public Criteria andLoveUserIsNull() {
            addCriterion("love_user is null");
            return (Criteria) this;
        }

        public Criteria andLoveUserIsNotNull() {
            addCriterion("love_user is not null");
            return (Criteria) this;
        }

        public Criteria andLoveUserEqualTo(String value) {
            addCriterion("love_user =", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserNotEqualTo(String value) {
            addCriterion("love_user <>", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserGreaterThan(String value) {
            addCriterion("love_user >", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserGreaterThanOrEqualTo(String value) {
            addCriterion("love_user >=", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserLessThan(String value) {
            addCriterion("love_user <", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserLessThanOrEqualTo(String value) {
            addCriterion("love_user <=", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserLike(String value) {
            addCriterion("love_user like", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserNotLike(String value) {
            addCriterion("love_user not like", value, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserIn(List<String> values) {
            addCriterion("love_user in", values, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserNotIn(List<String> values) {
            addCriterion("love_user not in", values, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserBetween(String value1, String value2) {
            addCriterion("love_user between", value1, value2, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserNotBetween(String value1, String value2) {
            addCriterion("love_user not between", value1, value2, "loveUser");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberIsNull() {
            addCriterion("love_user_number is null");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberIsNotNull() {
            addCriterion("love_user_number is not null");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberEqualTo(Integer value) {
            addCriterion("love_user_number =", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberNotEqualTo(Integer value) {
            addCriterion("love_user_number <>", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberGreaterThan(Integer value) {
            addCriterion("love_user_number >", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("love_user_number >=", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberLessThan(Integer value) {
            addCriterion("love_user_number <", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberLessThanOrEqualTo(Integer value) {
            addCriterion("love_user_number <=", value, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberIn(List<Integer> values) {
            addCriterion("love_user_number in", values, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberNotIn(List<Integer> values) {
            addCriterion("love_user_number not in", values, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberBetween(Integer value1, Integer value2) {
            addCriterion("love_user_number between", value1, value2, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andLoveUserNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("love_user_number not between", value1, value2, "loveUserNumber");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNull() {
            addCriterion("comment_id is null");
            return (Criteria) this;
        }

        public Criteria andCommentIdIsNotNull() {
            addCriterion("comment_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommentIdEqualTo(Long value) {
            addCriterion("comment_id =", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotEqualTo(Long value) {
            addCriterion("comment_id <>", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThan(Long value) {
            addCriterion("comment_id >", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("comment_id >=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThan(Long value) {
            addCriterion("comment_id <", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdLessThanOrEqualTo(Long value) {
            addCriterion("comment_id <=", value, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdIn(List<Long> values) {
            addCriterion("comment_id in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotIn(List<Long> values) {
            addCriterion("comment_id not in", values, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdBetween(Long value1, Long value2) {
            addCriterion("comment_id between", value1, value2, "commentId");
            return (Criteria) this;
        }

        public Criteria andCommentIdNotBetween(Long value1, Long value2) {
            addCriterion("comment_id not between", value1, value2, "commentId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}