package club.banyuan.mall.mgt.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class ResponsePages<T> {

    /**
     * pageNum : 1
     * pageSize : 10
     * totalPage : 1
     * total : 4
     * list : [{"id":1,"username":"test","password":"$2a$10$NZ5o7r2E.ayT2ZoxgjlI.eJ6OEYqjH7INR/F.mXDbjZJi9HF0YCVG","icon":"http://minio.banyuan.club/dev/preset/timg.jpg","email":"test@qq.com","nickName":"测试账号","note":null,"createTime":"2018-09-29T05:55:30.000+0000","loginTime":"2018-09-29T05:55:39.000+0000","status":1},{"id":3,"username":"admin","password":"$2a$10$j3R00nPamXaPyfIWL1NKGeNONZxEkxd1bGWjYhx7RsiBH8fXEpP7S","icon":"http://minio.banyuan.club/dev/preset/timg.jpg","email":"admin@163.com","nickName":"系统管理员","note":"系统管理员","createTime":"2018-10-08T05:32:47.000+0000","loginTime":"2019-04-20T04:45:16.000+0000","status":1},{"id":6,"username":"productAdmin","password":"$2a$10$iTPUCsonRsO7FuYbFGaIyeHWnrBs9iuBg4SshlaHggO8QA3UXfZga","icon":null,"email":"product@qq.com","nickName":"商品管理员","note":"只有商品权限","createTime":"2020-02-07T08:15:08.000+0000","loginTime":null,"status":1},{"id":7,"username":"orderAdmin","password":"$2a$10$ARwNRFwCCQ4YVWC9023He.1gnrtlFAI5Wxpo75kldfWqWp4gy9Ex6","icon":null,"email":"order@qq.com","nickName":"订单管理员","note":"只有订单管理权限","createTime":"2020-02-07T08:15:50.000+0000","loginTime":null,"status":1}]
     */

    private int pageNum;
    private int pageSize;
    private int totalPage;
    private long total;
    private List<T> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    public static  <T> ResponsePages listByPageInfo(PageInfo<?> pageInfo,List<T> resultList){
        ResponsePages<T> responsePages=new ResponsePages ();
        responsePages.setList (resultList);
        responsePages.setPageNum (pageInfo.getPageNum ());
        responsePages.setPageSize (pageInfo.getSize ());
        responsePages.setTotal (pageInfo.getTotal ());
        responsePages.setTotalPage (pageInfo.getPages ());
        return responsePages;

    }
}
