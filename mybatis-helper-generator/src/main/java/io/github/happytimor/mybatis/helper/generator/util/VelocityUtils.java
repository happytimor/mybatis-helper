package io.github.happytimor.mybatis.helper.generator.util;

import io.github.happytimor.mybatis.helper.generator.constant.GenConstants;
import io.github.happytimor.mybatis.helper.generator.domain.GenTable;
import io.github.happytimor.mybatis.helper.generator.domain.GenTableColumn;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VelocityUtils {

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String packageName = genTable.getPackageName();
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/entity.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/convertor.java.vm");
        templates.add("vm/java/addRequest.java.vm");
        templates.add("vm/java/editRequest.java.vm");
        templates.add("vm/java/queryRequest.java.vm");
        templates.add("vm/java/response.java.vm");
        templates.add("vm/java/controller.java.vm");
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable, VelocityContext context) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 大写类名
        String className = genTable.getClassName();

        String javaPath = StringUtils.replace(packageName, ".", "/");

        if (template.contains("entity.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}.java", javaPath, context.get("entity"), className);
        } else if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}Mapper.java", javaPath, context.get("mapper"), className);
        } else if (template.contains("convertor.java.vm")) {
            fileName = StringUtils.format("{}/{}/convertor/{}Convertor.java", javaPath, context.get("entity"), className);
        } else if (template.contains("service.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}Service.java", javaPath, context.get("service"), className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}Controller.java", javaPath, context.get("controller"), className);
        } else if (template.contains("addRequest.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}AddReq.java", javaPath, context.get("request"), className);
        } else if (template.contains("editRequest.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}EditReq.java", javaPath, context.get("request"), className);
        } else if (template.contains("queryRequest.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}QueryReq.java", javaPath, context.get("request"), className);
        } else if (template.contains("response.java.vm")) {
            fileName = StringUtils.format("{}/{}/{}Resp.java", javaPath, context.get("response"), className);
        }

        int lastDotIndex = fileName.lastIndexOf('/');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex).replace('.', '/')
                    + fileName.substring(lastDotIndex);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * 获取包后缀
     *
     * @param packageName 包名称
     * @return 包后缀名称
     */
    public static String getPackageSuffix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, lastIndex + 1);
    }


    /**
     * 根据列类型获取导入包
     *
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        HashSet<String> importList = new HashSet<String>();
        for (GenTableColumn column : columns) {
            if (GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.time.LocalDateTime");
                importList.add("org.springframework.format.annotation.DateTimeFormat");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }
}
