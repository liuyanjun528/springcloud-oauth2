package springcloud.outh2.project.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitReceiver {
    /**
     * @RabbitListener 注解监听方法队列，有消息就进行消费，并且可以声明交换机，队列，以及相互绑定
     */

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring_boot_queue",
                    durable = "true"),
            exchange = @Exchange(value = "spring_boot_exchange",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "boot.#"
    )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException, InterruptedException {
        System.err.println("--------------------------------------");
        System.err.println("消费端收到的消息: " + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        Thread.sleep(3000);
        //手工ACK
        channel.basicAck(deliveryTag, false);
    }


    /**
     * spring.rabbitmq.listener.order.queue.name=order-queue
     * spring.rabbitmq.listener.order.queue.durable=true
     * spring.rabbitmq.listener.order.exchange.name=order-exchange
     * spring.rabbitmq.listener.order.exchange.durable=true
     * spring.rabbitmq.listener.order.exchange.type=topic
     * spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
     * spring.rabbitmq.listener.order.key=order.#
     * 配置文件中读取
     *
     * @param order
     * @param channel
     * @param headers
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    )
    )

    /**
     * @Payload 注解指定消息体为java对象 springcloud.outh2.project.entity.Order包路径必须和生产者发送的对象包路径一样,
     *          可以抽取实体依赖,此项目不能直接依赖生产者,jar 认证配置会有冲突
     * @Headers 注解接受消息头
     * @param order
     * @param channel
     * @param headers
     * @throws Exception
     * 消费者一但接受到消息
     */
    @RabbitHandler
    public void onOrderMessage(@Payload springcloud.outh2.project.entity.Order order, Channel channel,
                               @Headers Map<String, Object> headers) throws Exception {
        boolean ack = true;
        Long deliveryTag = null;
        try {
            System.err.println("--------------------------------------");
            System.err.println("消费端获取order编号,执行业务逻辑: " + order.getOrderNo());
            deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            //抛异常,测试重回队列
            //throw new  RuntimeException();
        } catch (RuntimeException e) {
            e.getStackTrace();
            //打印日志,后续进行查看日志
            ack = false;
        }
        if (ack) {
            //手工ACK
            channel.basicAck(deliveryTag, false);
        } else {
            //参数2是否批量消息,参数3,设置true会重回队列,消息不会丢弃,设置false进入死信队列,目前没有配置死信队列,消息会丢失
            //配置了就不会丢失了
            //配置true,发生异常,可以发现一直打印在消费这条消息
            //一般不设置重回队列,需要配置一个死信队列
            channel.basicNack(deliveryTag, false, false);
        }

    }


}
