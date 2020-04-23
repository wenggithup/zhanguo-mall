package club.banyuan.mall.mgt.bean;

public class AdminLoginResp {

        /**
         * tokenHead : Bearer
         * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE1ODY5NzU0ODM3NjYsImV4cCI6MTU4NzU4MDI4M30.FDSmP8IK9_WDm28BOadayhephXeGxJIxwC4VhUklyXVzLsQ_ixr6mu1BigjXS3bC9dzpzdov4Z_N8a2R73pJuw
         */

        private String tokenHead;
        private String token;

        public String getTokenHead() {
            return tokenHead;
        }

        public void setTokenHead(String tokenHead) {
            this.tokenHead = tokenHead;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

}
