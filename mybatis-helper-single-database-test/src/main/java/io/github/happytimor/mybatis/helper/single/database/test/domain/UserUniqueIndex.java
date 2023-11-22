package io.github.happytimor.mybatis.helper.single.database.test.domain;

/**
 * @author chenpeng
 */
public class UserUniqueIndex {
    private Integer id;
    private String cardNo;
    private String name;

    public UserUniqueIndex() {

    }

    public UserUniqueIndex(String cardNo, String name) {
        this.cardNo = cardNo;
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
