/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author NitefullWind
 */
public class DBOp {
    String driver = "com.mysql.jdbc.Driver";
    String url="jdbc:mysql://115.159.160.167:3306/non?user=adminzzf&password=admin123&useUnicode=true&characterEncoding=UTF8";
    Connection  con = null;
    public String errorMessage;

    private PreparedStatement pstmt = null;

    /**
     *
     * @return 
     */
    public boolean connectDB()
   {
       try
       {
           Class.forName(driver);//加载并注册驱动程序
           con=DriverManager.getConnection(url);//建立连接
           return true;
       }catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(DBOp.class.getName()).log(Level.SEVERE, null, ex);
       }
       errorMessage = "服务器出问题了";
       return false;
   }
    //执行带参数的查询
    public List execSelect(String sql, String[] parameters) {
        ResultSet results = null;
        List list = new ArrayList();
        
        if(con == null) {
            if(!connectDB()){
                return list;
            }
        }
//        if(connectDB()){
            try { 
                pstmt = con.prepareStatement(sql);
                for(int i=0; i<parameters.length; i++) {
                    pstmt.setString(i+1, parameters[i]);
                }
                results = pstmt.executeQuery();
                //获取查询到的列数
                int colCount = results.getMetaData().getColumnCount();
                if(results.next()) {   
                    //将每一列的值放入一个row
                    List row = new ArrayList();
                    for(int i=1; i<=colCount; i++) {
                        row.add(results.getString(i));
                    }
                    //将一个row插入list
                    list.add(row);
                }else{
                    errorMessage = "用户名或密码错误";
                }
                return list;
            } catch (SQLException ex) {
                errorMessage="服务器出错了";
                Logger.getLogger(DBOp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
//                closeDB();
            }
//        }
        return list;
    }
//    @Override
    public List execSelect(String sql) {
        String[] nullList = {};
        return execSelect(sql, nullList);
    }
    
//    @Override 根据行号，列号获取一个字符串
    public String execSelect(String sql, int rowIndex, int colIndex) {
        List list = execSelect(sql);
        if(list!=null && list.size() >= rowIndex) {
            list = (List)list.get(rowIndex);
            if(list!=null && list.size() >= colIndex) {
                return (String)list.get(colIndex);
            }
        }
        return "";
    }
//    @Override 根据行号获取一行记录
//    public List execSelect(String sql, int rowIndex) {
//        List list = execSelect(sql);
//        if(list!=null && list.size() >= rowIndex) {
//            list = (List)list.get(rowIndex);
//            return list;
//        }
//        return null;
//    }
    
    //执行带参数的如insert,delete,update操作,返回操作影响的行数
    public int execUpdate(String sql, String[] parameterList) {
        int resultCount = -1;
        if(con == null) {
            connectDB();
        }
//        if(connectDB()){
            try { 
                pstmt = con.prepareStatement(sql);
                for(int i=0; i<parameterList.length; i++) {
                    pstmt.setString(i+1, parameterList[i]);
                }
                resultCount = pstmt.executeUpdate();
            } catch (SQLException ex) {
                errorMessage = "邮箱已被注册";
                Logger.getLogger(DBOp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
//                closeDB();
            }
//        }
        return resultCount;
    }
    
    //关闭查询
    public void closeDB() {
        try {
            if(pstmt!=null){
                pstmt.close();
            }
            if(con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
