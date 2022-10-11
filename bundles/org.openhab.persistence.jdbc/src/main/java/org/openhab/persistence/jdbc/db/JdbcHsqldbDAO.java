/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.persistence.jdbc.db;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.knowm.yank.Yank;
import org.openhab.core.items.Item;
import org.openhab.core.types.State;
import org.openhab.persistence.jdbc.dto.ItemVO;
import org.openhab.persistence.jdbc.dto.ItemsVO;
import org.openhab.persistence.jdbc.utils.StringUtilsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extended Database Configuration class. Class represents
 * the extended database-specific configuration. Overrides and supplements the
 * default settings from JdbcBaseDAO. Enter only the differences to JdbcBaseDAO here.
 *
 * @author Helmut Lehmeyer - Initial contribution
 */
@NonNullByDefault
public class JdbcHsqldbDAO extends JdbcBaseDAO {
    private static final String DRIVER_CLASS_NAME = org.hsqldb.jdbcDriver.class.getName();
    @SuppressWarnings("unused")
    private static final String DATA_SOURCE_CLASS_NAME = org.hsqldb.jdbc.JDBCDataSource.class.getName();

    private final Logger logger = LoggerFactory.getLogger(JdbcHsqldbDAO.class);

    /********
     * INIT *
     ********/
    public JdbcHsqldbDAO() {
        initSqlQueries();
        initSqlTypes();
        initDbProps();
    }

    private void initSqlQueries() {
        logger.debug("JDBC::initSqlQueries: '{}'", this.getClass().getSimpleName());
        // http://hsqldb.org/doc/guide/builtinfunctions-chapt.html
        sqlPingDB = "SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS";
        sqlGetDB = "SELECT DATABASE () FROM INFORMATION_SCHEMA.SYSTEM_USERS";
        sqlIfTableExists = "SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES WHERE TABLE_NAME='#searchTable#'";
        sqlCreateItemsTableIfNot = "CREATE TABLE IF NOT EXISTS #itemsManageTable# ( ItemId INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL, #colname# #coltype# NOT NULL)";
        sqlCreateNewEntryInItemsTable = "INSERT INTO #itemsManageTable# (ItemName) VALUES ('#itemname#')";
        // Prevent error against duplicate time value
        // http://hsqldb.org/doc/guide/dataaccess-chapt.html#dac_merge_statement
        // SQL_INSERT_ITEM_VALUE = "INSERT INTO #tableName# (TIME, VALUE) VALUES( NOW(), CAST( ? as #dbType#) )";
        sqlInsertItemValue = "MERGE INTO #tableName# "
                + "USING (VALUES #tablePrimaryValue#, CAST( ? as #dbType#)) temp (TIME, VALUE) ON (#tableName#.TIME=temp.TIME) "
                + "WHEN NOT MATCHED THEN INSERT (TIME, VALUE) VALUES (temp.TIME, temp.VALUE)";
    }

    /**
     * INFO: http://www.java2s.com/Code/Java/Database-SQL-JDBC/StandardSQLDataTypeswithTheirJavaEquivalents.htm
     */
    private void initSqlTypes() {
    }

    /**
     * INFO: https://github.com/brettwooldridge/HikariCP
     */
    private void initDbProps() {
        // Properties for HikariCP
        databaseProps.setProperty("driverClassName", DRIVER_CLASS_NAME);
        // driverClassName OR BETTER USE dataSourceClassName
        // databaseProps.setProperty("dataSourceClassName", DATA_SOURCE_CLASS_NAME);
    }

    /**************
     * ITEMS DAOs *
     **************/
    @Override
    public @Nullable Integer doPingDB() {
        return Yank.queryScalar(sqlPingDB, Integer.class, null);
    }

    @Override
    public ItemsVO doCreateItemsTableIfNot(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlCreateItemsTableIfNot,
                new String[] { "#itemsManageTable#", "#colname#", "#coltype#", "#itemsManageTable#" },
                new String[] { vo.getItemsManageTable(), vo.getColname(), vo.getColtype(), vo.getItemsManageTable() });
        logger.debug("JDBC::doCreateItemsTableIfNot sql={}", sql);
        Yank.execute(sql, null);
        return vo;
    }

    @Override
    public Long doCreateNewEntryInItemsTable(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlCreateNewEntryInItemsTable,
                new String[] { "#itemsManageTable#", "#itemname#" },
                new String[] { vo.getItemsManageTable(), vo.getItemname() });
        logger.debug("JDBC::doCreateNewEntryInItemsTable sql={}", sql);
        return Yank.insert(sql, null);
    }

    /*************
     * ITEM DAOs *
     *************/
    @Override
    public void doStoreItemValue(Item item, State itemState, ItemVO vo) {
        ItemVO storedVO = storeItemValueProvider(item, itemState, vo);
        String sql = StringUtilsExt.replaceArrayMerge(sqlInsertItemValue,
                new String[] { "#tableName#", "#dbType#", "#tableName#", "#tablePrimaryValue#" },
                new String[] { storedVO.getTableName(), storedVO.getDbType(), storedVO.getTableName(),
                        sqlTypes.get("tablePrimaryValue") });
        Object[] params = { storedVO.getValue() };
        logger.debug("JDBC::doStoreItemValue sql={} value='{}'", sql, storedVO.getValue());
        Yank.execute(sql, params);
    }

    /****************************
     * SQL generation Providers *
     ****************************/

    /*****************
     * H E L P E R S *
     *****************/

    /******************************
     * public Getters and Setters *
     ******************************/
}
