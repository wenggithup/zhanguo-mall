package club.banyuan.mall.mgt.bean;

public class AdminInfoMenusBean {

        /**
         * id : 1
         * parentId : 0
         * createTime : 2020-02-02T06:50:36.000+0000
         * title : 商品
         * level : 0
         * sort : 0
         * name : pms
         * icon : product
         * hidden : 0
         */

        private int id;
        private int parentId;
        private String createTime;
        private String title;
        private int level;
        private int sort;
        private String name;
        private String icon;
        private int hidden;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getHidden() {
            return hidden;
        }

        public void setHidden(int hidden) {
            this.hidden = hidden;
        }
    }

