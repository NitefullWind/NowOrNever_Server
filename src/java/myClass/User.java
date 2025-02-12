/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myClass;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NitefullWind
 */
public class User{
    private int id  = -1;
    private String email = "";
    private String userName = "";
    private int planNum = -1;
    private int newNum = -1;
    private int finishedNum = -1;
    private int learnedNum = 0;
    private int totalNum = 1;
    private int learnIndex = 0;
    private String tableName = "table_words";
    private DBOp db;
    public String errorMessage;

    public User() {
        db = new DBOp();
    }
    
    public boolean Login(String userEmail, String userPwd){
        String sql = "select * from tbl_user, tbl_learninfo where tbl_user.id=tbl_learninfo.id and email = ? and pwd = PASSWORD(?)";
        List list = db.execSelect(sql, new String[]{userEmail, userPwd});
        if(list!=null && list.size()==1) {
            list = (List)list.get(0);
            setId(Integer.parseInt(list.get(0).toString()));
            email = list.get(1).toString();
            userName = list.get(2).toString();
            planNum = Integer.parseInt(list.get(5).toString());
            newNum = Integer.parseInt(list.get(6).toString());
            finishedNum = Integer.parseInt(list.get(7).toString());
            learnedNum = Integer.parseInt(list.get(8).toString());
            totalNum = Integer.parseInt(list.get(9).toString());
            learnIndex = Integer.parseInt(list.get(10).toString());
            tableName = list.get(11).toString();
            return true;
        }
        errorMessage = db.errorMessage;
        return  false;
    }
    
    public boolean Signup(String userName, String userEmail, String userPwd) {
        String sql = "insert into tbl_user(name, email, pwd) values(?,?,?)";
        int count = db.execUpdate(sql, new String[]{userName, userEmail, userPwd});
        if(count == 1) {
            return true;
        }
        errorMessage = db.errorMessage;
        return false;
    }
    
    public boolean setLearnInfo(String uidString, String planNumString, String newNumString , String finishedNumString, 
            String learnNumString, String totalNumString, String learnIndexString, String tableNameString) {
        String sql = "update tbl_learninfo set planNum=?,newNum=?,finishedNum=?,learnedNum=?,totalNum=?,learnIndex=?, tableName=? where id = ?";
        int count = db.execUpdate(sql, new String[]{planNumString, newNumString, finishedNumString, learnNumString, totalNumString, learnIndexString, tableNameString, uidString});
        if(count == 1) {
            return true;
        }
        errorMessage = "更新学习信息失败";
        return false;
    }

    public String getUserName() {
        return userName;
    }

    public int getPlanNum() {
        return planNum;
    }

    public int getNewNum() {
        return newNum;
    }

    public int getFinishedNum() {
        return finishedNum;
    }

    public int getLearnedNum() {
        return learnedNum;
    }

    public void setLearnedNum(int learnedNum) {
        this.learnedNum = learnedNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getLearnIndex() {
        return learnIndex;
    }

    public void setLearnIndex(int learnIndex) {
        this.learnIndex = learnIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
