package com.ming.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ming.dao.ICrawlerDAO;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 没时间写说明拉,帮忙写下 ^-^.
 * User: Ming Li
 * Time: 13-10-11 下午5:54
 */
public class CrawlerDAO implements ICrawlerDAO {

    public static final String ALL = "all";             //全部
    public static final String RANDOM = "random";       //随机
    public static final String ACCURATE = "accurate";   //精准
    public static final String FUZZY = "fuzzy";         //模糊
    private ComboPooledDataSource dataSource;

    @Override
    public void addWebPage(String webPage) {
        String insertSql = "INSERT INTO WEB_PAGE(SITE, GENERATE_TIME) VALUES (?, ?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, webPage);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Set<String> getWebPage(String flag) {
        Set<String> result = new HashSet<String>();
        String insertSql = "SELECT SITE FROM WEB_PAGE";
        if (ALL.equals(flag)) {
            insertSql = "SELECT SITE FROM WEB_PAGE";
        } else if (RANDOM.equals(flag)){
            insertSql = "SELECT SITE FROM WEB_PAGE ORDER BY RANDOM() LIMIT 1";
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("site").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public Set<String> getAllWebPage() {
        return getWebPage(ALL);
    }

    @Override
    public String getRandomWebPage() {
        String result = "";
        Set<String> webPageSet = getWebPage(RANDOM);
        if (!webPageSet.isEmpty()) {
            result = (webPageSet.toArray()[0]).toString();
        }
        return result;
    }

    @Override
    public String findSiteByKey(String key, String findType) {
        StringBuilder result = new StringBuilder();
        StringBuilder insertSql = new StringBuilder();
        if (ACCURATE.equals(findType)) {
            insertSql.append("SELECT SITE FROM WEB_PAGE WHERE SITE = '").append(key).append("'");
        } else if (FUZZY.equals(findType)) {
            insertSql.append("SELECT SITE FROM WEB_PAGE WHERE SITE LIKE '%").append(key).append("%'");
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertSql.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.append(resultSet.getString("site").trim()).append("|");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    @Override
    public void addWebPageCur(String webPageCur) {
        String insertSql = "INSERT INTO WEB_PAGE_CUR(SITE, GENERATE_TIME) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, webPageCur);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Set<String> getWebPageCur() {
        Set<String> result = new HashSet<String>();
        String insertSql = "SELECT SITE FROM WEB_PAGE_CUR";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("site").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public void delWebPageCur(String webPageCur) {
        String delSql = "DELETE FROM WEB_PAGE_CUR WHERE SITE = (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(delSql);
            preparedStatement.setString(1, webPageCur);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String pushWebPageCur() {
        Set<String> sites = getWebPageCur();
        Iterator<String> iterator = sites.iterator();
        String result = null;
        if (iterator.hasNext()) {
            result = iterator.next();
            delWebPageCur(result);
        }
        return result;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
