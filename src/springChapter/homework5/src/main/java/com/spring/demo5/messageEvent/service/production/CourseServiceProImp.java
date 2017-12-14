package com.spring.demo5.messageEvent.service.production;

import com.spring.demo5.messageEvent.model.TCourse;
import com.spring.demo5.messageEvent.service.CourseService;
import com.spring.demo5.messageEvent.service.MyCoureseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 00013810 on 2017/9/14.
 */
@Service
public class CourseServiceProImp implements CourseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TCourse> getAllCourse() {
        String sql = "SELECT * FROM sp_course";
        return jdbcTemplate.query(sql, new TCourseRowMapper());
    }

    static class TCourseRowMapper implements RowMapper<TCourse> {
        @Override
        public TCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
            TCourse tCourse = new TCourse();
            tCourse.setId(rs.getInt("id"));
            tCourse.setName(rs.getString("name"));
            tCourse.setMark(rs.getInt("mark"));
            return tCourse;
        }
    }
}
