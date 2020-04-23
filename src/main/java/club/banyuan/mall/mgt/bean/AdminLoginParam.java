package club.banyuan.mall.mgt.bean;


import javax.validation.constraints.NotBlank;

public class AdminLoginParam {
   @NotBlank(message = "用户名不能为空")
   private String username;
   @NotBlank(message = "密码不能为空")
   private String password;

   public AdminLoginParam() {

   }

   public AdminLoginParam(String username, String password) {
      this.username = username;
      this.password = password;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String toString() {
      return "AdminLoginParam{" +
              "username='" + username + '\'' +
              ", password='" + password + '\'' +
              '}';
   }
}
