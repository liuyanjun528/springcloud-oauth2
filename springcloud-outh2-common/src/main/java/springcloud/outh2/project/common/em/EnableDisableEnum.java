package springcloud.outh2.project.common.em;

/**
 * Created by vilens on 2019/7/18.
 */
public enum EnableDisableEnum {

    NO(0, "启用"),
    YES(1, "禁用");

    private Integer id;

    private String name;

    private EnableDisableEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getValueByKey(Integer key) {
        for (EnableDisableEnum c : EnableDisableEnum.values()) {
            if (c.getId().equals(key)) {
                return c.getName();
            }
        }
        return "";
    }

}
