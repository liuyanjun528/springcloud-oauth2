package springcloud.outh2.project.common.node;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanjun.liu
 * @date 2020/6/10--18:49
 */
public interface INode extends Serializable {

    /**
     * 主键
     *
     * @return Long
     */
    Long getId();

    /**
     * 父主键
     *
     * @return Long
     */
    Long getParentId();

    /**
     * 子孙节点
     *
     * @return List<INode>
     */
    List<INode> getChildren();

    /**
     * 是否有子孙节点
     *
     * @return Boolean
     */
    default Boolean getHasChildren() {
        return false;
    }

}
