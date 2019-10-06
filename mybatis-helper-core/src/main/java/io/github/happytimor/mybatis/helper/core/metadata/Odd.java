package io.github.happytimor.mybatis.helper.core.metadata;

/**
 * @author chenpeng
 * @date 2019-10-06
 */
public class Odd {
    private String tableNum;
    private Integer id;

    public Odd(){

    }

    public Odd(String tableNum,Integer id){
        this.tableNum = tableNum;
        this.id = id;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
