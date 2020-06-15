package springcloud.outh2.project.common.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanjun.liu
 * @date 2020/6/10--18:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    private String title;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long key;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;
}
