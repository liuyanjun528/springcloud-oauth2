package springcloud.outh2.project.common.node;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author yanjun.liu
 * @date 2020/6/10--18:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForestNode extends BaseNode{
    private static final long serialVersionUID = 1L;

    /**
     * 节点内容
     */
    private Object content;

    public ForestNode(Long id, Long parentId, Object content) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
    }
}
