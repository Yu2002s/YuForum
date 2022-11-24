package com.drny.forum.controller.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListToTextTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < parameter.size(); j ++) {
            if (j == parameter.size() - 1) {
                sb.append(parameter.get(j));
            } else {
                sb.append(parameter.get(j)).append(",");
            }
        }
        ps.setString(i, sb.toString());
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String resultString = rs.getString(columnName);
        if (StringUtils.isNotBlank(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String resultString = rs.getString(columnIndex);
        if (StringUtils.isNotBlank(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String resultString = cs.getString(columnIndex);
        if (StringUtils.isNotBlank(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }
}
