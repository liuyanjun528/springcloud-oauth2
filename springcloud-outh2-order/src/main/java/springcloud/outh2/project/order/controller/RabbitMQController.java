package springcloud.outh2.project.order.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.messaging.Message;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import springcloud.outh2.project.common.utils.DateFormat;
import springcloud.outh2.project.common.utils.DateUtils;
import springcloud.outh2.project.common.utils.UUIDUtils;
import springcloud.outh2.project.entity.Order;
/**
 * @author yanjun.liu
 * @date 2020/8/1--14:46
 */
@RestController
@RequestMapping("/rabbitMQ")
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 回调函数: confirm确认
     * correlationData 消息唯一id
     * ack 是否到达Broker
     * cause nack出现异常，返回的异常消息
     */
    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("ack: " + ack);
            if (!ack) {
                System.err.println("异常处理....");
            } else {
                System.out.println("成功");
            }
        }
    };

    /**
     * return返回回调
     * 若routingKey不可达。会回调此方法
     * message     消息
     * replyCode   不可达错误码
     * replyText   错误内容信息
     * exchange    交换机
     * routingKey  路由key
     */
    final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
            System.err.println("message:" + message);
        }
    };

    /**
     * @throws Exception
     * 消息由消息体和消息头组成,消息头可以不写
     *  需要额外的属性就自己加,不需要可以不写
     * MessageHeaders mhs = new MessageHeaders(properties);
     */
    @GetMapping
    public void send() throws Exception {
        for (int i = 0; i < 5; i++) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("number", "12345");
            properties.put("send_time", DateUtils.format(DateFormat.DATE));
            MessageHeaders mhs = new MessageHeaders(properties);
            Message msg = MessageBuilder.createMessage("这是发送的消息+：" + i + ":你好啊美女", mhs);
            rabbitTemplate.setConfirmCallback(confirmCallback);
            rabbitTemplate.setReturnCallback(returnCallback);
            //消息id = 时间戳 + 全局唯一id
            CorrelationData correlationData = new CorrelationData(UUIDUtils.generate32BitId() + Long.toString(System.currentTimeMillis()));
            rabbitTemplate.convertAndSend("spring_boot_exchange", "boot.#", msg, correlationData);
        }

    }

    @GetMapping("/sendOrder")
    public void sendOrder() throws Exception {
        Order order = new Order();
        order.setName("商品订单");
        order.setOrderNo(UUIDUtils.generate32BitId());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //消息id = 时间戳 + 全局唯一id
        CorrelationData correlationData = new CorrelationData(UUIDUtils.generate32BitId() + Long.toString(System.currentTimeMillis()));
        rabbitTemplate.convertAndSend("order-exchange", "order.#", order, correlationData);

    }

}
