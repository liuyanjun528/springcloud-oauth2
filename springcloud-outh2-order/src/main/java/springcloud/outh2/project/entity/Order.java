package springcloud.outh2.project.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanjun.liu
 * @date 2020/8/2--18:32
 */
@Data
public class Order implements Serializable {

    private String name;
    private String orderNo;
}
