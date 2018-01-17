/**
 * @fileName :     dbNameConveter
 * @author :       zeeker
 * @date :         11/01/2018 17:09
 * @description :  数据库和bean的名称转化
 */

package com.zeeker.utils.jdbc;

public class DbNameConveter {

    /**
     * 将数据库的列名转化为bean属性名  phone_no -> phoneNo
     * @param columeName
     * @return
     */
    public static String underlineToCamel(String columeName){
        StringBuilder fieldName = new StringBuilder();

        String[] columes = columeName.split("_");
        String subString = null;
        fieldName.append(columes[0]);
        for (int i = 1; i < columes.length; i++){
            subString = columes[i].trim();
            if (subString != null){
                fieldName.append(Character.toUpperCase(subString.charAt(0)));
                fieldName.append(subString.substring(1, subString.length()));
            }
        }
        return fieldName.toString();
    }

    /**
     * 将bean属性名转化为数据库列名 phoneNo -> phone_no
     * @param fieldName
     * @return
     */
    public static String camelToUnderline(String fieldName){
        StringBuilder underlineString = new StringBuilder();
        for (Character c : fieldName.toCharArray()){
            underlineString.append(Character.isLowerCase(c) ? c : "_" + Character.toLowerCase(c));
        }
        return underlineString.toString();
    }

    /**
     * 将类名转化为数据库表名 BaseObject -> base_object
     * @param tableName
     * @return
     */
    public static String className2TableName(String tableName){
        return DbNameConveter.camelToUnderline(tableName).substring(1);
    }
}
