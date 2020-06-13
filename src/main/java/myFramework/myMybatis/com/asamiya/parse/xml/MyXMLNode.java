package myFramework.myMybatis.com.asamiya.parse.xml;

/**
 * 将每个标签看做是一个node,node中有属性
 */
public class MyXMLNode {

    private String id;
    private String paramType;
    private String returnType ;
    private boolean isGenerateId ;
    private String sql;







    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean isGenerateId() {
        return isGenerateId;
    }

    public void setGenerateId(boolean generateId) {
        isGenerateId = generateId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
