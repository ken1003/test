package ebmw.model.dao;

import ebmw.model.dto.RoleTaskKey;

public interface RoleTaskMapper {
    int deleteByPrimaryKey(RoleTaskKey key);

    int insert(RoleTaskKey record);

    int insertSelective(RoleTaskKey record);
}