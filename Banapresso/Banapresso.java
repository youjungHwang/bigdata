package com.koreait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Banapresso {

   public static void main(String[] args) {
      String DRIVER_ID = "webdriver.chrome.driver";
      String DRIVER_PATH = "C:/HYJ/Jsp/chromedriver.exe";

      System.setProperty(DRIVER_ID, DRIVER_PATH);
      WebDriver driver = new ChromeDriver();
     

      Connection conn = null;
      PreparedStatement pstmt = null;

      String sql = "";
      String url = "jdbc:mysql://localhost:3306/jspstudy";
      String uid = "root";
      String upw = "1234";
    

      String base_url = "https://www.banapresso.com/store";

      try {
         driver.get(base_url);

         int pageNum = 2;
         String ba_name = null; // 지점명 
         String ba_address = null; // 주소 

         while (true) {
            List<WebElement> name = driver.findElements(By.cssSelector(".store_name_map > i"));
            List<WebElement> address = driver.findElements(By.cssSelector(".store_name_map > i + span"));

            try {
               Class.forName("com.mysql.jdbc.Driver");
               conn = DriverManager.getConnection(url, uid, upw);

               if (conn != null) {
                  sql = "insert into tb_banapresso(ba_name, ba_address) values (?, ?)";
                  pstmt = conn.prepareStatement(sql);

                  for (int i = 0; i <= name.size(); i++) {
                     ba_name = name.get(i).getText();
                     ba_address = address.get(i).getText();
                     // 리스트(배열)에 저장되어 있는 것을 한줄씩 출력

                     System.out.println(ba_name); 
                     pstmt.setString(1, ba_name); // DB에 저장
                     System.out.println(ba_address);
                     pstmt.setString(2, ba_address);
                     pstmt.executeUpdate();
                  }
               }
            } catch (Exception e) {
               e.printStackTrace();
            }

            if (pageNum <= 5) {
               WebElement Nextpage = driver.findElement(By.cssSelector(".pagination > ul > li:nth-child(" + pageNum + ")"));
               Nextpage.click();
               Thread.sleep(2000);
               pageNum++;
            } else {
               WebElement Nexttab = driver.findElement(By.cssSelector(".store_list_box > .pagination > .btn_page_next > a"));
               Nexttab.click();
               Thread.sleep(2000);
               pageNum = 2;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      driver.close();

   } 
}