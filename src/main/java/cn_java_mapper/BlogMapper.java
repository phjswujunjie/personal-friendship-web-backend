package cn_java_mapper;

import cn_java_PO.Blog;
import cn_java_PO.BlogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogMapper {
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Blog row);

    int insertSelective(Blog row);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Blog row, @Param("example") BlogExample example);

    int updateByExample(@Param("row") Blog row, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog row);

    int updateByPrimaryKey(Blog row);

    @Select("select * from blog where is_public=4 order by id DESC")
    List<Map<String, Object>> displayAroundBlog();
}