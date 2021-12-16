package com.example.coupon.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public abstract class CodeEnumTypeHandler <E extends Enum <E>> implements TypeHandler <CodeEnum> {
    private Class <E> type;
    
    public CodeEnumTypeHandler(Class <E> type) {
        this.type = type;
    }
 
	@Override
	public void setParameter(PreparedStatement ps, int i, CodeEnum parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(1, parameter.getCode());
		
	}

	@Override
	public CodeEnum getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String code = rs.getString(columnName);
        return getCodeEnum(code);
	}

	@Override
	public CodeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String code = rs.getString(columnIndex);
        return getCodeEnum(code);
	}

	@Override
	public CodeEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		String code = cs.getString(columnIndex);
        return getCodeEnum(code);
	}
	
	private CodeEnum getCodeEnum(String code) {
        try {
            CodeEnum[] enumConstants = (CodeEnum[]) type.getEnumConstants();
            for (CodeEnum codeNum: enumConstants) {           
                if (codeNum.getCode().equals(code)) {
                    return codeNum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TypeException("Can't make enum object '" + type + "'", e);
        }
    }
}