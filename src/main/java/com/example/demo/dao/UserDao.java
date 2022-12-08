package com.example.demo.dao;

import com.example.demo.pojo.User;
import com.example.demo.util.JdbcUtil;
import com.example.demo.util.TimeUtil;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.UserCardVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao {

    public static void main(String[] args) {
        System.out.println(new UserDao().getUserInfo(2).toString());
    }

    public String getUserGender(Integer userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT gender FROM user u where u.id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String gender = null;
            while (rs.next()) {
                gender = (rs.getString("gender"));
            }
            connection.close();
            return gender;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserCardVO getUserCardInfo(Integer userId) {
        try {
            Connection connection = JdbcUtil.getConnection();

            String sql = "(SELECT DISTINCT u.id, u.username, u.avatar, u.gender, u.createtime as create_time " +
                    ", SUM(TIMESTAMPDIFF(MINUTE, create_time, expire_time)) as cumulative_study_time " +
                    "From task t, user u " +
                    "WHERE t.is_finished = 1 AND t.uid = ? AND u.id = t.uid) " +
                    "UNION " +
                    "(SELECT u.id, u.username, u.avatar, u.gender, u.createtime as create_time, 0 " +
                    "FROM `user` u WHERE id = ?) ";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            UserCardVO userCardVO = null;
            while (rs.next()) {
                if ((rs.getInt("id") == 0)) continue;
                userCardVO = new UserCardVO();
                userCardVO.setId(rs.getInt("id"));
                userCardVO.setCreateTime(rs.getDate("create_time"));
                userCardVO.setGender(rs.getString("gender"));
                userCardVO.setUsername(rs.getString("username"));
                userCardVO.setAvatar(rs.getString("avatar"));
                userCardVO.setCumulativeStudyTime(rs.getInt("cumulative_study_time"));
                break;
            }
            connection.close();
            return userCardVO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserInfo(int userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(userId);
                user.setAvatar(resultSet.getString("avatar"));
                user.setGender(resultSet.getString("gender"));
                user.setUsername(resultSet.getString("username"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ContactVO> getContactInfo(int userId) {
        try {
            Connection connection = JdbcUtil.getConnection();
            String sql = "(SELECT c.id, uu.avatar, uu.username, c.create_time, c.content, uu.id friend_id " +
                    "FROM chat_log c, user u, user uu " +
                    "WHERE c.sender = u.id AND u.id = ? AND c.text_type = 2 AND uu.id = c.receiver) " +
                    "UNION " +
                    "(SELECT c.id, uu.avatar, uu.username, c.create_time, c.content, uu.id frenid_id " +
                    "FROM chat_log c, user u, user uu " +
                    "WHERE c.receiver = u.id AND u.id = ? AND c.text_type = 2 AND uu.id = c.sender) " +
                    "ORDER BY create_time DESC ";
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            List<ContactVO> contactVOList = new ArrayList<>();
            while (resultSet.next()) {
                ContactVO contactVO = new ContactVO();
                contactVO.setId(resultSet.getInt("id"));
                contactVO.setAvatar(resultSet.getString("avatar"));
                contactVO.setUsername(resultSet.getString("username"));
                contactVO.setContent(resultSet.getString("content"));
                contactVO.setFriendId(resultSet.getInt("friend_id"));
                contactVO.setCreateTime(TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("create_time")));
                contactVOList.add(contactVO);
            }
            return contactVOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(User user) {
        Connection conn = null;
        Statement stat = null;
        int result = 0;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "INSERT INTO user (account, password, username) VALUES ('"+user.getAccount()+"','"+user.getPassword()+"','"+user.getUsername()+"')";
            result = stat.executeUpdate(sql);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //将结果返回
        return result;
    }


    public User show(int i) {
        User user = new User();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "SELECT * From user WHERE id ='"+i+"'";
            rs = stat.executeQuery(sql);
            while(rs.next()) {
                Integer id = rs.getInt("id");
                String account = rs.getString("account");
                String password = rs.getString("password");
                Integer age =rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                Date birthday = rs.getDate("birthday");
                String username = rs.getString("username");

                //封装mvdent对象
                user = new User(account,password,age,gender,phone,username);

                //将mvdent对象保存到集合中
            }

        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;

    }


    public int update(User user) {
        Connection conn = null;
        Statement stat = null;
        int result = 0;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "UPDATE user set account ='"+user.getAccount()+"', password ='"+user.getPassword()+"', age ='"+user.getAge()+"', gender ='"+ user.getGender() +"', phone ='"+ user.getPhone()+"',username ='"+ user.getUsername()+"' where id ='"+ user.getId()+"'";
            result = stat.executeUpdate(sql);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //将结果返回
        return result;
    }


    public boolean login(String account, String password) {
        Connection conn = null;
        Statement stat = null;
        ResultSet result = null;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "select * from user where account='" + account + "' and password= '" + password + "' ";
            result = stat.executeQuery(sql);
            if(result.next()){
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //将结果返回
        return false;
    }


    public boolean exist(String account) {
        Connection conn = null;
        Statement stat = null;
        ResultSet result = null;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "SELECT * From user WHERE account = '"+account+"'";
            result = stat.executeQuery(sql);
            if(!result.next()){
                return true;
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public int getid(String account) {
        int id = 0;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try{
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyroom", "root", "your password");

            //3.获取执行者对象
            stat = conn.createStatement();

            //4.执行sql语句，并且接收返回的结果集
            String sql = "SELECT id From user WHERE account ='"+account+"'";
            rs = stat.executeQuery(sql);
            while(rs.next()) {
                id = rs.getInt("id");
            }

        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;

    }
}
