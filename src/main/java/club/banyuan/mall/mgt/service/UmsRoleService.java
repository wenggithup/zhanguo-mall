package club.banyuan.mall.mgt.service;

import club.banyuan.mall.mgt.common.ResponsePages;

public interface UmsRoleService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);
}
